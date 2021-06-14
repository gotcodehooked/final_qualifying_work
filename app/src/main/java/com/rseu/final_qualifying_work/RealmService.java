package com.rseu.final_qualifying_work;


import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmService {


 public RealmService(){

   }

    private static RealmConfiguration realmConfiguration(){
         String realmName = "e-journal";
        return new RealmConfiguration.Builder().name(realmName).build();

    }


    public static Realm getInstance(){

        return Realm.getInstance(realmConfiguration());
    }

}
