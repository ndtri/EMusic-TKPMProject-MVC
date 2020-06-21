package com.tkpm.emusicmvc;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emusic.R;
import com.tkpm.emusicmvc.views.IView;
import com.tkpm.emusicmvc.views.ViewFactory;

public class PlayActivity extends AppCompatActivity {
    IView appView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appView = ViewFactory.getMVCView(ViewFactory.VIEW_TYPE.PLAY_VIEW_TYPE, PlayActivity.this, null, getIntent());
        setContentView(appView.getRootView());
        appView.initViews();

        int playlist_id = getIntent().getExtras().getInt("PLAYLIST_ID");
        TextView tv_media_title_detail = findViewById(R.id.tv_media_artist_detail);
        tv_media_title_detail.setText(playlist_id+"");
    }


    @Override
    protected void onResume() {
        super.onResume();
        appView.bindDataToView();
    }
}
