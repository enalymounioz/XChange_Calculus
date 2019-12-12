package com.sky.casper.skywalker_new_app.Models;

import android.util.Pair;

import com.google.gson.JsonObject;
import com.sky.casper.skywalker_new_app.Helpers.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CVProfile implements Serializable { //// cv profile is separated in personal details (name,surname,phone etc.) and bio details(work experience, it skills, education etc.)
    private Map<String,Boolean> privacies;
    private boolean bioVisibility;
    private BioInfo bInfo;
    private PersonalInfo pInfo;

    public CVProfile(Pair<Map,Map> personalBioDetails,Pair<Map,Map> moreBioValues){   /// constructor with parameters the personal and bio details for now the only thing that we want is the privacy values
        privacies = new HashMap<>();
        Map<String,String> simpleValues = moreBioValues.first;
        Map<String,JSONArray> arrayValues = moreBioValues.second;
        JSONArray jsonArray = arrayValues.get(Settings.BIO_INFO.BioPermissions.class.getSimpleName()); /// get privacy values
        for(int i=0; i<jsonArray.length(); i++){
            try {
                JSONObject temp = (JSONObject) jsonArray.get(i);
                String key;
                privacies.put(key=temp.keys().next(),temp.get(key).equals("1"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        bioVisibility = simpleValues.get(Settings.BIO_INFO.VISIBILITY).equals("1"); /// set bio visibility
        privacies.put(Settings.BIO_INFO.VISIBILITY,bioVisibility);
    }

    public Map<String,Boolean> getPrivacies(){
        return this.privacies;
    }

    public void setPrivacy(String key, Boolean value){
        this.privacies.put(key,value);
    }

    public void updatePrivacies(String jsonArrPrivacy){
        try {
            JSONArray jsonArray = new JSONArray(jsonArrPrivacy);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject privacy = jsonArray.getJSONObject(i);
                this.privacies.put(privacy.keys().next(),privacy.getString(privacy.keys().next()).equals("1"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setBioVisibility(boolean v){
        this.bioVisibility = v;
        this.privacies.put(Settings.BIO_INFO.VISIBILITY,v);
    }

    public void setPrivacy(Boolean value){
        for(Map.Entry<String,Boolean> entryPrivacy : this.privacies.entrySet()){
            entryPrivacy.setValue(value);
        }
        this.bioVisibility = value;
    }

    private class BioInfo{  /// bio details

    }

    private class PersonalInfo{ //// personal details

    }
}
