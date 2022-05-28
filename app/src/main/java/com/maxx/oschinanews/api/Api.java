package com.maxx.oschinanews.api;

import com.maxx.oschinanews.model.Blogs;
import com.maxx.oschinanews.model.Token;

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

    @GET("/action/openapi/token")
    Call<Token> getToken(
            @Query("client_id")  String clientId,
            @Query("client_secret") String clientSecret,
            @Query("grant_type") String grantType,
            @Query("redirect_uri") String redirectUri,
            @Query("code") String code
    );
}
