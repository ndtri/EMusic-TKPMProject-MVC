package com.tkpm.emusicmvc.models.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;

import com.tkpm.emusicmvc.models.Song;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;

public class SongListDbAdapter {
    private static final String TAG = "SONG_MODEL";
    public static final String TABLE_NAME = "songs";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SONG_ID = "song_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ARTIST = "artist";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_PATH = "path";

    public static final String SCRIPT_CREATE_TABLE = new StringBuilder("CREATE TABLE ")
            .append(TABLE_NAME).append("(")
            .append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            .append(COLUMN_SONG_ID).append(" INTEGER , ")
            .append(COLUMN_TITLE).append(" TEXT,")
            .append(COLUMN_ARTIST).append(" TEXT,")
            .append(COLUMN_DURATION).append(" INTEGER,")
            .append(COLUMN_PATH).append(" TEXT ,")
            .append(" )")
            .toString();

    private static DatabaseManager mDatabaseManager = DatabaseManager.getInstance();
    private static SongListDbAdapter songListDBAdapterInstance;

    private SongListDbAdapter(DatabaseManager databaseManager) {}

    public static SongListDbAdapter getSongListAdapterInstance(DatabaseManager databaseManager){
        if(songListDBAdapterInstance == null){
            songListDBAdapterInstance = new SongListDbAdapter(databaseManager);
        }
        return songListDBAdapterInstance;
    }

    public ArrayList<Song> getAllAudioFromDevice() {
        final ArrayList<Song> tempAudioList = new ArrayList<>();
        final Context context = mDatabaseManager.getContext();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {

                MediaStore.Audio.AudioColumns.DATA,//path
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.ArtistColumns.ARTIST,
                MediaStore.Audio.AudioColumns.DURATION,
                MediaStore.Audio.AudioColumns._ID,

        };
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 ";
        Cursor c = context.getContentResolver().query(uri, projection, selection, null, null);//select .. from audio
        int debugLoop = 40;
        if (c != null) {
            int count = 0;

            while (c.moveToNext()) {
                count++;
//                Log.d(TAG, "getAllAudioFromDevice: " + count);
                Song song = new Song();
                String path = c.getString(0);//c.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)
                String name = c.getString(1);//c.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE)
                String artist = c.getString(2);//c.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)
                Long duration = c.getLong(3);//c.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)
                int songId = c.getInt(4);
                song.setTitle(name);
                song.setArtist(artist);
                song.setPath(path);
                song.setBitmap(null);
                song.setDuration(duration);
                song.setSongId(songId);
                tempAudioList.add(song);
            }
            c.close();
        }

        return tempAudioList;
    }

    public String formatMilliSecond(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        //      return  String.format("%02d Min, %02d Sec",
        //                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
        //                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
        //                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));

        // return timer string
        return finalTimerString;
    }

    public long insertSong(Song song) {

        if (!isSongExist(song)) {
            SQLiteDatabase database = mDatabaseManager.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(SongListDbAdapter.COLUMN_SONG_ID, song.getSongId());
            contentValues.put(SongListDbAdapter.COLUMN_TITLE, song.getTitle());
            contentValues.put(SongListDbAdapter.COLUMN_ARTIST, song.getArtist());
            contentValues.put(SongListDbAdapter.COLUMN_DURATION, song.getDuration());
            contentValues.put(SongListDbAdapter.COLUMN_PATH, song.getPath());
            //            database.close();
            return database.insert(SongListDbAdapter.TABLE_NAME, null, contentValues);
        }
        return 0;
    }

    public void deleteAllSong() {
        SQLiteDatabase database = mDatabaseManager.getWritableDatabase();
        database.delete(TABLE_NAME, null, null);
    }

    public boolean isSongExist(Song song) {
        SQLiteDatabase db = mDatabaseManager.getReadableDatabase();
        boolean result = false;
        String query = MessageFormat.format("SELECT {0} FROM {1} WHERE {2}={3} ",
                (Object) new String[]{SongListDbAdapter.COLUMN_ID, SongListDbAdapter.TABLE_NAME, SongListDbAdapter.COLUMN_SONG_ID, String.valueOf(song.getSongId())});
        String strSQL = "SELECT "+ SongListDbAdapter.COLUMN_TITLE+ " FROM "+ SongListDbAdapter.TABLE_NAME + " WHERE "+SongListDbAdapter.COLUMN_SONG_ID+" = "+ song.getSongId();
        //SQLiteDatabase db = MainActivity.mDatabaseManager.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(strSQL,null);;
        if (cursor.moveToFirst()) {
            result = true;
        }
        //databaseManager.closeDatabase();
        return result;
    }

    public long getRowsSong() {
        SQLiteDatabase db = mDatabaseManager.getReadableDatabase();

        return DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songModelList = new ArrayList<>();
        SQLiteDatabase db = mDatabaseManager.getReadableDatabase();
        String[] projection = {
                SongListDbAdapter.COLUMN_ID,
                SongListDbAdapter.COLUMN_SONG_ID,
                SongListDbAdapter.COLUMN_TITLE,
                SongListDbAdapter.COLUMN_DURATION,
                SongListDbAdapter.COLUMN_ARTIST,
                SongListDbAdapter.COLUMN_PATH
        };
        Cursor cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Song song = new Song();
                song.setId(cursor.getInt(cursor.getColumnIndex(SongListDbAdapter.COLUMN_ID)));
                song.setSongId(cursor.getInt(cursor.getColumnIndex(SongListDbAdapter.COLUMN_SONG_ID)));
                song.setTitle(cursor.getString(cursor.getColumnIndex(SongListDbAdapter.COLUMN_TITLE)));
                song.setArtist(cursor.getString(cursor.getColumnIndex(SongListDbAdapter.COLUMN_ARTIST)));
                song.setDuration(cursor.getLong(cursor.getColumnIndex(SongListDbAdapter.COLUMN_DURATION)));
                song.setPath(cursor.getString(cursor.getColumnIndex(SongListDbAdapter.COLUMN_PATH)));
                songModelList.add(song);
            } while (cursor.moveToNext());

        }
        //databaseManager.closeDatabase();
        return songModelList;
    }
}


