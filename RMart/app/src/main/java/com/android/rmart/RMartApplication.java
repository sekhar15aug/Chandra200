package com.android.rmart;

import android.app.Application;

import com.android.rmart.util.AppUtils;

/**
 * Created by Chandra on 02-12-2015.
 */
public class RMartApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppUtils.getInstance().setAppContext(getApplicationContext());
    }
}
