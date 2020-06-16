package com.tkpm.emusicmvc.views;

import com.tkpm.emusicmvc.models.Song;

import java.util.ArrayList;

public interface IFetchActivityView extends IView {
    public void showAllSongs(ArrayList<Song> songList);
    public void navigateToPlayActivity(long songId);
}
