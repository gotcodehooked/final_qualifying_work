package com.rseu.final_qualifying_work;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

public class RealmApp  extends Application {


    public static App app;


    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        String appID = "fqw-mgjuw";

        app = new App(new AppConfiguration.Builder(BuildConfig.APPLICATION_ID)
                .defaultSyncErrorHandler((session, error) -> Log.e(TAG(), "Sync error: ${error.errorMessage}"))
                .build());
    }

    private String TAG() {
        return "lel";
    }


}
