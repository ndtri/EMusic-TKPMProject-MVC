package com.tkpm.emusicmvc.models;

import android.graphics.Bitmap;

public class Song {
    private long id;
    private long songId;
    private String path;
    private String artist;
    private String title;
    private Bitmap bitmap;
    private Long duration;

    public Song() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(int song_id) {
        this.songId = song_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
