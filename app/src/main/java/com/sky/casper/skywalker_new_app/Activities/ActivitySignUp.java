package com.sky.casper.skywalker_new_app.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sky.casper.skywalker_new_app.Helpers.JsonHelper;
import com.sky.casper.skywalker_new_app.Helpers.ServerRequest;
import com.sky.casper.skywalker_new_app.Helpers.Settings;
import com.sky.casper.skywalker_new_app.R;

import org.json.JSONException;

public class ActivitySignUp extends AppCompatActivity implements ServerRequest.AsyncResponse {



    private RelativeLayout relativeLayout;//Variables for background animation
    private AnimationDrawable animationDrawable;
    /*Variable for check box and a Toast Message*/
    private CheckBox termsConditions;

    /*user personal information*/
    private TextInputEditText user_name,user_surname,user_postcode,user_password,user_confirm,user_email,user_address;

    private ServerRequest serverRequest;
    private Button skipButton;//Variable for the Skip Button



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


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
                            }
        });
        /*Going to Home Activity*/

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

        if(user_name.getText().toString().trim().isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.fill_name), Toast.LENGTH_SHORT).show();
        }
        else if(user_surname.getText().toString().trim().isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.fill_surname), Toast.LENGTH_SHORT).show();
        }
        else if(user_email.getText().toString().trim().isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.fill_email), Toast.LENGTH_SHORT).show();
        }
        else if(user_address.getText().toString().trim().isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.fill_address), Toast.LENGTH_SHORT).show();
        }
        else if(user_postcode.getText().toString().trim().isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.fill_postcode), Toast.LENGTH_SHORT).show();
        }
        else if(user_password.getText().toString().trim().isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.fill_password), Toast.LENGTH_SHORT).show();
        }
        else if(user_confirm.getText().toString().trim().isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.fill_confirmation), Toast.LENGTH_SHORT).show();
        }
        else if(!termsConditions.isChecked()){
            Toast.makeText(this, getResources().getString(R.string.check_terms_conditions), Toast.LENGTH_SHORT).show();
        }
        else{
            String password = user_password.getText().toString();
            String repassword = user_confirm.getText().toString();

            if(!password.equals(repassword)){
                Toast.makeText(this, getResources().getString(R.string.wrong_repassword), Toast.LENGTH_SHORT).show();
            }
            else{
                serverRequest = new ServerRequest(ActivitySignUp.this, ActivitySignUp.this);
                serverRequest.execute(Settings.CONNECTION_TYPES.POST,"name",user_name.getText().toString(),"surname",user_surname.getText().toString(),
                        "mail",user_email.getText().toString(),"address",user_address.getText().toString(),"postcode",user_postcode.getText().toString(),"repassword",repassword,Settings.URLS.REGISTER_URL);
            }
        }
    }

    @Override
    public void handleAnswer(String answer) {
        Log.e("AnswerSignUp",answer);
        if(answer.equals(Settings.ERROR_MSG.ERROR_SRVR)){ // server exception or general server problem
            Toast.makeText(this,getResources().getString(R.string.server_error),Toast.LENGTH_LONG).show();
        }
        else if(answer.equals(Settings.ERROR_MSG.NO_INTERNET)){ // device is not connecting to the network
            Toast.makeText(this, getResources().getString(R.string.no_internet),Toast.LENGTH_LONG).show();
        }
        else{
            JsonHelper jsonHelper;
            try{
                jsonHelper = new JsonHelper(answer);
                if(jsonHelper.getStatus().toLowerCase().equals("success")){
                    Toast.makeText(this,getResources().getString(R.string.success_registration),Toast.LENGTH_LONG).show();
                }
                else{
                    String message = jsonHelper.getMessage();
                    if(message.toLowerCase().contains("email")){
                        Toast.makeText(this, getResources().getString(R.string.email_exist), Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(this, getResources().getString(R.string.fail_registration), Toast.LENGTH_LONG).show();
                    }
                }

            }catch (JSONException e){
                Toast.makeText(this, getResources().getString(R.string.general_error), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        /// restart the server request because asynchronous tasks can only be executed one time
        serverRequest.cancel(true);
        serverRequest = new ServerRequest(this,this);
    }
}
