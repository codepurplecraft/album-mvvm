package com.albums.myalbums.persistence.db;

import android.content.Context;

import com.albums.myalbums.AppExecutors;
import com.albums.myalbums.persistence.db.dao.AlbumDao;
import com.albums.myalbums.persistence.db.dao.UserDao;
import com.albums.myalbums.persistence.model.Album;
import com.albums.myalbums.persistence.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Album.class, User.class}, version = 1, exportSchema = false)
public abstract class AlbumDatabase  extends RoomDatabase {

    public static final String DATABASE_NAME = "album-db";
    public abstract AlbumDao albumDao();
    public abstract UserDao userDao();
    private static AlbumDatabase sInstance;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AlbumDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AlbumDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    public static AlbumDatabase buildDatabase(final Context appContext,
                                              final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AlbumDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                           // sInstance.insertData(albums);
                            // notify that the database was created and it's ready to be used
                            sInstance.setDatabaseCreated();
                        });
                    }
                })
                .build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    private static void insertData(final List<Album> albums) {
        sInstance.runInTransaction(() -> sInstance.albumDao().save(albums));
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

}
