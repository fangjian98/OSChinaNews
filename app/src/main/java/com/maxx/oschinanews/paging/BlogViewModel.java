package com.maxx.oschinanews.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.maxx.oschinanews.model.Blog;

public class BlogViewModel extends ViewModel {

    public LiveData<PagedList<Blog>> blogPagedList;

    public BlogViewModel(){
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(BlogDataSource.PER_PAGE)
                .setPrefetchDistance(2)
                .setInitialLoadSizeHint(BlogDataSource.PER_PAGE)
                .setMaxSize(65536*BlogDataSource.PER_PAGE)
                .build();
        blogPagedList = new LivePagedListBuilder<>(
                new BlogDataSourceFactory(),config)
                .build();
    }
}
