package com.maxx.oschinanews.paging;

/*public class BlogViewModel extends ViewModel {

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
}*/

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.maxx.oschinanews.db.BlogDao;
import com.maxx.oschinanews.db.MyDataBase;
import com.maxx.oschinanews.model.Blog;

public class BlogViewModel extends AndroidViewModel {

    public static final int PER_PAGE = 20;
    public LiveData<PagedList<Blog>> blogPagedList;

    public BlogViewModel(@NonNull Application application) {
        super(application);
        BlogDao blogDao = MyDataBase.getInstance(application).getBlogDao();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PER_PAGE)
                .setPrefetchDistance(3)
                .setInitialLoadSizeHint(PER_PAGE)
                .setMaxSize(65536*PER_PAGE)
                .build();
        blogPagedList = new LivePagedListBuilder<>(
                blogDao.getBlogList(),config)
                .setBoundaryCallback(new BlogBoundaryCallback(application))
                .build();

    }

    //刷新
    public void refresh(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int size = MyDataBase.getInstance(getApplication())
                        .getBlogDao()
                        .getCount();
                Log.d("Maxx", "size=" + size);
                MyDataBase.getInstance(getApplication())
                        .getBlogDao()
                        .clear();
            }
        });
    }
}
