package com.albums.myalbums.persistence.web;


import com.albums.myalbums.persistence.model.Album;
import com.albums.myalbums.persistence.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {

    String HTTPS_API_GITHUB_URL = "https://jsonplaceholder.typicode.com";

    @GET("/albums")
    Call<List<Album>> getAlbums();

    @GET("/users")
    Call<List<User>> getUsers();
}
