package com.tkpm.emusicmvc.models.repositories;

import com.tkpm.emusicmvc.models.Song;

import java.util.ArrayList;

public interface ISongListRepo {
    public ArrayList<Song> getSongList() throws Exception;
    public Song getSong(int sondId) throws Exception;
}
