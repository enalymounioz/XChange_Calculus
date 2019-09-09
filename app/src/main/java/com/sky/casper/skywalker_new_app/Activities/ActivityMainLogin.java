package com.sky.casper.skywalker_new_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.sky.casper.skywalker_new_app.R;

import org.w3c.dom.Text;

public class ActivityMainLogin extends AppCompatActivity {

    /*Variables for if username and password not filled in login button is disabled*/
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonConfirm;

    /*Variables for background animation*/
    RelativeLayout relativeLayout;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        /*Make Activity full screen and hide navigation bar*/
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        relativeLayout =findViewById(R.id.activity_main_login);
        /*Start Background Animation*/
        animationDrawable = (AnimationDrawable)relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
        /*Start Background Animation*/

        /*Check if fields username and password are filled in */
        editTextUsername = findViewById(R.id.edit_text_username);
        editTextUsername = findViewById(R.id.edit_text_password);
        buttonConfirm = findViewById(R.id.button_confirm);

        editTextUsername.addTextChangedListener(loginTextWatcher);
        editTextPassword.addTextChangedListener(loginTextWatcher);

       }
       private TextWatcher loginTextWatcher = new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
               String usernameInput = editTextUsername.getText().toString().trim();
               String passwordInput = editTextPassword.getText().toString().trim();

               buttonConfirm.setEnabled(!usernameInput.isEmpty()&& !passwordInput.isEmpty());

           }

           @Override
           public void afterTextChanged(Editable editable) {

           }
       };

    public void btn_signup(View view) {
        startActivity(new Intent(getApplicationContext(),ActivitySignUp.class));
    }


}
