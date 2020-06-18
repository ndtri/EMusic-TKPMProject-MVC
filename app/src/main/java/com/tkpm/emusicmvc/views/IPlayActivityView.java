package com.tkpm.emusicmvc.views;

import com.tkpm.emusicmvc.models.Song;

public interface IPlayActivityView extends IView {
    public void updateControlPlaying(Song song);
    public void updateSeekBar(int currentDuration);
    public void updateButtonPlay();

}
