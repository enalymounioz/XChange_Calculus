package com.sky.casper.skywalker_new_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sky.casper.skywalker_new_app.Activities.ActivityMainLogin;

public class ActivitySplash extends AppCompatActivity {

    //Manual duration of Splash Screen
    private static int timeout=1000;

   /*Variables for background animation*/
    RelativeLayout relativeLayout;
    AnimationDrawable animationDrawable;

    /*Variables for logo animation*/
    TextView text;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*Make Activity full screen and hide navigation bar*/
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        relativeLayout =findViewById(R.id.root_layout);
        /*Start Background Animation*/
        animationDrawable = (AnimationDrawable)relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
        /*Start Background Animation*/


        text= findViewById(R.id.text);
        image=findViewById(R.id.image);
        /*Start Logo Animation*/
        Animation animation = AnimationUtils.loadAnimation(ActivitySplash.this,R.anim.logoanimation);
        image.startAnimation(animation);
        text.startAnimation(animation);


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

    }
}
