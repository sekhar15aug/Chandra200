package com.android.rmart.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Chandra on 02-12-2015.
 */
public class AppUtils {

    private static AppUtils ourInstance = new AppUtils();
    private static Context mContext;

    public static AppUtils getInstance() {
        return ourInstance;
    }

    private AppUtils() {
    }

    public void setAppContext(Context context) {
        mContext = context;
    }

    public Context getAppContext() {
        return mContext;
    }

    public String getPaginationUrl(String url, int index, int size) {
        if (url != null && index >= 0 && size >= 1) {
            String newUrl = url + "&page=" + index + "&pageSize=" + size;
            return newUrl;
        }
        return url;
    }

    public boolean isNetworkAvailable() {
        if (null != mContext) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}
