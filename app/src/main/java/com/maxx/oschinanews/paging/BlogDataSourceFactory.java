package com.maxx.oschinanews.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.maxx.oschinanews.model.Blog;

public class BlogDataSourceFactory extends DataSource.Factory<Integer, Blog> {
    @NonNull
    @Override
    public DataSource<Integer, Blog> create() {
        return new BlogDataSource();
    }
}
