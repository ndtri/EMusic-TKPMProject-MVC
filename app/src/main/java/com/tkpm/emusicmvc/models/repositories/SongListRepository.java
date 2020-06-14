package com.tkpm.emusicmvc.models.repositories;

import com.tkpm.emusicmvc.models.Song;
import com.tkpm.emusicmvc.models.db.DatabaseManager;
import com.tkpm.emusicmvc.models.db.SongListDbAdapter;

import java.util.ArrayList;
import java.util.List;

public class SongListRepository implements ISongListRepo {

    SongListDbAdapter songListDBAdapter;
    ArrayList<Song> audioList;

    List<Observer> observers;


    public SongListRepository(SongListDbAdapter songListDBAdapter) {
        this.songListDBAdapter = songListDBAdapter;
        audioList = this.songListDBAdapter.getAllSongs();
        observers = new ArrayList<Observer>();
    }

    public void registerObserver(Observer observer){
        observers.add(observer);
    }

    private void notifyObservers(){
        for(Observer observer: observers){
            observer.update();
        }
    }

    @Override
    public ArrayList<Song> getSongList() throws Exception {
        if(this.audioList != null && this.audioList.size() > 0){
            return this.audioList;
        } else {
            throw new Exception("Empty list!");
        }
    }

}
