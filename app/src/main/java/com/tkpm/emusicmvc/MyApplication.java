package com.tkpm.emusicmvc;

import android.app.Application;
import android.content.Context;

import com.tkpm.emusicmvc.models.db.DatabaseManager;
import com.tkpm.emusicmvc.models.db.SongListDbAdapter;

public class MyApplication extends Application {
    static SongListDbAdapter songListDbAdapter;
    DatabaseManager databaseManager;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseManager = DatabaseManager.newInstance(getApplicationContext());
        songListDbAdapter = SongListDbAdapter.getSongListAdapterInstance(databaseManager);
    }

    public static SongListDbAdapter getSongListDbAdapter() {
        return songListDbAdapter;
    }
}
