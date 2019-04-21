package com.albums.myalbums.persistence.model;

import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by home on 4/15/2019.
 * Album model class containing all information about the album
 */
@Entity
public class Album implements Comparable<Album>{

    @SerializedName("userId")
    private int userId;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @PrimaryKey
    @SerializedName("id")
    private int id;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SerializedName("title")
    private String title;

    @Override
    public int compareTo(Album o) {
        return title.compareTo(o.title);
    }
}
