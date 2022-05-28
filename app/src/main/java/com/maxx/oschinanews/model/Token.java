package com.maxx.oschinanews.model;


public class Token {
    public String access_token;
    public String refresh_token;
    public String token_type;
    public int expires_in;
    public int uid;

    @Override
    public String toString() {
        return "Token{" +
                "access_token='" + access_token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in=" + expires_in +
                ", uid=" + uid +
                '}';
    }
}
