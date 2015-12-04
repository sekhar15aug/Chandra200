package com.android.rmart.request;

import android.text.TextUtils;

import com.android.rmart.util.AppUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Chandra on 02-12-2015.
 */
public class AppRequestQueue {

    private static final String TAG = AppRequestQueue.class.getSimpleName();
    private static AppRequestQueue mInstance = null;
    private RequestQueue mRequestQueue;

    private AppRequestQueue() {
    }

    public static synchronized AppRequestQueue getInstance() {

        if (null == mInstance) {
            mInstance = new AppRequestQueue();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(AppUtils.getInstance().getAppContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
