package com.tkpm.emusicmvc.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emusic.R;
import com.tkpm.emusicmvc.models.Song;

import java.util.ArrayList;


public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private Context context;
    private ArrayList<Song> songs;
    ListItemClickListener listItemClickListener;

    public interface ListItemClickListener{
        void onItemClicked(long position);
    }

    public SongAdapter(Context context, ArrayList<Song> songs, ListItemClickListener listItemClickListener) {
        this.context = context;
        this.songs = songs;
        this.listItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public SongAdapter.SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_model, parent, false);

        return new SongViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.rv_model;
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.SongViewHolder holder, int position) {
        final Song song = songs.get(position);
        holder.textViewTitle.setText(song.getTitle());
        holder.textViewArtist.setText(song.getArtist());
        holder.layoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listItemClickListener != null){
                    listItemClickListener.onItemClicked(song.getSongId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    static class SongViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout layoutContainer;
        public TextView textViewTitle, textViewArtist;

        public SongViewHolder(View view){
            super(view);
            layoutContainer = view.findViewById(R.id.layoutContainer);
            textViewTitle = view.findViewById(R.id.tv_media_title);
            textViewArtist = view.findViewById(R.id.tv_media_artist);
        }


    }
}


