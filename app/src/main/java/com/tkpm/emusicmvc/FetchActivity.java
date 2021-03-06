package com.tkpm.emusicmvc;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tkpm.emusicmvc.views.IView;
import com.tkpm.emusicmvc.views.ViewFactory;

public class FetchActivity extends AppCompatActivity {
    IView appView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            appView = ViewFactory.getMVCView(ViewFactory.VIEW_TYPE.FETCH_VIEW_TYPE, FetchActivity.this, null, getIntent(), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(appView.getRootView());
        try {
            appView.initViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        appView.bindDataToView();
    }
}
