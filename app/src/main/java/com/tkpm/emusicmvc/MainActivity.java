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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.emusic.R;
import com.tkpm.emusicmvc.models.Song;
import com.tkpm.emusicmvc.models.db.DatabaseManager;
import com.tkpm.emusicmvc.models.db.PlaylistDBAdapter;
import com.tkpm.emusicmvc.models.db.SongListDbAdapter;
import com.tkpm.emusicmvc.views.BouncingImageView;

import java.nio.channels.FileLock;
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
    private BouncingImageView imgWorkoutPlaylist;
    private BouncingImageView imgRapPlaylist;
    private BouncingImageView imgEDMPlaylist;
    private BouncingImageView imgRockPlaylist;
    private EditText edtSearchPlaylist;
    private RadioGroup btnFilter;
    private RadioButton btnRedFilter;
    private RadioButton btnGreenFilter;
    private RadioButton btnOrangeFilter;

    static final String RED_FILTER = "RED";
    static final String ORANGE_FILTER = "ORANGE";
    static final String GREEN_FILTER = "GREEN";
    ArrayList<BouncingImageView> imgPlaylistList = new ArrayList<>();
    static String colorFilter = "NONE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgAngryPlaylist = findViewById(R.id.imgAngryPlaylist);
        imgPlaylistList.add(imgAngryPlaylist);
        imgSadPlaylist = findViewById(R.id.imgSadPlaylist);
        imgPlaylistList.add(imgSadPlaylist);
        imgRelaxPlaylist = findViewById(R.id.imgRelaxPlaylist);
        imgPlaylistList.add(imgRelaxPlaylist);
        imgDepressPlaylist = findViewById(R.id.imgDepressPlaylist);
        imgPlaylistList.add(imgDepressPlaylist);
        imgHappyPlaylist = findViewById(R.id.imgHappyPlaylist);
        imgPlaylistList.add(imgHappyPlaylist);
        imgEDMPlaylist = findViewById(R.id.imgEDMPlaylist);
        imgPlaylistList.add(imgEDMPlaylist);
        imgRapPlaylist = findViewById(R.id.imgRapPlaylist);
        imgPlaylistList.add(imgRapPlaylist);
        imgRockPlaylist = findViewById(R.id.imgRockPlaylist);
        imgPlaylistList.add(imgRockPlaylist);
        imgWorkoutPlaylist = findViewById(R.id.imgWorkoutPlaylist);
        imgPlaylistList.add(imgWorkoutPlaylist);

        edtSearchPlaylist = findViewById(R.id.edtSearchPlaylist);
        btnFilter = findViewById(R.id.btnFilter);
        btnGreenFilter = findViewById(R.id.btnGreenFilter);
        btnOrangeFilter = findViewById(R.id.btnOrangeFilter);
        btnRedFilter = findViewById(R.id.btnRedFilter);
        edtSearchPlaylist = findViewById(R.id.edtSearchPlaylist);

        imgWorkoutPlaylist.setColor(RED_FILTER);
        imgWorkoutPlaylist.setName(getResources().getString(R.string.playlist_workout));

        imgAngryPlaylist.setColor(RED_FILTER);
        imgAngryPlaylist.setName(getResources().getString(R.string.playlist_angry));

        imgRockPlaylist.setColor(RED_FILTER);
        imgRockPlaylist.setName(getResources().getString(R.string.playlist_rock));

        imgDepressPlaylist.setColor(ORANGE_FILTER);
        imgDepressPlaylist.setName(getResources().getString(R.string.playlist_depress));

        imgRapPlaylist.setColor(ORANGE_FILTER);
        imgRapPlaylist.setName(getResources().getString(R.string.playlist_rap));

        imgSadPlaylist.setColor(ORANGE_FILTER);
        imgSadPlaylist.setName(getResources().getString(R.string.playlist_sad));

        imgRelaxPlaylist.setColor(GREEN_FILTER);
        imgRelaxPlaylist.setName(getResources().getString(R.string.playlist_relax));

        imgHappyPlaylist.setColor(GREEN_FILTER);
        imgHappyPlaylist.setName(getResources().getString(R.string.playlist_happy));

        imgEDMPlaylist.setColor(GREEN_FILTER);
        imgEDMPlaylist.setName(getResources().getString(R.string.playlist_edm));

        imgAngryPlaylist.setOnClickListener(this::onClick);
        imgSadPlaylist.setOnClickListener(this::onClick);
        imgRelaxPlaylist.setOnClickListener(this::onClick);
        imgDepressPlaylist.setOnClickListener(this::onClick);
        imgHappyPlaylist.setOnClickListener(this::onClick);
        imgEDMPlaylist.setOnClickListener(this::onClick);
        imgRapPlaylist.setOnClickListener(this::onClick);
        imgRockPlaylist.setOnClickListener(this::onClick);
        imgWorkoutPlaylist.setOnClickListener(this::onClick);
        btnRedFilter.setOnClickListener(this::onClick);
        btnOrangeFilter.setOnClickListener(this::onClick);
        btnGreenFilter.setOnClickListener(this::onClick);
        edtSearchPlaylist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Search(s.toString());
            }
        });

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
            case R.id.imgHappyPlaylist:
                intent.putExtra("PLAYLIST_ID", 2);
                startActivity(intent);
                break;
            case R.id.imgWorkoutPlaylist:
                intent.putExtra("PLAYLIST_ID", 3);
                startActivity(intent);
                break;
            case R.id.imgRockPlaylist:
                intent.putExtra("PLAYLIST_ID", 4);
                startActivity(intent);
                break;
            case R.id.imgDepressPlaylist:
                intent.putExtra("PLAYLIST_ID", 5);
                startActivity(intent);
                break;
            case R.id.imgRelaxPlaylist:
                intent.putExtra("PLAYLIST_ID", 6);
                startActivity(intent);
                break;
            case R.id.imgRapPlaylist:
                intent.putExtra("PLAYLIST_ID", 7);
                startActivity(intent);
                break;
            case R.id.imgEDMPlaylist:
                intent.putExtra("PLAYLIST_ID", 8);
                startActivity(intent);
            case R.id.imgSadPlaylist:
                intent.putExtra("PLAYLIST_ID", 9);
                startActivity(intent);
                break;
            case R.id.btnGreenFilter:
                Filter(GREEN_FILTER);
                break;
            case R.id.btnRedFilter:
                Filter(RED_FILTER);
                break;
            case R.id.btnOrangeFilter:
                Filter(ORANGE_FILTER);
                break;
            default:
                break;
        }
    }

    public void Filter(String color){
        if (colorFilter.equals(color)) {
            btnFilter.clearCheck();
            colorFilter = "NONE";
            for (BouncingImageView imgPlaylist : imgPlaylistList)
                imgPlaylist.setVisibility(View.VISIBLE);
        } else {
            colorFilter = color;
            for (BouncingImageView imgPlaylist : imgPlaylistList) {
                if (imgPlaylist.getColor().equals(colorFilter))
                    imgPlaylist.setVisibility(View.VISIBLE);
                else
                    imgPlaylist.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void Search(String name){
        for (BouncingImageView imgPlaylist : imgPlaylistList) {
            if (imgPlaylist.getName().toLowerCase().contains(name.toLowerCase()))
                imgPlaylist.setVisibility(View.VISIBLE);
            else
                imgPlaylist.setVisibility(View.INVISIBLE);
        }
    }

}
