package com.example.ptsganjil202111rpl2aryoseto6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailActivityFavorit extends AppCompatActivity implements View.OnClickListener {

    Bundle bundle;
    ImageView iv_image, like;
    TextView tv_nama, tv_tahun, tv_deskripsi;

    String nama, image, tahun, deskripsi;
    int id;

    Realm realm;
    RealmHelper realmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorit);

        like = findViewById(R.id.dislikeFavorit);
        iv_image = findViewById(R.id.iv_image_klub_detailFavorit);
        tv_nama = findViewById(R.id.tv_nama_detailFavorit);
        tv_tahun = findViewById(R.id.tv_tahun_detailFavorit);
        tv_deskripsi = findViewById(R.id.tv_deskripsi_detailFavorit);

        bundle = getIntent().getExtras();
        id = bundle.getInt("id", 0);
        nama = bundle.getString("nama");
        tahun = bundle.getString("tahun");
        deskripsi = bundle.getString("deskripsi");
        image = bundle.getString("image");

        tv_nama.setText(nama);
        tv_tahun.setText(tahun);
        tv_deskripsi.setText(deskripsi);
        Picasso.get()
                .load(image)
                .into(iv_image);

        // Set up
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);


        like.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == like){
            realmHelper.delete(id);
            Toast.makeText(DetailActivityFavorit.this, "Delete Success", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
