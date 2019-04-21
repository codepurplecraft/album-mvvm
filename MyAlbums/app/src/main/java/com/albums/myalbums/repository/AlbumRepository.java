package com.albums.myalbums.repository;

import android.util.Log;

import com.albums.myalbums.AppExecutors;
import com.albums.myalbums.persistence.db.dao.AlbumDao;
import com.albums.myalbums.persistence.db.dao.UserDao;
import com.albums.myalbums.persistence.model.Album;
import com.albums.myalbums.persistence.model.User;
import com.albums.myalbums.persistence.web.WebService;

import java.io.IOException;
import java.util.List;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumRepository {
    private static final String TAG = AlbumRepository.class.getSimpleName();
    private AlbumDao mAlbumDao;
    private UserDao mUserDao;
    private AppExecutors mExecutor;
    private WebService webService;

    public AlbumRepository(WebService webservice, AlbumDao albumDao,UserDao userDao, AppExecutors executor) {
        mExecutor = executor;
        mAlbumDao = albumDao;
        mUserDao = userDao;
        this.webService = webservice;
        getUsersFromApi();
    }

    public LiveData<List<Album>> getAlbums(int userId) {
        getAlbumsFromApi();
        return mAlbumDao.loadAlbumsByUser(userId);
    }

    public LiveData<List<User>> getUsers() {
        getUsersFromApi();
        return mUserDao.loadUsers();
    }

    private void getAlbumsFromApi() {
        Call<List<Album>> call = webService.getAlbums();
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                List<Album> albums = response.body();
                Log.d(TAG, "Number of albums received: " + albums.size());
                mExecutor.diskIO().execute(() -> {
                    mAlbumDao.save(albums);
                });
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    private void getUsersFromApi() {
        Call<List<User>> call = webService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                Log.d(TAG, "Number of users received: " + users.size());
                mExecutor.diskIO().execute(() -> {
                    mUserDao.saveUser(users);
                });
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

   /* private void refreshUser(final String userId) {
        // Runs in a background thread.
        mExecutor.networkIO().execute(() -> {
            // Check if user data was fetched recently.
          *//*  boolean albumsExist = mAlbumDao.hasAlbums(1000);
            if (!albumsExist) {*//*
                // Refreshes the data.
                Response<List<Album>> response = null;
                try {
                    response = webService.getAlbums().execute();
                    // Check for errors here.

                    // Updates the database. The LiveData object automatically
                    // refreshes, so we don't need to do anything else here.
                    mAlbumDao.save(response.body());
                } catch (IOException e) {
                    e.printStackTrace();
                }


//            }
        });
    }*/



}
