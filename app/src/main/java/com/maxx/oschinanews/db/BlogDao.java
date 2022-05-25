package com.maxx.oschinanews.db;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.maxx.oschinanews.model.Blog;

import java.util.List;

@Dao
public interface BlogDao {

    @Insert
    void insertBlogs(List<Blog> blogs);

    @Query("DELETE FROM blog")
    void clear();

    @Query("SELECT * FROM blog")
    DataSource.Factory<Integer, Blog> getBlogList();

}
