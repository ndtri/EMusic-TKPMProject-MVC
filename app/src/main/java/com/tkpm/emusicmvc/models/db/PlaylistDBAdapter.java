package com.tkpm.emusicmvc.models.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tkpm.emusicmvc.models.PlaylistSongModel;
import com.tkpm.emusicmvc.models.db.DatabaseManager;
import com.tkpm.emusicmvc.models.Song;

import java.util.ArrayList;

public class PlaylistDBAdapter {
    public static final String TABLE_NAME = "playlist";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PLAYLIST_TITLE = "title";
    public static final String COLUMN_NUMBER_OF_SONG = "number_of_song";
//    private static final String TAG = "PlaylistModel";

    public static final String SCRIPT_CREATE_TABLE = new StringBuilder("CREATE TABLE ")
            .append(TABLE_NAME).append("(")
            .append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            .append(COLUMN_PLAYLIST_TITLE).append(" TEXT ")
            .append(" )")
            .toString();

    private static DatabaseManager mDatabaseManager = DatabaseManager.getInstance();
    private static PlaylistDBAdapter playlistDBAdapterInstance;
    private int id;
    private String title;
    private int numberOfSongs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfSong(){ return numberOfSongs;    }

    public void setNumberOfSong(int num){ this.numberOfSongs = num;}

    public String getTitle() { return title;    }

    private PlaylistDBAdapter(DatabaseManager databaseManager){}
    private PlaylistDBAdapter(){}

    public static PlaylistDBAdapter getplaylistDBAdapterInstance(DatabaseManager databaseManager){
        if(playlistDBAdapterInstance == null){
            playlistDBAdapterInstance = new PlaylistDBAdapter(databaseManager);
        }
        return playlistDBAdapterInstance;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public static long createPlaylist(String title) {
        SQLiteDatabase database = DatabaseManager.getInstance().getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLAYLIST_TITLE, title);
        long id = database.insert(TABLE_NAME, null, contentValues);
        database.close();
        return id;
    }

