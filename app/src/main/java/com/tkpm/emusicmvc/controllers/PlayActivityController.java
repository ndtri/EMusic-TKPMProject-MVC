package com.tkpm.emusicmvc.controllers;

import com.tkpm.emusicmvc.models.repositories.SongListRepository;
import com.tkpm.emusicmvc.views.PlayActivityViewImpl;

public class PlayActivityController implements IController {
    SongListRepository songListModel;
    PlayActivityViewImpl songView;

    public PlayActivityController(SongListRepository songListModel, PlayActivityViewImpl songView){
        this.songListModel = songListModel;
        this.songView = songView;
    }

    @Override
    public void onViewLoaded() {
        songView.showSelectedSong();
    }
}
