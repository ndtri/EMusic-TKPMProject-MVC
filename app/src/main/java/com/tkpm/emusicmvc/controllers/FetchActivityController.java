package com.tkpm.emusicmvc.controllers;

import com.tkpm.emusicmvc.models.repositories.SongListRepository;
import com.tkpm.emusicmvc.views.FetchActivityViewImpl;

public class FetchActivityController implements IController {
    SongListRepository songListModel;
    FetchActivityViewImpl songListView;
    int playlist_id;

    public FetchActivityController(SongListRepository songListModel, FetchActivityViewImpl songListView, int playlist_id){
        this.songListModel = songListModel;
        this.songListView = songListView;
        this.playlist_id = playlist_id;
    }

    @Override
    public void onViewLoaded() {
        try {
            songListView.showPlaylistSongs(playlist_id);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onSongSelected(long songId){
        songListView.navigateToPlayActivity(songId);
    }

    public void onPlayAllClicked(int playlist_id) {
        //songListView.navigateToPlayActivity(playlist_id);
    }
}
