package com.maxx.oschinanews.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.maxx.oschinanews.api.RetrofitClient;
import com.maxx.oschinanews.model.Blog;
import com.maxx.oschinanews.model.Blogs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogDataSource extends PageKeyedDataSource<Integer, Blog> {

    private static final String ACCESS_TOKEN = "8891c42b-78ab-4f66-a89f-ae577ce4542b";
    private static final int CATELOG = 1;
    public static final int PER_PAGE = 20;
    public static final int FIRST_PAGE = 1;

    //第一页
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Blog> callback) {
        RetrofitClient.getInstance()
                .getApi()
                .getBlogs(ACCESS_TOKEN, CATELOG, FIRST_PAGE, PER_PAGE)
                .enqueue(new Callback<Blogs>() {
                    @Override
                    public void onResponse(Call<Blogs> call, Response<Blogs> response) {
                        if (response.body() != null) {
                            //把数据传递给PagedList
                            callback.onResult(response.body().blogList, null, FIRST_PAGE + 1);
                            Log.d("Maxx", "loadInitial:" + response.body().blogList);
                        }
                    }

                    @Override
                    public void onFailure(Call<Blogs> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Blog> callback) {

    }

    //下一页
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Blog> callback) {
        RetrofitClient.getInstance()
                .getApi()
                .getBlogs(ACCESS_TOKEN, CATELOG, params.key, PER_PAGE)
                .enqueue(new Callback<Blogs>() {
                    @Override
                    public void onResponse(Call<Blogs> call, Response<Blogs> response) {
                        if (response.body() != null) {
                            //把数据传递给PagedList
                            callback.onResult(response.body().blogList, params.key + 1);
                            Log.d("Maxx", "loadAfter:" + response.body().blogList);
                        }
                    }

                    @Override
                    public void onFailure(Call<Blogs> call, Throwable t) {

                    }
                });
    }
}
