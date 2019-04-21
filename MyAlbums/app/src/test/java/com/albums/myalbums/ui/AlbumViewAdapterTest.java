package com.albums.myalbums.ui;

import com.albums.myalbums.persistence.model.Album;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AlbumViewAdapterTest {
    AlbumViewAdapter adapter;


    @Test
    public void getItemCount() {
        adapter = new AlbumViewAdapter(null);
        Assert.assertEquals(adapter.getItemCount(),0);
        List<Album> albumList = new ArrayList<>();
        Album album = new Album();
        Album album1 = new Album();
        albumList.add(album);
        albumList.add(album1);
        adapter = new AlbumViewAdapter(albumList);
        Assert.assertEquals(adapter.getItemCount(),2);
    }

    @Test
    public void addItems() {
        adapter = new AlbumViewAdapter(null);
        adapter.addItems(null);
        List<Album> albumList = new ArrayList<>();
        Album album = new Album();
        Album album1 = new Album();
        albumList.add(album);
        albumList.add(album1);
        adapter.addItems(albumList);
    }
}