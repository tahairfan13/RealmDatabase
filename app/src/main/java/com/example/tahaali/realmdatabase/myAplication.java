package com.example.tahaali.realmdatabase;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Taha Ali on 10/18/2017.
 */

public class myAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);  ///STEP1
        //Step 2
        RealmConfiguration configuration=new RealmConfiguration.Builder().name("myFirstRealm.realm")// BY DEFAULT it will create defult.realm
                .build();
        //Step 3
        Realm.setDefaultConfiguration(configuration);


    }
}



