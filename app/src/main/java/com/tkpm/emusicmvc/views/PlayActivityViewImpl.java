package com.tkpm.emusicmvc.views;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emusic.R;
import com.tkpm.emusicmvc.MyApplication;
import com.tkpm.emusicmvc.controllers.PlayActivityController;
import com.tkpm.emusicmvc.models.Song;
import com.tkpm.emusicmvc.models.db.SongListDbAdapter;
import com.tkpm.emusicmvc.models.repositories.SongListRepository;

public class PlayActivityViewImpl implements IPlayActivityView, View.OnClickListener {
    View rootView;
    PlayActivityController playActivityController;
    private SongListRepository playActivityModel;

    TextView textViewTitle, textViewArtist;
    TextView txtCurrentTime, txtTotalTime;
    ImageButton playBtn;
    ImageButton nextBtn;
    ImageButton prevBtn;
    ImageButton shuffleBtn;
    ImageButton repeatBtn;
    SeekBar seekBarDurationSong;

    Song song;
    long msongId;

    public PlayActivityViewImpl(Context context, ViewGroup container, Intent intent) throws Exception {
        rootView = LayoutInflater.from(context).inflate(R.layout.player, container);
        playActivityModel = new SongListRepository(MyApplication.getSongListDbAdapter());
        msongId = intent.getLongExtra("songId",1);
        playActivityModel = new SongListRepository(MyApplication.getSongListDbAdapter());
        playActivityController = new PlayActivityController(playActivityModel,this);
        song = playActivityModel.getSong(msongId);
        playActivityController.play(song);
    }

    public PlayActivityViewImpl(Context context, ViewGroup container, Intent intent, long songId) throws Exception {
        rootView = LayoutInflater.from(context).inflate(R.layout.player, container);
        playActivityModel = new SongListRepository(MyApplication.getSongListDbAdapter());
        playActivityModel = new SongListRepository(MyApplication.getSongListDbAdapter());
        //msongId = intent.getLongExtra("songId", 1);
        playActivityController = new PlayActivityController(playActivityModel,this);
        song = playActivityModel.getSong(songId);
        playActivityController.play(song);
    }

    @Override
    public void initViews() {
        textViewTitle = rootView.findViewById(R.id.tv_media_title_detail);
        textViewArtist = rootView.findViewById(R.id.tv_media_artist_detail);
        txtCurrentTime = rootView.findViewById(R.id.txtCurrentTime);
        txtTotalTime = rootView.findViewById(R.id.txtTotalTime);
        playBtn = rootView.findViewById(R.id.btnPlay);
        nextBtn = rootView.findViewById(R.id.btnNext);
        prevBtn = rootView.findViewById(R.id.btnPrevious);
        repeatBtn = rootView.findViewById(R.id.btnRepeat);
        shuffleBtn = rootView.findViewById(R.id.btnShuffle);
        seekBarDurationSong = rootView.findViewById(R.id.seekbarPlayer);

        playBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        repeatBtn.setOnClickListener(this);
        shuffleBtn.setOnClickListener(this);

        seekBarDurationSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //playActivityController.updateDuration(progress);
                if (fromUser) {
                    playActivityController.updateDuration(progress);
                    if (!PlayActivityController.isPlaying()) {
                        playActivityController.resume();
                        setButtonPause();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        updateControlPlaying(song);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void updateControlPlaying(Song curSong) {
        try {
            curSong = song;
            textViewTitle.setText(curSong.getTitle());
            textViewArtist.setText(curSong.getArtist());
            txtTotalTime.setText(SongListDbAdapter.formatMilliSecond(curSong.getDuration()));
            seekBarDurationSong.setMax(curSong.getDuration().intValue() / 1000);
        } catch (Exception ex){
            Toast.makeText(rootView.getContext(),ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void updateSeekBar(int currentDuration) {
        seekBarDurationSong.setProgress(currentDuration / 1000);
        txtCurrentTime.setText(SongListDbAdapter.formatMilliSecond(currentDuration));
    }

    @Override
    public void updateButtonPlay() {
        if (PlayActivityController.isPlaying()) {
            setButtonPause();
        } else {
            setButtonPlay();
        }
    }

    @Override
    public void bindDataToView() {
        playActivityController.onViewLoaded();
    }

    private void setButtonPlay() {
        playBtn.setImageResource(R.drawable.ic_play);
    }

    private void setButtonPause() {
        playBtn.setImageResource(R.drawable.ic_pause);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:
                    Toast.makeText(rootView.getContext(), "PLAY CLICK", Toast.LENGTH_SHORT).show();
                if (PlayActivityController.isPlaying()) {// song is playing then stop
                    playActivityController.pause();
                    setButtonPlay();
                } else if (PlayActivityController.isPause()) { //resume
                    playActivityController.resume();
                    setButtonPause();
                } else {
                    playActivityController.play(song);
                    setButtonPause();
                }

                break;
            case R.id.btnNext:
                break;
            case R.id.btnPrevious:
                break;
            default:
                break;
        }
    }
}
