package com.tkpm.emusicmvc.controllers;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.emusic.BuildConfig;
import com.tkpm.emusicmvc.models.Song;
import com.tkpm.emusicmvc.models.repositories.SongListRepository;
import com.tkpm.emusicmvc.views.PlayActivityViewImpl;

import java.io.IOException;
import java.util.ArrayList;

public class PlayActivityController implements IController, MediaPlayer.OnPreparedListener {
    SongListRepository songListModel;
    PlayActivityViewImpl songView;

    private static ArrayList<Song> waitingSongs = new ArrayList<>();
    private static Song curSong;

    private static boolean isPause;
    private static boolean isStopped = false;
    private static MediaPlayer mediaPlayer = null;
    private CountDownTimer countDownTimerUpdateSeekBar = null;


    public static final int LOOP_NONE = 1;
    public static final int LOOP_ALL = 2;
    public static final int LOOP_ONE = 3;

    public static final int ACTION_FROM_USER = 1;

    public PlayActivityController(SongListRepository songListModel, PlayActivityViewImpl songView){
        this.songListModel = songListModel;
        this.songView = songView;
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
    }

    @Override
    public void onViewLoaded() {
        //songView.updateControlPlaying(waitingSongs.get(0));
        songView.updateControlPlaying(curSong);
    }

//    @Override
//    public void onCompletion(MediaPlayer mp) {
//
//    }

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

    public void updateDuration(int progress) {
        mediaPlayer.seekTo(progress * 1000);
    }

//    public void updateSeekBar(int duration) {
//        if (songView != null) {
//            songView.updateSeekBar(duration);
//        }
//    }

    public static boolean isPlaying() {
//        if (isStopped) {
//            return false;
//        }
        return mediaPlayer.isPlaying();
    }

    public static boolean isPause() {
//        if (isStopped) {
//            return true;
//        }
        return isPause;
    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            isStopped = true;
            mediaPlayer.reset();
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void play(final Song song) {
        isPause = false;
        isStopped = false;

        try {
            curSong = song;
            mediaPlayer.reset();
            mediaPlayer.setDataSource(curSong.getPath());
            mediaPlayer.setOnPreparedListener(this);
            //mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(mp -> {
                //countDownTimerUpdateSeekBar.cancel();
                songView.updateButtonPlay();
//                stopPlaying();
                mediaPlayer.stop();
                //isStopped = true;
                //mediaPlayer.pause();
                //mediaPlayer = new MediaPlayer();

                //songView.updateControlPlaying(curSong);
            });
            mediaPlayer.prepareAsync();

//            if (curSong != waitingSongs.get(0)) {
//                //stopPlaying();
//                curSong = waitingSongs.get(0);
//                mediaPlayer = new MediaPlayer();
//                //mediaPlayer.reset();
//                mediaPlayer.setDataSource(curSong.getPath());
//                mediaPlayer.setOnPreparedListener(this);
//                mediaPlayer.start();
//
//            }
//
//            mediaPlayer.setOnCompletionListener(mp -> {
//                stopPlaying();
//                waitingSongs.remove(0);
//                curSong = waitingSongs.get(0);
//                //songView.updateControlPlaying(curSong);
//                mediaPlayer = new MediaPlayer();
//                try {
//                    mediaPlayer.setDataSource(curSong.getPath());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                mediaPlayer.setOnPreparedListener(this);
//                mediaPlayer.start();
//                //songView.updateControlPlaying(curSong);
//            });
            //mediaPlayer.setOnCompletionListener(this);

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
}
