package com.albums.myalbums.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.albums.myalbums.R;
import com.albums.myalbums.persistence.model.Album;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class AlbumViewAdapter extends RecyclerView.Adapter<AlbumViewAdapter.RecyclerViewHolder> {

    private List<Album> albumList;

    public AlbumViewAdapter(List<Album> albums) {
        this.albumList = albums;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.itemTextView.setText(album.getUserId()+"");
        holder.titleTextView.setText(album.getTitle());
        holder.itemView.setTag(album);
    }

    @Override
    public int getItemCount() {
        if(albumList == null){
            return 0;
        }
        return albumList.size();
    }

    public void addItems(List<Album> albums) {
        this.albumList = albums;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTextView;
        private TextView titleTextView;

        RecyclerViewHolder(View view) {
            super(view);
            itemTextView = view.findViewById(R.id.album_user_id);
            titleTextView = view.findViewById(R.id.album_title);
        }
    }
}
