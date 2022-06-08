package com.maxx.oschinanews.model;

public class User {
    public String id;
    public String email;
    public String name;
    public String gender;
    public String avatar;
    public String location;
    public String url;

    public User(){
    }

    public User(String id, String email, String name, String gender, String avatar, String location, String url) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.avatar = avatar;
        this.location = location;
        this.url = url;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", avatar='" + avatar + '\'' +
                ", location='" + location + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}