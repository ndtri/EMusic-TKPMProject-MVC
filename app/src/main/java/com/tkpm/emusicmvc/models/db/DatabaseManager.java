package com.tkpm.emusicmvc.models.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
    private static DatabaseManager mDatabaseInstance = null;

    private SQLiteDatabase mDatabase;

    public Context getContext() {
        return mContext;
    }

    private Context mContext;
    private int mOpenCounter;

    public static DatabaseManager getInstance() {
        return mDatabaseInstance;
    }

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "E-Music_db.db";
    //singleton
    public static DatabaseManager newInstance(Context context) {
        if (mDatabaseInstance == null) {
            mDatabaseInstance = new DatabaseManager(context.getApplicationContext());
        }
        return mDatabaseInstance;
    }

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SongListDbAdapter.SCRIPT_CREATE_TABLE);
        //db.execSQL(PlaylistModel.SCRIPT_CREATE_TABLE);
        //db.execSQL(PlaylistSongModel.SCRIPT_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SongListDbAdapter.TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + PlaylistModel.TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + PlaylistSongModel.TABLE_NAME);
        onCreate(db);
    }

    public void resetDB() {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + SongListDbAdapter.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + PlaylistModel.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + PlaylistSongModel.TABLE_NAME);
        onCreate(db);
    }
}

