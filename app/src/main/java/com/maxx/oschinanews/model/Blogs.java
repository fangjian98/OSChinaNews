package com.maxx.oschinanews.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Blogs {

    @SerializedName("newslist")
    public List<Blog> blogList;

    @Override
    public String toString() {
        return "Blogs{" +
                "blogList=" + blogList +
                '}';
    }
}


