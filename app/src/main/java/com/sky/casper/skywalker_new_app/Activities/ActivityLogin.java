package com.sky.casper.skywalker_new_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sky.casper.skywalker_new_app.Helpers.JsonHelper;
import com.sky.casper.skywalker_new_app.Helpers.ServerRequest;
import com.sky.casper.skywalker_new_app.Helpers.Settings;
import com.sky.casper.skywalker_new_app.R;

import org.json.JSONException;

public class ActivityLogin extends AppCompatActivity implements ServerRequest.AsyncResponse {

    private Button forgotButton;//Variable for the Forgot Password Button
    private Button skipButton;//Variable for the Skip Button

    /* User information for login*/
    private TextInputEditText email;
    private TextInputEditText password;
    /* User information for login*/

    private ServerRequest serverRequest; // server request for login and maybe for other API services

    /*Variables for background animation*/
    private RelativeLayout relativeLayout;
    private AnimationDrawable animationDrawable;
    /*Variables for background animation*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        /*Make Activity full screen and hide navigation bar*/
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        /*Make Activity full screen and hide navigation bar*/

        /*Going to Home Activity*/
        skipButton = findViewById(R.id.button_skip);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityHome();
                /*Going to Home Activity*/

            }
        });
        initialiseValues();
        setListeners();
    }
    /*Going to Home Activity*/
    private void openActivityHome() {
        Intent intent=new Intent(this, ActivityHome.class);
        startActivity(intent);
    }
    /*Going to Home Activity*/


    private void initialiseValues(){
        /*Start Background Animation*/
        relativeLayout = findViewById(R.id.activity_main_login);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
        /*Start Background Animation*/

        /*Forgot my password button*/
        forgotButton = findViewById(R.id.button_forgot);

        /* user login information */
        email = findViewById(R.id.edit_text_username);
        password = findViewById(R.id.edit_text_password);

        /* server request for login in */
        serverRequest = new ServerRequest(this,this);
    }

    private void setListeners(){
        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
            /*Forgot my password button*/
        });
    }

    /*Sign up button to sign up activity*/
    public void btn_signup(View view) {
        startActivity(new Intent(getApplicationContext(), ActivitySignUp.class));
    }
    /*Sign up button to sign up activity*/

    /* execute the asynchronous request */
    public void btn_login(View v){
        // checks if the user fill in the email and password
        if(email.getText().toString().trim().replaceAll("\\s+","").equals("") || password.getText().toString().trim().replaceAll("\\s+","").equals("")){
            Toast.makeText(ActivityLogin.this,ActivityLogin.this.getResources().getString(R.string.fill_username_password),Toast.LENGTH_LONG).show();
        }
        else {
            serverRequest.execute(Settings.CONNECTION_TYPES.POST, "Username", email.getText().toString(), "Password", password.getText().toString(), Settings.URLS.LOGIN_CANDIDATE);
        }
    }


    @Override /* handle the server answer */
    public void handleAnswer(String answer) {
        Log.e("AnswerLogin",answer);
        if(answer.equals(Settings.ERROR_MSG.ERROR_SRVR)){ // server exception or general server problem
            Toast.makeText(this,getResources().getString(R.string.server_error),Toast.LENGTH_LONG).show();
        }
        else if(answer.equals(Settings.ERROR_MSG.NO_INTERNET)){ // device is not connecting to the network
            Toast.makeText(this, getResources().getString(R.string.no_internet),Toast.LENGTH_LONG).show();
        }
        else{ // success or user error (password, email)
            JsonHelper jsonHelper;
            try {
                 jsonHelper = new JsonHelper(answer);
                 if(jsonHelper.getStatus().toLowerCase().equals("success")){
                    /// Go to profile activity
                    Toast.makeText(this,answer,Toast.LENGTH_SHORT).show();
                 }
                 else{
                     String message = jsonHelper.getMessage();
                     if(message.toLowerCase().contains("wrong")){
                         Toast.makeText(this, getResources().getString(R.string.wrong_username_password), Toast.LENGTH_LONG).show();
                     }
                     else {
                         Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                     }
                 }
            } catch (JSONException e) {
                Toast.makeText(this, getResources().getString(R.string.general_error), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        /// restart the server request because asynchronous tasks can only be executed one time
        serverRequest.cancel(true);
        serverRequest = new ServerRequest(this,this);
    }
}
