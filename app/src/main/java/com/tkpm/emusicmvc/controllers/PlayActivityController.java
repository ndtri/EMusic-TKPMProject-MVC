package com.tkpm.emusicmvc.controllers;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.emusic.BuildConfig;
import com.tkpm.emusicmvc.models.PlaylistSongModel;
import com.tkpm.emusicmvc.models.Song;
import com.tkpm.emusicmvc.models.repositories.SongListRepository;
import com.tkpm.emusicmvc.views.PlayActivityViewImpl;

import java.io.IOException;
import java.util.ArrayList;

public class PlayActivityController implements IController, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    SongListRepository songListModel;
    PlayActivityViewImpl songView;

    private static ArrayList<Song> audioList;
    private static Song curSong;
    private int position;
    private int playlist_id;

    private static boolean isPause;
    private static boolean isStopped = false;
    private static MediaPlayer mediaPlayer = null;
    private CountDownTimer countDownTimerUpdateSeekBar = null;


    public static final int LOOP_NONE = 1;
    public static final int LOOP_ALL = 2;
    public static final int LOOP_ONE = 3;

    public static final int ACTION_FROM_USER = 1;

    public PlayActivityController(SongListRepository songListModel, PlayActivityViewImpl songView, int playlist_id){
        this.songListModel = songListModel;
        this.songView = songView;
        this.playlist_id = playlist_id;
        audioList = PlaylistSongModel.getAllSongFromPlaylistId(playlist_id);
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
    }

    @Override
    public void onViewLoaded() {
        //songView.updateControlPlaying(waitingSongs.get(0));
        songView.updateControlPlaying(curSong);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (!mediaPlayer.isPlaying()) {
            countDownTimerUpdateSeekBar = new CountDownTimer(curSong.getDuration(), 1000) {
                public void onTick(long millisUntilFinished) {
                    try {
                        if (mediaPlayer.isPlaying()) {
                            songView.updateSeekBar(mediaPlayer.getCurrentPosition());
                        }

                    } catch (IllegalStateException ex) {
                        ex.printStackTrace();
                    }
                }

                public void onFinish() {

                }
            }.start();

        }
        mp.start();

        if (songView != null) {
            songView.updateButtonPlay();
            //songView.updateControlPlaying(waitingSongs.get(0));
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        //                songView.updateButtonPlay();
//                mediaPlayer.stop();
        stopPlaying();
        countDownTimerUpdateSeekBar.cancel();
        mediaPlayer = new MediaPlayer();
        next();
    }

    public void updateDuration(int progress) {
        mediaPlayer.seekTo(progress * 1000);
    }

//    public void updateSeekBar(int duration) {
//        if (songView != null) {
//            songView.updateSeekBar(duration);
//        }
//    }

    public static boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public static boolean isPause() {
        return isPause;
    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public int findCurSongIndex(Song curSong) {
        for (int i = 0; i < audioList.size(); i++) {
            if (audioList.get(i).getSongId() == curSong.getSongId()) {
                return i;
            }
        }
        return -1;
    }

    public void play(final Song song) {
        isPause = false;
        //isStopped = false;

        try {
            curSong = song;
            position = findCurSongIndex(curSong);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(curSong.getPath());
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.prepareAsync();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void pause() {
        mediaPlayer.pause();
        isPause = true;
    }

    public void resume() {
        isPause = false;
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
        mediaPlayer.start();

    }


    public void next() {
        if (mediaPlayer!= null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        try {
            if(position == audioList.size() - 1){
                position = -1;
            }
            curSong = audioList.get(++position);
            songView.updateControlPlaying(curSong);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(curSong.getPath());
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            //mediaPlayer.start();
            mediaPlayer.prepareAsync();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void previous() {
        if (mediaPlayer!= null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        try {
            if(position == 0){
                position = audioList.size();
            }
            curSong = audioList.get(--position);
            songView.updateControlPlaying(curSong);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(curSong.getPath());
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.prepareAsync();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
