package com.sky.casper.skywalker_new_app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.casper.skywalker_new_app.Helpers.Cache;
import com.sky.casper.skywalker_new_app.Helpers.DatabaseHelper;
import com.sky.casper.skywalker_new_app.Helpers.ServerRequest;
import com.sky.casper.skywalker_new_app.Helpers.Settings;
import com.sky.casper.skywalker_new_app.R;

import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ActivitySplash extends AppCompatActivity {

    //Manual duration of Splash Screen
    private static int timeout=3000;

    //Animation declaration

    Animation logoanimation;
    ImageView image;
    TextView text;
    DatabaseHelper db;
    Cache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*Make Activity full screen and hide navigation bar*/
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        /*Move from Splash Screen to Main Screen*/
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            db = new DatabaseHelper(ActivitySplash.this); /// create database before moving to the main screen
            cache = new Cache(ActivitySplash.this);  // create cache handler
            String ads_json;
            try {
                ads_json = new ServerRequest(ActivitySplash.this).execute(Settings.CONNECTION_TYPES.POST,"page",Integer.toString(1), Settings.URLS.URL_ADS).get(); /// Download ads
                cache.saveSomething(ads_json,"ads.ser",false);   /// save them in cache
                Log.e("ADS",ads_json);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent;
                if(db.getUserId()==null) {
                    intent = new Intent(ActivitySplash.this, ActivityLogin.class);
                }
                else{
                    Settings.getCandidateDetails();
                    intent = new Intent(ActivitySplash.this, ActivityHome.class);
                }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); /// delete splash activity
            startActivity(intent);
        },timeout);
        /*Move from Splash Screen to Main Screen*/

        //Start Animation form small to big logo
        logoanimation = AnimationUtils.loadAnimation(this,R.anim.logoanimation);
        image = findViewById(R.id.image);
        image.startAnimation(logoanimation);
        text = findViewById(R.id.text);
        text.startAnimation(logoanimation);
    }


}
