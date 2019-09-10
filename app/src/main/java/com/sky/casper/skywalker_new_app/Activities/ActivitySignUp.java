package com.sky.casper.skywalker_new_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.sky.casper.skywalker_new_app.R;

public class ActivitySignUp extends AppCompatActivity {

    /*Variables for background animation*/
    RelativeLayout relativeLayout;
    AnimationDrawable animationDrawable;
    /*Variables for background animation*/

    /*Variable for check box and a Toast Message*/
    CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /*Make Activity full screen and hide navigation bar*/
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        /*Make Activity full screen and hide navigation bar*/

        /*Start Background Animation*/
        relativeLayout = findViewById(R.id.activity_sign_up);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
        /*Start Background Animation*/


        /*On click Listener for the check box and a Toast Message Terms & Conditions*/
        checkBox = findViewById(R.id.checkboxTermsConditions);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()){
                    Toast.makeText(ActivitySignUp.this, checkBox.getText()+" Checked", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ActivitySignUp.this,checkBox.getText()+" Unchecked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*On click Listener for the check box and a Toast Message Terms & Conditions*/

    }

}
