package com.mksengun.demoproject.base;

import android.app.Application;
import android.content.Context;

/**
 * This is the base application class. This may be need for multi dex later.
 */
public class BaseApplication extends Application {

    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }

}