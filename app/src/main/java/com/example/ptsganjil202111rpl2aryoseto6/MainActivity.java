package com.example.ptsganjil202111rpl2aryoseto6;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String URL_TEAMS =
            "https://www.thesportsdb.com/api/v1/json/123/search_all_teams.php?l=English%20Premier%20League";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageView bookmark;

    private Klub_Adapter adapter;
    private ArrayList<Model_Klub> klubArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());

        bookmark = findViewById(R.id.bookmark);
        recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progressbar);

        if (recyclerView == null || progressBar == null || bookmark == null) {
            Toast.makeText(this,
                    "UI error: cek id bookmark/recyclerview/progressbar di activity_main.xml",
                    Toast.LENGTH_LONG).show();
            return;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookmark.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritActivity.class);
            startActivity(intent);
        });

        getData();
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        long startMs = System.currentTimeMillis();

        AndroidNetworking.get(URL_TEAMS)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, "API response in " + (System.currentTimeMillis() - startMs) + " ms");

                            JSONArray jsonArray = response.optJSONArray("teams");
                            if (jsonArray == null) {
                                Toast.makeText(MainActivity.this,
                                        "Data tidak ditemukan (teams = null)",
                                        Toast.LENGTH_LONG).show();
                                return;
                            }

                            klubArrayList = new ArrayList<>();
                            int max = Math.min(jsonArray.length(), 20);

                            for (int i = 0; i < max; i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                String nama = obj.optString("strTeam", "-");
                                String tahun = obj.optString("intFormedYear", "-");
                                String deskripsi = obj.optString("strDescriptionEN", "");

                                // IMPORTANT: kosong -> null, supaya adapter bisa handle
                                String image = obj.optString("strTeamBadge", null);
                                if (image != null && image.trim().isEmpty()) image = null;

                                klubArrayList.add(new Model_Klub(image, nama, tahun, deskripsi));
                            }

                            adapter = new Klub_Adapter(klubArrayList, position -> {
                                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                intent.putExtra("nama", klubArrayList.get(position).getNama());
                                intent.putExtra("tahun", klubArrayList.get(position).getTahun());
                                intent.putExtra("deskripsi", klubArrayList.get(position).getDeskripsi());
                                intent.putExtra("image", klubArrayList.get(position).getImage());
                                startActivity(intent);
                            });

                            recyclerView.setAdapter(adapter);

                        } catch (Exception e) {
                            Log.e(TAG, "Parsing error", e);
                            Toast.makeText(MainActivity.this,
                                    "Parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        } finally {
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "Network error: " + anError.getErrorDetail(), anError);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,
                                "Network error: " + anError.getErrorDetail(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}