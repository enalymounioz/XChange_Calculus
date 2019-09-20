package com.sky.casper.skywalker_new_app.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sky.casper.skywalker_new_app.R;

public class ActivitySignUp extends AppCompatActivity {



    private RelativeLayout relativeLayout;//Variables for background animation
    private AnimationDrawable animationDrawable;
    /*Variable for check box and a Toast Message*/
    private CheckBox termsConditions;

    /*user personal information*/
    private TextInputEditText user_name,user_surname,user_postcode,user_password,user_confirm,user_email,user_address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        /*Make Activity full screen and hide navigation bar*/
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        /*Make Activity full screen and hide navigation bar*/

        initialiseValues();
        setListeners();

    }


    private void initialiseValues(){

        /*Start Background Animation*/
        relativeLayout = findViewById(R.id.activity_sign_up);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
        /*Start Background Animation*/

        user_name = findViewById(R.id.edit_text_name);
        user_surname = findViewById(R.id.edit_text_surname);
        user_email = findViewById(R.id.edit_text_email);
        user_address = findViewById(R.id.edit_text_address);
        user_postcode = findViewById(R.id.edit_text_postcode);
        user_password = findViewById(R.id.edit_text_password);
        user_confirm = findViewById(R.id.edit_text_confirm_password);

        termsConditions = findViewById(R.id.checkboxTermsConditions);
    }

    private void setListeners(){
        /*On click Listener for the check box and a Toast Message Terms & Conditions*/
        termsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (termsConditions.isChecked()){
                    Toast.makeText(ActivitySignUp.this, termsConditions.getText()+" Checked", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ActivitySignUp.this, termsConditions.getText()+" Unchecked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*On click Listener for the check box and a Toast Message Terms & Conditions*/
    }

    /*sign up button listener*/
    public void btnSignUp(View v){

    }
}
