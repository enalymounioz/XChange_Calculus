package com.sky.casper.skywalker_new_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.sky.casper.skywalker_new_app.Adapter.ViewPagerAdapter;
import com.sky.casper.skywalker_new_app.Fragments.GenerateFragment;
import com.sky.casper.skywalker_new_app.Fragments.MoreOptionsPrivacyFragment;
import com.sky.casper.skywalker_new_app.Fragments.UploadResumeFragment;
import com.sky.casper.skywalker_new_app.Fragments.AcademicFragment;
import com.sky.casper.skywalker_new_app.Fragments.WorkExperienceFragment;
import com.sky.casper.skywalker_new_app.R;

public class ActivityProfile extends AppCompatActivity {

    private Button skipButton;//Variable for the Skip Button

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

         /*Make Activity full screen and hide navigation bar*/
         View decorView = getWindow().getDecorView();
         decorView.setSystemUiVisibility(
                 View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
         /*Make Activity full screen and hide navigation bar*/

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new UploadResumeFragment(), "Resume");
        viewPagerAdapter.AddFragment(new AcademicFragment(), "Academic");
        viewPagerAdapter.AddFragment(new WorkExperienceFragment(), "Work Experience");
         viewPagerAdapter.AddFragment(new GenerateFragment(), "Generate");
         viewPagerAdapter.AddFragment(new MoreOptionsPrivacyFragment(),"More Options");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

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
