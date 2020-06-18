package com.tkpm.emusicmvc.views;

import android.view.View;

public interface IView {
    public View getRootView();
    public void initViews() throws Exception;
    public void bindDataToView();
}
