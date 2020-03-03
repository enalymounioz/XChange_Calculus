package com.sky.casper.skywalker_new_app.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.sky.casper.skywalker_new_app.Helpers.Cache;
import com.sky.casper.skywalker_new_app.Helpers.DatabaseHelper;
import com.sky.casper.skywalker_new_app.R;

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
            Preference preferenceSecurity = findPreference("security_privacy_fragment");
            Preference preferenceLogout =  findPreference("logout_fragment");
            /* whether the user does not connected do not open privacy settings*/
            preferenceSecurity.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
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
            });

            if(db.getUserId()!=null){
                preferenceLogout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                        builder.setMessage(ctx.getResources().getString(R.string.logout_message))
                                .setPositiveButton(ctx.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        db.delete_id(db.getUserId());
                                        Intent intent=new Intent(ctx, ActivityHome.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        dialog.dismiss();
                                        startActivity(intent);
                                        ((Activity)ctx).finish();

                                    }
                                })
                                .setNegativeButton(ctx.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                        builder.show();
                        return true;
                    }
                });
            }
            else{
                preferenceLogout.setVisible(false);
            }

        }
    }
     public static class LogoutFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.logout_preferences, rootKey);
        }
    }
    }





