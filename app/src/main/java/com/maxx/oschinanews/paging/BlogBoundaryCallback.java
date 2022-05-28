package com.maxx.oschinanews.paging;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.maxx.oschinanews.api.RetrofitClient;
import com.maxx.oschinanews.db.MyDataBase;
import com.maxx.oschinanews.model.Blog;
import com.maxx.oschinanews.model.Blogs;
import com.maxx.oschinanews.utils.NetworkUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogBoundaryCallback extends PagedList.BoundaryCallback<Blog> {

    private static final int CATELOG = 2;
    public static final int PER_PAGE = 20;
    public static final int FIRST_PAGE = 1;
    private Application application;
    private String accessToken;
    private int lastPage;

    public BlogBoundaryCallback(Application application){
        this.application = application;
        this.accessToken = NetworkUtil.getToken(application);
        Log.d("Maxx", "accessToken=" + accessToken);
    }

    //加载第一页数据
    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        lastPage = FIRST_PAGE;
        getTopData();
    }

    private void getTopData() {
        RetrofitClient.getInstance()
                .getApi()
                .getBlogs(accessToken, CATELOG, FIRST_PAGE, PER_PAGE)
                .enqueue(new Callback<Blogs>() {
                    @Override
                    public void onResponse(Call<Blogs> call, Response<Blogs> response) {
                        if (response.body() != null) {
                            //把数据传递给PagedList
                            //callback.onResult(response.body().blogList, null, FIRST_PAGE + 1);
                            insertBlogs(response.body().blogList);
                            Log.d("Maxx", "loadInitial:" + response.body().blogList);
                        }
                    }

                    @Override
                    public void onFailure(Call<Blogs> call, Throwable t) {

                    }
                });
    }

    //加载下一页数据
    @Override
    public void onItemAtEndLoaded(@NonNull Blog blog) {
        super.onItemAtEndLoaded(blog);
        getTopAfterData(blog);
    }

    private void getTopAfterData(Blog blog) {
        RetrofitClient.getInstance()
                .getApi()
                .getBlogs(accessToken, CATELOG, lastPage++, PER_PAGE)
                .enqueue(new Callback<Blogs>() {
                    @Override
                    public void onResponse(Call<Blogs> call, Response<Blogs> response) {
                        if (response.body() != null) {
                            //把数据传递给PagedList
                            //callback.onResult(response.body().blogList, params.key + 1);
                            insertBlogs(response.body().blogList);
                            Log.d("Maxx", "loadAfter: page=" + lastPage + " " + response.body().blogList);
                        }
                    }

                    @Override
                    public void onFailure(Call<Blogs> call, Throwable t) {

                    }
                });
    }

    //把网络数据，保存到数据库
    private void insertBlogs(List<Blog> blogs) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                MyDataBase.getInstance(application)
                        .getBlogDao()
                        .insertBlogs(blogs);
            }
        });
    }
}
