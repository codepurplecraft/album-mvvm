package com.albums.myalbums.persistence.db.dao;

import com.albums.myalbums.persistence.model.Album;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface AlbumDao {

    @Insert(onConflict = REPLACE)
    void save(List<Album> albums);
    @Query("SELECT * FROM album where userId= :userId")
    LiveData<List<Album>> loadAlbumsByUser(int userId);

}
