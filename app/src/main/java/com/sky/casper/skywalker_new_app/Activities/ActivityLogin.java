package com.sky.casper.skywalker_new_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.sky.casper.skywalker_new_app.R;

public class ActivityLogin extends AppCompatActivity {

    Button button;//Variable for the Forgot Password Button
    RelativeLayout relativeLayout;//Variables for background animation
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*Make Activity full screen and hide navigation bar*/
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        /*Make Activity full screen and hide navigation bar*/

        /*Start Background Animation*/
        relativeLayout = findViewById(R.id.activity_main_login);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
        /*Start Background Animation*/

        /*Forgot my password button*/
        button = findViewById(R.id.button_forgot);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
            /*Forgot my password button*/
        });

    }

    /*Sign up button to sign up activity*/
    public void buttonSignup(View view) {
        startActivity(new Intent(getApplicationContext(), ActivitySignUp.class));
    }

    public void buttonSkip(View view) {
    }
    /*Sign up button to sign up activity*/


}
