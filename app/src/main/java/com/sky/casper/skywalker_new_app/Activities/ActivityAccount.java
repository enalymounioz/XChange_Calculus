package com.sky.casper.skywalker_new_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hbb20.CountryCodePicker;
import com.sky.casper.skywalker_new_app.R;

public class ActivityAccount extends AppCompatActivity {

    private ImageButton backButton;

    CountryCodePicker ccp;
    EditText editTextCarrierNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        /*Make Activity full screen and hide navigation bar*/
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        /*Make Activity full screen and hide navigation bar*/

        /*Going to Login Activity*/
        backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(view -> openHomeActivity());
        /*Going to Login Activity*/
    }
    /*Going to Login Activity*/
    private void openHomeActivity() {
        Intent intent=new Intent(this, ActivityHome.class);
        startActivity(intent);
    }
    /*Going to Login Activity*/
}
