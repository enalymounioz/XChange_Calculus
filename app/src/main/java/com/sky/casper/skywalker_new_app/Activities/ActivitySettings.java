package com.sky.casper.skywalker_new_app.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
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
import java.util.Set;
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
    public static class SecurityAndPrivacyFragment extends PreferenceFragmentCompat {
        CVProfile cvProfile=null;
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.security_and_privacy_preferences, rootKey);
            final PreferenceCategory preferenceCategory = findPreference("editpublicprofile");
            String[] privaciesTags = Settings.BIO_INFO.BioPermissions.getPermissionsTitles();     //// key values permissions from server
            String[] privaciesTitles = ctx.getResources().getStringArray(R.array.privacy_items);    ///// title values on screen

            /* privaciesTitles and privaciesTags are sorted the same one by one more specifically, the two arrays are represent like a map String->String. The key value permission has the same position with its title */

            if(!initialiseProfile()){  //// get profile
                return;
            }
            Map<String,Boolean>  permissions = cvProfile.getPrivacies();  /// get privacy values connected for each attribute
            for(int i=0; i<privaciesTags.length; i++){     //// for evvery key value
                SwitchPreferenceCompat temp  = new SwitchPreferenceCompat(ActivitySettings.ctx); /// build a preference
                temp.setChecked(permissions.get(privaciesTags[i]));    ///// get the value of the key (true or false)
                temp.setTitle(privaciesTitles[i]);     ///// set the title
                temp.setSummaryOff(ctx.getResources().getString(R.string.private_permissions));
                temp.setSummaryOn(ctx.getResources().getString(R.string.public_permissions));
                temp.setKey(privaciesTags[i]);
                temp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {   //// when the checked value changed
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        cvProfile.setPrivacy(preference.getKey(),(Boolean) newValue);     //// update the value on profile
                        return true;
                    }
                });
//
                preferenceCategory.addPreference(temp);
            }


            SwitchPreferenceCompat entirePrivacy = findPreference("privaciesCheckedUnChecked");  /// global checked value, when the value changed then all the preferences will take the same value
            if(permissions.containsValue(false)){   //// id there is at least one "false" value
                entirePrivacy.setTitle(ctx.getResources().getString(R.string.all_unchecked));  /// set unchecked title

            }
            else{
                entirePrivacy.setTitle(ctx.getResources().getString(R.string.all_checked)); /// set checked title
            }
            entirePrivacy.setChecked(!permissions.containsValue(false));   //// no false value -> true , at least one false value -> false
            entirePrivacy.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    int pCount = preferenceCategory.getPreferenceCount();
                    for(int i = 0; i < pCount; i++) {
                        SwitchPreferenceCompat preferncePrivacy = (SwitchPreferenceCompat) preferenceCategory.getPreference(i);
                        preferncePrivacy.setChecked((Boolean)newValue);
                    }
//                    for(String key : getPreferenceScreen().getSharedPreferences().getAll().keySet()){
//                        Log.e("KEYSPREFERENCES ",key);
//                        SwitchPreferenceCompat preferncePrivacy = findPreference(key);
//                        preferncePrivacy.setChecked((Boolean)newValue);
//                    }
                    cvProfile.setPrivacy((Boolean)newValue); /// update all the values in privacy map
                    if((Boolean)newValue){
                        preference.setTitle(ctx.getResources().getString(R.string.all_checked));
                    }
                    else{
                        preference.setTitle(ctx.getResources().getString(R.string.all_unchecked));
                    }
                    return true;
                }
            });
        }

        @Override
        public void onStop() {   ///// when the fragment stopped set the new values on server
            cache.saveProfile(cvProfile);
            Gson gson = new Gson();
            String json = gson.toJson(cvProfile.getPrivacies());
            try {
                JSONObject jsonObject = new JSONObject(json);
                jsonObject.put("Id",db.getUserId());
                json = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new ServerRequest(ctx).execute(Settings.CONNECTION_TYPES.JSON,json,Settings.URLS.URL_UPDATE_PERMISSIONS);
            super.onStop();
        }

        @Override
        public void onPause() { ///// when the fragment paused set the new values on server
            cache.saveProfile(cvProfile);
            Gson gson = new Gson();
            String json = gson.toJson(cvProfile.getPrivacies());
            try {
                JSONObject jsonObject = new JSONObject(json);
                jsonObject.put("Id",db.getUserId());
                json = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new ServerRequest(ctx).execute(Settings.CONNECTION_TYPES.JSON,json,Settings.URLS.URL_UPDATE_PERMISSIONS);
            super.onPause();
        }

        @Override
        public void onDestroy() { ///// when the fragment destroyed set the new values on server
            cache.saveProfile(cvProfile);
            Gson gson = new Gson();
            String json = gson.toJson(cvProfile.getPrivacies());
            try {
                JSONObject jsonObject = new JSONObject(json);
                jsonObject.put("Id",db.getUserId());
                json = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new ServerRequest(ctx).execute(Settings.CONNECTION_TYPES.JSON,json,Settings.URLS.URL_UPDATE_PERMISSIONS);
            super.onDestroy();
        }

        public boolean initialiseProfile(){
            cvProfile = cache.getCVProfile();  //// get profile from cache
            if(cvProfile==null){   //// if there is nothing on cache
                if(Settings.getCandidateDetails()){   /// take it from the server
                    cvProfile = cache.getCVProfile(); /// save it on cache
                    return true;
                }
                else{
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.general_error), Toast.LENGTH_LONG).show();   //// something wrong happened
                    return false;
                }
            }
            else{   //// if the profile is on cache
                String bioDetails;
                try {
                    bioDetails = new ServerRequest(Skywalker.getContext()).execute(Settings.CONNECTION_TYPES.POST,"Id",db.getUserId(),"Token",cache.getServerToken(),Settings.URLS.BIO_URL).get(); //// take only the privacy values from server
                    Log.e("Permisions details",bioDetails);
                    JsonHelper jsonHelper = new JsonHelper(bioDetails);
                    if(jsonHelper.invalidToken()){
                        cache.saveUserToken(new ServerRequest(Skywalker.getContext()).execute(Settings.CONNECTION_TYPES.POST,"Id",db.getUserId(), Settings.URLS.URL_TOKEN).get());
                        bioDetails = new ServerRequest(Skywalker.getContext()).execute(Settings.CONNECTION_TYPES.POST,"Id",db.getUserId(),"Token",cache.getServerToken(),Settings.URLS.BIO_URL).get();
                        jsonHelper = new JsonHelper(bioDetails);
                    }
                    cvProfile.updatePrivacies(jsonHelper.getAttribute(Settings.BIO_INFO.BioPermissions.class.getSimpleName())); //// update the privacy values
                    cvProfile.setBioVisibility(jsonHelper.getAttribute(Settings.BIO_INFO.VISIBILITY).equals("1")); //// and bio visibility
                    return true;
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.general_error), Toast.LENGTH_LONG).show();
                    return false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.general_error), Toast.LENGTH_LONG).show();
                    return false;
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.general_error), Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }

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