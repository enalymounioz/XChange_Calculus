package com.sky.casper.skywalker_new_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hbb20.CountryCodePicker;
import com.sky.casper.skywalker_new_app.Helpers.Settings;
import com.sky.casper.skywalker_new_app.R;


public class ActivityAccount extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText editTextCarrierNumber;
    FloatingActionButton fabEditPicture1, fabEditPicture2, fabEditPicture3;
    Animation fabOpen, fabClose,rotateForward,rotateBackward;
    boolean isOpen =false;

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
        ImageButton backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(view -> openHomeActivity());
        /*Going to Login Activity*/

        /*Floating action Button for picture*/
        fabEditPicture1 = findViewById(R.id.fabEditPicture1);
        fabEditPicture2 = findViewById(R.id.fabEditPicture2);
        fabEditPicture3 = findViewById(R.id.fabEditPicture3);

        fabOpen = AnimationUtils.loadAnimation(this,R.anim.fab_edit_picture_profile_open);
        fabClose = AnimationUtils.loadAnimation(this,R.anim.fab_edit_picture_profile_close);

        rotateForward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotateBackward= AnimationUtils.loadAnimation(this,R.anim.rotate_backwards);

        /*Floating action Button for picture*/
        fabEditPicture1.setOnClickListener(view -> animFab()
        );
        fabEditPicture2.setOnClickListener(view -> {
            animFab();
            Toast.makeText(ActivityAccount.this,"Camera fab Clicked. Replace this Action",Toast.LENGTH_SHORT).show();
        });
        fabEditPicture3.setOnClickListener(view -> {
            animFab();
            Toast.makeText(ActivityAccount.this,"Folder fab Clicked. Replace this Action",Toast.LENGTH_SHORT).show();
        });
    }
    private void animFab(){
        if (isOpen){
            fabEditPicture1.startAnimation(rotateBackward);
            fabEditPicture2.startAnimation(fabClose);
            fabEditPicture3.startAnimation(fabClose);
            fabEditPicture2.setClickable(false);
            fabEditPicture3.setClickable(false);
            isOpen=false;
        }
        else{
            fabEditPicture1.startAnimation(rotateForward);
            fabEditPicture2.startAnimation(fabOpen);
            fabEditPicture3.startAnimation(fabOpen);
            fabEditPicture2.setClickable(true);
            fabEditPicture3.setClickable(true);
            isOpen=true;

        }

    }

    /*Going to Login Activity*/
    private void openHomeActivity() {
        Intent intent=new Intent(this, ActivityHome.class);
        startActivity(intent);
    }



}
