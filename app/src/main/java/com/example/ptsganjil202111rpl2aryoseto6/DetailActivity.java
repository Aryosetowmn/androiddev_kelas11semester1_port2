package com.example.ptsganjil202111rpl2aryoseto6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    Bundle bundle;
    ImageView iv_image, ic_like, ic_back;
    TextView tv_nama, tv_tahun, tv_deskripsi;

    String nama, image, tahun, deskripsi;

    Realm realm;
    RealmHelper realmHelper;
    ModelKlubRealm modelKlubRealm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ic_back =findViewById(R.id.backdetail);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ic_like = findViewById(R.id.like);
        iv_image = findViewById(R.id.iv_image_klub_detail);
        tv_nama = findViewById(R.id.tv_nama_detail);
        tv_tahun = findViewById(R.id.tv_tahun_detail);
        tv_deskripsi = findViewById(R.id.tv_deskripsi_detail);

        bundle = getIntent().getExtras();
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


        //Set up Realm
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

        ic_like.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == ic_like){

            Toast.makeText(DetailActivity.this, "Save Succes", Toast.LENGTH_LONG).show();
            bundle = getIntent().getExtras();
            nama = bundle.getString("nama");
            tahun = bundle.getString("tahun");
            deskripsi = bundle.getString("deskripsi");
            image = bundle.getString("image");

            modelKlubRealm = new ModelKlubRealm();
            modelKlubRealm.setImage(image);
            modelKlubRealm.setNama(nama);
            modelKlubRealm.setTahun(tahun);
            modelKlubRealm.setDeskripsi(deskripsi);

            realmHelper = new RealmHelper(realm);
            realmHelper.save(modelKlubRealm);

        }
    }
}
