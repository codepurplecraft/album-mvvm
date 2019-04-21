package com.albums.myalbums;

import android.app.Application;

import com.albums.myalbums.persistence.db.AlbumDatabase;
import com.albums.myalbums.persistence.web.WebService;
import com.albums.myalbums.repository.AlbumRepository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BasicApp extends Application {

    private AppExecutors mAppExecutors;
    private WebService mWebService;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
        mWebService = create();
    }

    public AlbumDatabase getDatabase() {
        return AlbumDatabase.getInstance(this, mAppExecutors);
    }

    public AlbumRepository getRepository() {
        return new AlbumRepository(mWebService, getDatabase().albumDao(), getDatabase().userDao(),mAppExecutors);
    }

    public static WebService create() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebService.HTTPS_API_GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(WebService.class);
    }
}
