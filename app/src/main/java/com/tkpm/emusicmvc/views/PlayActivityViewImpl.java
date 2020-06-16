package com.tkpm.emusicmvc.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emusic.R;
import com.tkpm.emusicmvc.MyApplication;
import com.tkpm.emusicmvc.controllers.PlayActivityController;
import com.tkpm.emusicmvc.models.Song;
import com.tkpm.emusicmvc.models.repositories.SongListRepository;

public class PlayActivityViewImpl implements IPlayActivityView {
    View rootView;
    PlayActivityController playActivityController;
    private SongListRepository playActivityModel;

    TextView textViewTitle, textViewArtist;

    Song song;
    int songId;

    public PlayActivityViewImpl(Context context, ViewGroup container, Intent intent) {
        rootView = LayoutInflater.from(context).inflate(R.layout.player, container);
        playActivityModel = new SongListRepository(MyApplication.getSongListDbAdapter());
        songId = intent.getIntExtra("songId",1);
        playActivityModel = new SongListRepository(MyApplication.getSongListDbAdapter());
        playActivityController = new PlayActivityController(playActivityModel,this);
    }

    @Override
    public void initViews() {
        textViewTitle = (TextView)rootView.findViewById(R.id.tv_media_title_detail);
        textViewArtist = (TextView)rootView.findViewById(R.id.tv_media_artist_detail);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void showSelectedSong() {
        try {
            song = playActivityModel.getSong(songId);
            textViewTitle.setText(song.getTitle());
            textViewArtist.setText(song.getArtist());
        } catch (Exception ex){
            Toast.makeText(rootView.getContext(),ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void bindDataToView() {
        playActivityController.onViewLoaded();
    }


}
