package com.sky.casper.skywalker_new_app.Helpers;

import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonHelper {
    private String jsonString;
    private JSONObject jsonObject;

    public  JsonHelper(String strJson) throws JSONException {
            this.jsonString = strJson;
            this.jsonObject = new JSONObject(this.jsonString);
    }

    public String getStatus() throws JSONException {
        return jsonObject.getString("Status");
    }

    public String getMessage() throws JSONException {
        return jsonObject.getString("Message");
    }

    public String getAttribute(String attribute) throws JSONException { /// get a specific attribute from the json object
        return jsonObject.getString(attribute);
    }

    public Pair<Map, Map> decodeBioInfo(){     //// get the bio   details
        Map<String, JSONArray> arrayInfo = new HashMap<String,JSONArray>();
        Map<String,String> otherValues = new HashMap<String,String>();
        Iterator keys=jsonObject.keys();
        try {
            while (keys.hasNext()) {
                String currentKey = (String) keys.next();
                if(jsonObject.get(currentKey).toString().startsWith("["))
                    arrayInfo.put(currentKey,jsonObject.getJSONArray(currentKey));
                else{
                    otherValues.put(currentKey,jsonObject.getString(currentKey));
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return new Pair(otherValues,arrayInfo);
    }

    public Pair<Map,Map> decodePersonalAndBioInfo(){ //// get personal details and some bio values
        JSONObject personDetaills,bioDetails;
        HashMap<String,String> personalAttr = new HashMap<String,String>();
        HashMap<String,String> profileAttr = new HashMap<String,String>();
        try{
            JSONArray jsonArray=jsonObject.getJSONArray("Items");
            personDetaills = jsonArray.getJSONObject(0);
            bioDetails = jsonArray.getJSONObject(1);

            Iterator keys = personDetaills.keys();

            while(keys.hasNext()){
                String currentDynamicKey = (String)keys.next();
                personalAttr.put(currentDynamicKey,personDetaills.getString(currentDynamicKey));
            }

            keys = bioDetails.keys();

            while(keys.hasNext()){
                String currentDynamicKey = (String)keys.next();
                profileAttr.put(currentDynamicKey,bioDetails.getString(currentDynamicKey));
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return new Pair(personalAttr,profileAttr);
    }

    public boolean invalidToken(){
        try {
            if(jsonObject.getString("Status").toLowerCase().contains("fail") && jsonObject.getString("Response").toLowerCase().contains("invalid")){
                return true;
            }
            else{
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
