package com.sky.casper.skywalker_new_app.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.sky.casper.skywalker_new_app.R;

public class ActivitySettings extends AppCompatActivity {
    static Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /*Make Activity full screen and hide navigation bar*/
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        /*Make Activity full screen and hide navigation bar*/

        this.ctx = ActivitySettings.this;
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
    public static class SecurityAndPrivacyFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.security_and_privacy_preferences, rootKey);
//            PreferenceCategory preferenceCategory = findPreference("editpublicprofile");
//            SwitchPreferenceCompat test  = new SwitchPreferenceCompat(ActivitySettings.ctx);
//            test.setChecked(true);
//            test.setTitle("Blaaallajrfj");
//            test.setSummaryOn("Public");
//            test.setSummaryOff("Private");
//            preferenceCategory.addPreference(test);
        }
    }

    public static class LanguageAndRegionFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.language_and_region_preferences, rootKey);
        }
    }
    public static class AccountFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.account_preferences, rootKey);
        }
    }
    public static class ResumeFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.resume_preferences, rootKey);
        }
    }
    public static class NotificationsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.notifications_preferences, rootKey);
        }
    }

    public static class LogoutFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.logout_preferences, rootKey);
        }
    }

}