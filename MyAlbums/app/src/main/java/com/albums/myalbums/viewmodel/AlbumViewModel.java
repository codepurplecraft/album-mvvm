package com.albums.myalbums.viewmodel;

import android.app.Application;

import com.albums.myalbums.BasicApp;
import com.albums.myalbums.persistence.model.Album;
import com.albums.myalbums.persistence.model.User;
import com.albums.myalbums.repository.AlbumRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

/**
 * Created by home on 4/15/2019.
 */
public class AlbumViewModel extends AndroidViewModel {

    private final AlbumRepository mRepository;
    private final MediatorLiveData<List<User>> mObservableUsers;

    public AlbumViewModel(@NonNull Application application) {
        super(application);

        mObservableUsers = new MediatorLiveData<>();
        mObservableUsers.setValue(null);

        mRepository = ((BasicApp) application).getRepository();
        LiveData<List<User>> users = mRepository.getUsers();

        mObservableUsers.addSource(users, mObservableUsers::setValue);
    }

    public LiveData<List<Album>> getAlbums(int userId) {
        return mRepository.getAlbums(userId);
    }

    public MediatorLiveData<List<User>> getUsers() {
        return mObservableUsers;
    }
}
