package com.sky.casper.skywalker_new_app.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.sky.casper.skywalker_new_app.R;

public class ActivityAbout extends AppCompatActivity {

    private Button skipButton;//Variable for the Skip Button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        /*Make Activity full screen and hide navigation bar*/
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        /*Going to Home Activity*/
        skipButton = findViewById(R.id.button_skip);
        skipButton.setOnClickListener(view -> openActivityHome());
        /*Going to Home Activity*/


    }
    /*Going to Home Activity*/
    private void openActivityHome() {
        Intent intent=new Intent(this, ActivityHome.class);
        startActivity(intent);
    }
    /*Going to Home Activity*/

}
