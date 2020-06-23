package com.tkpm.emusicmvc;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emusic.R;
import com.tkpm.emusicmvc.controllers.PlayActivityController;
import com.tkpm.emusicmvc.models.PlaylistSongModel;
import com.tkpm.emusicmvc.models.Song;
import com.tkpm.emusicmvc.views.IView;
import com.tkpm.emusicmvc.views.ViewFactory;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {
    IView appView;
    int playlist_id;
    private ArrayList<Song> audioList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        playlist_id = getIntent().getExtras().getInt("PLAYLIST_ID");
        audioList = PlaylistSongModel.getAllSongFromPlaylistId(playlist_id);

        //for(Song song: audioList) {
            try {
                appView = ViewFactory.getMVCView(ViewFactory.VIEW_TYPE.PLAY_VIEW_TYPE, PlayActivity.this, null, getIntent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            setContentView(appView.getRootView());

            try {
                appView.initViews();
            } catch (Exception e) {
                e.printStackTrace();
            }
        //}


    }


    @Override
    protected void onResume() {
        super.onResume();
        appView.bindDataToView();
    }
}
