package com.example.ptsganjil202111rpl2aryoseto6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FavoritActivity extends AppCompatActivity {

    ImageView ic_back;

    Realm realm;
    RealmHelper realmHelper;

    RecyclerView recyclerView;
    Favorite_Adapter favorite_adapter;
    List<ModelKlubRealm> modelKlubRealms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);

        ic_back = findViewById(R.id.backfavorit);
        recyclerView = findViewById(R.id.recyclerviewfavorite);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        // Setup Realm
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

        realmHelper = new RealmHelper(realm);
        modelKlubRealms = new ArrayList<>();

        modelKlubRealms = realmHelper.getAllData();

        show();

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        favorite_adapter.notifyDataSetChanged();
        show();
    }

    public void show(){
        favorite_adapter = new Favorite_Adapter(modelKlubRealms, new Favorite_Adapter.Callback() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(FavoritActivity.this, DetailActivityFavorit.class);
                intent.putExtra("id", modelKlubRealms.get(position).getId());
                intent.putExtra("nama", modelKlubRealms.get(position).getNama());
                intent.putExtra("tahun", modelKlubRealms.get(position).getTahun());
                intent.putExtra("deskripsi", modelKlubRealms.get(position).getDeskripsi());
                intent.putExtra("image", modelKlubRealms.get(position).getImage());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(favorite_adapter);
    }

}
