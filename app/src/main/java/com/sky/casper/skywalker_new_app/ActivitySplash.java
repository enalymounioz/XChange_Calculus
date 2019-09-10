package com.sky.casper.skywalker_new_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.sky.casper.skywalker_new_app.Activities.ActivityMainLogin;

public class ActivitySplash extends AppCompatActivity {

    //Manual duration of Splash Screen
    private static int timeout=3000;

    //Animation declaration

    Animation logoanimation;
    ImageView image;
    TextView text;


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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(ActivitySplash.this,ActivityMainLogin.class);
                startActivity(intent);
            }
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