    public static ArrayList<PlaylistDBAdapter> getAllPlaylist() {
        ArrayList<PlaylistDBAdapter> playlistModels = new ArrayList<PlaylistDBAdapter>();
        SQLiteDatabase db = DatabaseManager.getInstance().getReadableDatabase();
        String query = "SELECT P." + COLUMN_ID + ",P." + COLUMN_PLAYLIST_TITLE + ",COUNT(PS." + PlaylistSongModel.COLUMN_ID + ") " + COLUMN_NUMBER_OF_SONG + " from " + TABLE_NAME + " P" +
                " LEFT JOIN " + PlaylistSongModel.TABLE_NAME + " PS ON P." + COLUMN_ID + "=PS." + PlaylistSongModel.COLUMN_PLAYLIST_ID + "" +
                " group by P." + COLUMN_ID + ",P." + COLUMN_PLAYLIST_TITLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                PlaylistDBAdapter playlist = new PlaylistDBAdapter();
                playlist.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                playlist.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_PLAYLIST_TITLE)));
                playlist.setNumberOfSong(cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER_OF_SONG)));
                playlistModels.add(playlist);
            } while (cursor.moveToNext());
        }

        return playlistModels;
    }


    public static ArrayList<PlaylistDBAdapter> getAllPlaylist(String value) {
        ArrayList<PlaylistDBAdapter> playlistModels = new ArrayList<PlaylistDBAdapter>();
        SQLiteDatabase db = DatabaseManager.getInstance().getReadableDatabase();

        String whereClause =  "WHERE ? = '' OR " + COLUMN_PLAYLIST_TITLE +" LIKE ?";
        String[] whereArgs = new String[]{value ,"%" + value + "%"};
        String query = "SELECT P." + COLUMN_ID + ",P." + COLUMN_PLAYLIST_TITLE + ",COUNT(PS." + PlaylistSongModel.COLUMN_ID + ") " + COLUMN_NUMBER_OF_SONG + " from " + TABLE_NAME + " P" +
                " LEFT JOIN " + PlaylistSongModel.TABLE_NAME + " PS ON P." + COLUMN_ID + "=PS." + PlaylistSongModel.COLUMN_PLAYLIST_ID + " "
                + whereClause +
                " group by P." + COLUMN_ID + ",P." + COLUMN_PLAYLIST_TITLE;

        Cursor cursor = db.rawQuery(query, whereArgs);
        if (cursor.moveToFirst()) {
            do {
                PlaylistDBAdapter playlist = new PlaylistDBAdapter();
                playlist.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                playlist.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_PLAYLIST_TITLE)));
                playlist.setNumberOfSong(cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER_OF_SONG)));
                playlistModels.add(playlist);
            } while (cursor.moveToNext());
        }

        return playlistModels;
    }


    public static ArrayList<PlaylistDBAdapter> getAllPlaylist(int skip, int take) {
        ArrayList<PlaylistDBAdapter> playlistModels = new ArrayList<PlaylistDBAdapter>();
        SQLiteDatabase db = DatabaseManager.getInstance().getReadableDatabase();
        String query = "SELECT P." + COLUMN_ID + ",P." + COLUMN_PLAYLIST_TITLE + ",COUNT(PS." + PlaylistSongModel.COLUMN_ID + ") " + COLUMN_NUMBER_OF_SONG +
                " FROM ( SELECT * from " + TABLE_NAME + " P LIMIT " + skip + "," + take + ") P " +
                " LEFT JOIN " + PlaylistSongModel.TABLE_NAME + " PS  ON P." + COLUMN_ID + "=PS." + PlaylistSongModel.COLUMN_PLAYLIST_ID + "" +
                " group by P." + COLUMN_ID + ",P." + COLUMN_PLAYLIST_TITLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                PlaylistDBAdapter playlist = new PlaylistDBAdapter();
                playlist.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                playlist.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_PLAYLIST_TITLE)));
                playlist.setNumberOfSong(cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER_OF_SONG)));
                playlistModels.add(playlist);
            } while (cursor.moveToNext());
        }

        return playlistModels;
    }

    public static PlaylistDBAdapter getPlaylistById(int playlistId) {

        SQLiteDatabase db = DatabaseManager.getInstance().getReadableDatabase();
        String query = "SELECT P." + COLUMN_ID + ",P." + COLUMN_PLAYLIST_TITLE + ",COUNT(PS." + PlaylistSongModel.COLUMN_ID + ") " + COLUMN_NUMBER_OF_SONG + " from " + TABLE_NAME + " P" +
                " LEFT JOIN " + PlaylistSongModel.TABLE_NAME + " PS ON P." + COLUMN_ID + "=PS." + PlaylistSongModel.COLUMN_PLAYLIST_ID + "  " +
                " WHERE P." + COLUMN_ID + " = " + playlistId + "  " +

                " group by P." + COLUMN_ID + ",P." + COLUMN_PLAYLIST_TITLE;
        Log.d("1", "getPlaylistById: " + query);
        Cursor cursor = db.rawQuery(query, null);
        Log.d("2", "getPlaylistById: " + cursor.getCount());
        if (cursor != null) {
            cursor.moveToFirst();
            PlaylistDBAdapter playlist = new PlaylistDBAdapter();
            playlist.setId(cursor.getInt(cursor.getColumnIndex(PlaylistDBAdapter.COLUMN_ID)));
            playlist.setTitle(cursor.getString(cursor.getColumnIndex(PlaylistDBAdapter.COLUMN_PLAYLIST_TITLE)));
            playlist.setNumberOfSong(cursor.getInt(cursor.getColumnIndex(PlaylistDBAdapter.COLUMN_NUMBER_OF_SONG)));
            return playlist;

        }

        return null;
    }

    public static PlaylistDBAdapter getInfoPlaylistById(int playlistId) {
        SQLiteDatabase db = DatabaseManager.getInstance().getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + playlistId;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            PlaylistDBAdapter playlist = new PlaylistDBAdapter();
            playlist.setId(cursor.getInt(cursor.getColumnIndex(PlaylistDBAdapter.COLUMN_ID)));
            playlist.setTitle(cursor.getString(cursor.getColumnIndex(PlaylistDBAdapter.COLUMN_PLAYLIST_TITLE)));
            return playlist;

        }

        return null;
    }

//    public static long updateTitlePlaylist(PlaylistModel playlistModel) {
//        SQLiteDatabase db = DatabaseManager.getInstance().getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(PlaylistModel.COLUMN_PLAYLIST_TITLE, playlistModel.getTitle());
//        long id = db.update(PlaylistModel.TABLE_NAME, contentValues, PlaylistModel.COLUMN_ID + " =? ", new String[]{String.valueOf(playlistModel.getId())});
//        return id;
//    }
//
//    public static long deletePlaylist(int playlistId) {
//        SQLiteDatabase db = DatabaseManager.getInstance().getWritableDatabase();
//        long result = db.delete(PlaylistModel.TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(playlistId)});
//        return result;
//    }
}

