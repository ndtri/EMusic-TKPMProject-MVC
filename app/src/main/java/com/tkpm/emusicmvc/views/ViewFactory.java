package com.tkpm.emusicmvc.views;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

public class ViewFactory {
    public enum VIEW_TYPE{
        FETCH_VIEW_TYPE, PLAY_VIEW_TYPE
    }

    public static IView getMVCView(VIEW_TYPE viewType, Context context, ViewGroup viewGroup, Intent intent, long songId) throws Exception {
        IView views = null;
        switch (viewType) {
            case FETCH_VIEW_TYPE:
                views = new FetchActivityViewImpl(context, viewGroup);
                break;
            case PLAY_VIEW_TYPE:
                views = new PlayActivityViewImpl(context, viewGroup, intent, songId);
                break;
        }
        return views;
    }
}
