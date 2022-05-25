package com.maxx.oschinanews.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.maxx.oschinanews.model.Blog;

@Database(entities = {Blog.class}, version = 1, exportSchema = true)
public abstract class MyDataBase extends RoomDatabase {

    private static final String DATABASE_NAME = "news.db";
    private static MyDataBase mInstance;

    public static synchronized MyDataBase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MyDataBase.class,
                    DATABASE_NAME)
                    .build();
        }
        return mInstance;
    }

    public abstract BlogDao getBlogDao();
}
