package com.example.ptsganjil202111rpl2aryoseto6;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper  {

    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    // untuk menyimpan data
    public void save(final ModelKlubRealm modelKlubRealm){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null){
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(ModelKlubRealm.class).max("id");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    modelKlubRealm.setId(nextId);
                    realm.copyToRealm(modelKlubRealm);
                }else{
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
    }

    // untuk memanggil semua data
    public List<ModelKlubRealm> getAllData(){
        RealmResults<ModelKlubRealm> results = realm.where(ModelKlubRealm.class).findAll();
        return results;
    }

    // untuk menghapus data
    public void delete(final int id){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<ModelKlubRealm> model = realm.where(ModelKlubRealm.class).equalTo("id", id).findAll();
                Log.d("pppppppppppppppp", "getAllData: "+model.size());
                model.deleteFromRealm(0);
            }
        });
    }

}
