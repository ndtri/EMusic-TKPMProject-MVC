package com.tkpm.emusicmvc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.example.emusic.R;
import com.tkpm.emusicmvc.models.Song;
import com.tkpm.emusicmvc.models.db.DatabaseManager;
import com.tkpm.emusicmvc.models.db.PlaylistDBAdapter;
import com.tkpm.emusicmvc.models.db.SongListDbAdapter;
import com.tkpm.emusicmvc.views.BouncingImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private BouncingImageView imgAngryPlaylist;
    private BouncingImageView imgSadPlaylist;
    private BouncingImageView imgRelaxPlaylist;
    private BouncingImageView imgHappyPlaylist;
    private BouncingImageView imgDepressPlaylist;
    private BouncingImageView imgSleepyPlaylist;
    private BouncingImageView imgRapPlaylist;
    private BouncingImageView imgEDMPlaylist;
    private BouncingImageView imgRockPlaylist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgAngryPlaylist = findViewById(R.id.imgAngryPlaylist);
        imgSadPlaylist = findViewById(R.id.imgSadPlaylist);
        imgRelaxPlaylist = findViewById(R.id.imgRelaxPlaylist);
        imgAngryPlaylist.setOnClickListener(this::onClick);
        imgSadPlaylist.setOnClickListener(this::onClick);
        imgRelaxPlaylist.setOnClickListener(this::onClick);

    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, PlayActivity.class);
        switch (v.getId()){
            case R.id.imgAngryPlaylist:
                intent.putExtra("PLAYLIST_ID", 1);
                startActivity(intent);
                break;
            case R.id.imgSadPlaylist:
                intent.putExtra("PLAYLIST_ID", 9);
                startActivity(intent);
                break;
            case R.id.imgRelaxPlaylist:
                intent.putExtra("PLAYLIST_ID", 6);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
