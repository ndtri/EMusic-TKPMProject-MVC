package com.tkpm.emusicmvc.models;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tkpm.emusicmvc.models.db.DatabaseManager;
import com.tkpm.emusicmvc.models.Song;
import com.tkpm.emusicmvc.models.db.PlaylistDBAdapter;

import java.text.MessageFormat;
import java.util.ArrayList;

public class PlaylistSongModel {
    public static final String TABLE_NAME = "playlist_song";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PLAYLIST_ID = "playlist_id";
    public static final String COLUMN_SONG_ID = "song_id";

    public static final String SCRIPT_CREATE_TABLE = new StringBuilder("CREATE TABLE ")
            .append(TABLE_NAME).append("(")
            .append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            .append(COLUMN_PLAYLIST_ID).append(" INTEGER, ")
            .append(COLUMN_SONG_ID).append(" INTEGER ")
            .append(" )")
            .toString();
    private int id;
    private int playlistId;
    private int songId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public static long addSongToPlaylist(int songId, int playlistId) {
        SQLiteDatabase db = DatabaseManager.getInstance().getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SONG_ID, songId);
        contentValues.put(COLUMN_PLAYLIST_ID, playlistId);
        long id = db.insert(TABLE_NAME, null, contentValues);
        if (id > 0) {
            PlaylistDBAdapter playlistModel = PlaylistDBAdapter.getInfoPlaylistById(playlistId);
        }
        return id;
    }


    public static boolean isSongExisitPlaylist(int songId, int playlistId) {
        SQLiteDatabase db = DatabaseManager.getInstance().getReadableDatabase();
        boolean result = false;
        String query = MessageFormat.format("SELECT {0} FROM {1} WHERE {2}={3} AND {4}={5}",
                new String[]{COLUMN_ID, TABLE_NAME, COLUMN_SONG_ID, String.valueOf(songId), COLUMN_PLAYLIST_ID, String.valueOf(playlistId)});
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            result = true;
        }
        //databaseManager.closeDatabase();
        return result;
    }

    public static ArrayList<Song> getAllSongFromPlaylistId(int playlistId) {
        SQLiteDatabase db = DatabaseManager.getInstance().getReadableDatabase();
        ArrayList<Song> resultSongs = new ArrayList<>();
        String query = "SELECT S.* FROM playlist_song PS JOIN song S ON PS.song_id=S.song_id WHERE PS.playlist_id=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(playlistId)});

        while (cursor.moveToNext()) {
            Song songModel = new Song();
            songModel.setId(cursor.getInt(cursor.getColumnIndex(Song.COLUMN_ID)));
            songModel.setSongId(cursor.getInt(cursor.getColumnIndex(Song.COLUMN_SONG_ID)));
            songModel.setTitle(cursor.getString(cursor.getColumnIndex(Song.COLUMN_TITLE)));
            songModel.setArtist(cursor.getString(cursor.getColumnIndex(Song.COLUMN_ARTIST)));
            songModel.setDuration(cursor.getLong(cursor.getColumnIndex(Song.COLUMN_DURATION)));
            songModel.setPath(cursor.getString(cursor.getColumnIndex(Song.COLUMN_PATH)));

            resultSongs.add(songModel);
        }

        return resultSongs;

    }

    public static long deleteSongInPlaylist(int songId, int playlistId) {
        SQLiteDatabase db = DatabaseManager.getInstance().getWritableDatabase();

        long resultDeleteSong = db.delete(TABLE_NAME, COLUMN_SONG_ID + " = ?" + " AND " + COLUMN_PLAYLIST_ID + " =?", new String[]{
                String.valueOf(songId), String.valueOf(playlistId)
        });

        return resultDeleteSong;
    }
}
