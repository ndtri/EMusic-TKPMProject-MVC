package com.tkpm.emusicmvc.views;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emusic.R;
import com.tkpm.emusicmvc.MyApplication;
import com.tkpm.emusicmvc.PlayActivity;
import com.tkpm.emusicmvc.controllers.FetchActivityController;
import com.tkpm.emusicmvc.controllers.PlayActivityController;
import com.tkpm.emusicmvc.models.Song;
import com.tkpm.emusicmvc.models.repositories.Observer;
import com.tkpm.emusicmvc.models.repositories.SongListRepository;

import java.util.ArrayList;

public class FetchActivityViewImpl implements IFetchActivityView, SongAdapter.ListItemClickListener {
    View rootView;
    FetchActivityController fetchActivityController;
    private SongListRepository fetchActivityModel;

    private RecyclerView recyclerView;


    SongAdapter songAdapter;

    public FetchActivityViewImpl(Context context, ViewGroup container) {
        rootView = LayoutInflater.from(context).inflate(R.layout.activity_fetch,container);
        fetchActivityModel = new SongListRepository(MyApplication.getSongListDbAdapter());

        fetchActivityController = new FetchActivityController(fetchActivityModel, this);
    }

    @Override
    public void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView = rootView.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void bindDataToView() {
        fetchActivityController.onViewLoaded();
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void showAllSongs(ArrayList<Song> songList) {
        songAdapter = new SongAdapter(rootView.getContext(), songList, this);
        songAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(songAdapter);
    }

    @Override
    public void onItemClicked(long position) {
        fetchActivityController.onSongSelected(position);
    }

    @Override
    public void navigateToPlayActivity(long songId){
        Intent intent = new Intent(rootView.getContext(), PlayActivity.class);
        intent.putExtra("songId", songId);
        rootView.getContext().startActivity(intent);
    }
}
