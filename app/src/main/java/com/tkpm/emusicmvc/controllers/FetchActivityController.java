package com.tkpm.emusicmvc.controllers;

import com.tkpm.emusicmvc.models.repositories.SongListRepository;
import com.tkpm.emusicmvc.views.FetchActivityViewImpl;

public class FetchActivityController implements IController {
    SongListRepository songListModel;
    FetchActivityViewImpl songListView;

    public FetchActivityController(SongListRepository songListModel, FetchActivityViewImpl songListView){
        this.songListModel = songListModel;
        this.songListView = songListView;
    }

    @Override
    public void onViewLoaded() {
        try {
            songListView.showAllSongs(songListModel.getSongList());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onSongSelected(long songId){
        songListView.navigateToPlayActivity(songId);
    }
}
