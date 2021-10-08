package com.example.ptsganjil202111rpl2aryoseto6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Klub_Adapter adapter;
    ArrayList<Model_Klub> KlubArrayList;
    ImageView bookmark;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookmark = findViewById(R.id.bookmark);
        recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        getData();


        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoritActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getData(){
        AndroidNetworking.get("https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=English%20Premier%20League")
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            KlubArrayList = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("teams");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String nama = jsonObject.getString("strTeam");
                                String tahun = jsonObject.getString("intFormedYear");
                                                     String deskripsi = jsonObject.getString("strDescriptionEN");
                            String image = jsonObject.getString("strTeamBadge");



                            KlubArrayList.add(new Model_Klub(image, nama, tahun, deskripsi));
                        }

                        adapter = new Klub_Adapter(KlubArrayList, new Klub_Adapter.Callback() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                intent.putExtra("nama", KlubArrayList.get(position).getNama());
                                intent.putExtra("tahun", KlubArrayList.get(position).getTahun());
                                intent.putExtra("deskripsi", KlubArrayList.get(position).getDeskripsi());
                                intent.putExtra("image", KlubArrayList.get(position).getImage());
                                startActivity(intent);
                            }
                        });

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(layoutManager);

                        progressBar.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }
}
