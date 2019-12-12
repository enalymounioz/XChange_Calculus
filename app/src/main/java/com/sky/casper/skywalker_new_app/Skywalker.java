package com.sky.casper.skywalker_new_app;

import android.app.Application;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.sky.casper.skywalker_new_app.Helpers.Cache;
import com.sky.casper.skywalker_new_app.Helpers.DatabaseHelper;

/* Application class which does different initialisations and handling many asynchronous things such as sockets */
public class Skywalker extends Application {

    private static Context mContext;
    private DatabaseHelper db;
    private Cache cache;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        db = new DatabaseHelper(this);
        cache = new Cache(this);
        if(db.getUserId()!=null){   //// every time that start the app we disconnect the user until to put the appropriate button
            Log.e("Delete user id","DELETE DB USER ID");
            db.delete_id(db.getUserId());
            cache.deleteServerToken();
            cache.deleteProfile();
        }
    }

    public static Context getContext(){
        return mContext;
    }
}
