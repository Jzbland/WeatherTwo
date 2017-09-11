package com.beestar.jzb.goglebleweather;

import android.app.Application;
import android.content.Context;

/**
 * Created by jzb on 2017/6/28.
 */

public class MyApp extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

    }

    public static Context getContext() {
        return mContext;
    }
}
