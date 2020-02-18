package com.sky.casper.skywalker_new_app.Activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import com.google.gson.Gson;
import com.sky.casper.skywalker_new_app.Helpers.Cache;
import com.sky.casper.skywalker_new_app.Helpers.DatabaseHelper;
import com.sky.casper.skywalker_new_app.Helpers.JsonHelper;
import com.sky.casper.skywalker_new_app.Helpers.ServerRequest;
import com.sky.casper.skywalker_new_app.Helpers.Settings;
import com.sky.casper.skywalker_new_app.Models.CVProfile;
import com.sky.casper.skywalker_new_app.R;
import com.sky.casper.skywalker_new_app.Skywalker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ActivitySettings extends AppCompatActivity {
    private static Context ctx;
    private static Cache cache;
    private static DatabaseHelper db;

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

        /* context cache database construction*/
        ctx = ActivitySettings.this;
        cache = new Cache(this);
        db = new DatabaseHelper(this);
        /* context cache database construction*/
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            Preference preference = findPreference("security_privacy_fragment");

            /* when the user is not connected do not open privacy settings
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    if(db.getUserId()==null){
                        Toast.makeText(ctx,ctx.getResources().getString(R.string.required_login),Toast.LENGTH_LONG).show();
                        preference.setFragment("");
                        return true;
                    }
                    else{
                        preference.setFragment("com.sky.casper.skywalker_new_app.Activities.ActivitySettings$SecurityAndPrivacyFragment") ;
                        return false;
                    }
                }
            });*/

        }
    }

    public static class LanguageAndRegionFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.language_and_region_preferences, rootKey);
        }
    }


    public static class LogoutFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.logout_preferences, rootKey);
        }
    }

}