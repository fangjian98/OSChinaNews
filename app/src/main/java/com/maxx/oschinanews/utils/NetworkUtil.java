package com.maxx.oschinanews.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

public class NetworkUtil {

    private static final String ACCESS_TOKEN = "8891c42b-78ab-4f66-a89f-ae577ce4542b";
    private static final String PREF_NAME = "oschina_news";
    private static final String KEY_TOKEN = "token";

    /**
     * 判断网络是否连接
     */
    public static boolean isNetConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                Log.d("Maxx", "isConnected:" + mNetworkInfo.isConnected());
                return mNetworkInfo.isConnected();
            }
        }
        Log.d("Maxx", "isNetConnected:false");
        return false;
    }

    public static void openHtmlViewer(Context context, String uri) {
        Intent intent = new Intent(Constants.ACTION_HTML_VIEW, Uri.parse(uri));
        intent.setClassName("com.maxx.oschinanews", "com.maxx.oschinanews.utils.HtmlViewer");
        /*Intent intent = new Intent();
        intent.setAction(Constants.ACTION_HTML_VIEW);
        intent.setDataAndType(Uri.parse(uri),"text/html");
        intent.putExtra(Intent.EXTRA_TITLE, uri);*/
        context.startActivity(intent);
    }

    public static void setToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply();
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TOKEN, ACCESS_TOKEN);
    }
}
