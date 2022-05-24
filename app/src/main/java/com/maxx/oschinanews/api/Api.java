package com.maxx.oschinanews.api;

import com.maxx.oschinanews.model.Blogs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    //oschina API参考:https://www.oschina.net/openapi/docs/news_list
    @GET("/action/openapi/news_list")
    Call<Blogs> getBlogs(
            @Query("access_token") String token,
            @Query("catalog") int catalog,
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );
}
