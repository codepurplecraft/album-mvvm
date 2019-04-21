package com.albums.myalbums.persistence.db.dao;

import com.albums.myalbums.persistence.model.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Insert(onConflict = REPLACE)
    void saveUser(List<User> users);
    @Query("SELECT * FROM user")
    LiveData<List<User>> loadUsers();
}
