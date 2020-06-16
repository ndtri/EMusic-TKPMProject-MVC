package com.tkpm.emusicmvc;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        appView.bindDataToView();
    }
}
