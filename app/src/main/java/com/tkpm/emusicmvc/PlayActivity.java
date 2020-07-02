package com.tkpm.emusicmvc;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

        //for(Song song: audioList) {
            try {
                appView = ViewFactory.getMVCView(ViewFactory.VIEW_TYPE.PLAY_VIEW_TYPE, PlayActivity.this, null, getIntent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            setContentView(appView.getRootView());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        }

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
