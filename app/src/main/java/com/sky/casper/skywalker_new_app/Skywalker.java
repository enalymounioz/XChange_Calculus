package com.sky.casper.skywalker_new_app;

import android.app.Application;
import android.content.Context;

/* Application class which does different initialisations and handling many asynchronous things such as sockets */

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
