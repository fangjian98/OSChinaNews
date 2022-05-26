package com.maxx.oschinanews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkUtil {

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
}
