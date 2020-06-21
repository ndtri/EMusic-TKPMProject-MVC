package com.tkpm.emusicmvc.controllers;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;

import com.tkpm.emusicmvc.models.Song;
import com.tkpm.emusicmvc.models.repositories.SongListRepository;
import com.tkpm.emusicmvc.views.PlayActivityViewImpl;

import java.io.IOException;
import java.util.ArrayList;

public class PlayActivityController implements IController, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    SongListRepository songListModel;
    PlayActivityViewImpl songView;

    private ArrayList<Song> audioList;
    private static Song curSong;
    private int position;

    private static boolean isPause;
    private static MediaPlayer mediaPlayer = null;
    private CountDownTimer countDownTimerUpdateSeekBar = null;


    public static final int LOOP_NONE = 1;
    public static final int LOOP_ALL = 2;
    public static final int LOOP_ONE = 3;

    public static final int ACTION_FROM_USER = 1;

    public PlayActivityController(SongListRepository songListModel, PlayActivityViewImpl songView){
        this.songListModel = songListModel;
        this.songView = songView;
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void onViewLoaded() {
        songView.updateControlPlaying(curSong);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

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
//
        if (songView != null) {
            songView.updateButtonPlay();
        }
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

    public void play(final Song song) {
        isPause = false;
        try {
            position = 0;
            curSong = song;
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
            if(position == audioList.size()){
                position = -1;
            }
            curSong = audioList.get(position + 1);
            mediaPlayer.setDataSource(curSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (Exception ex){

        }
    }
    public void previous() {
        if (mediaPlayer!= null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        try {
            if(position == 0){
                position = audioList.size() + 1;
            }
            curSong = audioList.get(position - 1);
            mediaPlayer.setDataSource(curSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (Exception ex){

        }
    }
}
