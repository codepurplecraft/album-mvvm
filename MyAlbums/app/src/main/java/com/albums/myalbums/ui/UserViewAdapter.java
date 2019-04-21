package com.albums.myalbums.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.albums.myalbums.R;
import com.albums.myalbums.persistence.model.Album;
import com.albums.myalbums.persistence.model.User;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class UserViewAdapter extends RecyclerView.Adapter<UserViewAdapter.RecyclerViewHolder> {

    private List<User> userList;
    private ItemClickListener onItemClickListener;

    public UserViewAdapter(List<User> users, ItemClickListener onItemClickListener) {
        this.userList = users;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        User user = userList.get(position);
        holder.itemTextView.setText(user.getId()+"");
        holder.titleTextView.setText(user.getName());
        holder.itemView.setTag(user);
        holder.titleTextView.setOnClickListener(v -> {
            onItemClickListener.onItemClicked(holder, user, position);
        });
        holder.arrowView.setOnClickListener(v -> {
            onItemClickListener.onItemClicked(holder, user, position);
        });
    }

    @Override
    public int getItemCount() {
        if(userList == null){
            return 0;
        }
        return userList.size();
    }

    public void addItems(List<User> users) {
        this.userList = users;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTextView;
        private TextView titleTextView;
        private View arrowView;

        RecyclerViewHolder(View view) {
            super(view);
            itemTextView = view.findViewById(R.id.album_user_id);
            titleTextView = view.findViewById(R.id.user_name);
            arrowView = view.findViewById(R.id.arrow);
        }
    }


    public interface ItemClickListener {

        void onItemClicked(RecyclerView.ViewHolder vh, Object item, int pos);
    }
}
