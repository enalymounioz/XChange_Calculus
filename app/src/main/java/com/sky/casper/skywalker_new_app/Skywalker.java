package com.sky.casper.skywalker_new_app;

import android.app.Application;
import android.content.Context;

public class Skywalker extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
