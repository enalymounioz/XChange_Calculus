package com.sky.casper.skywalker_new_app.Helpers;

import android.util.Pair;

import com.google.gson.JsonObject;

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

    public Map decodePersonalInfo(){ //// get personal details and some bio values
        JSONObject personDetaills;
        HashMap<String,String> personalAttr = new HashMap<String,String>();
        try{
            JSONArray jsonArray=jsonObject.getJSONArray("Items");
            for(int i=0; i<jsonArray.length(); i++){
                personDetaills = jsonArray.getJSONObject(i);
                Iterator keys = personDetaills.keys();

                while(keys.hasNext()){
                    String currentDynamicKey = (String)keys.next();
                    personalAttr.put(currentDynamicKey,personDetaills.getString(currentDynamicKey));
                }
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return personalAttr;
    }

    public boolean invalidToken(){
        try {
            return jsonObject.getString("Status").toLowerCase().contains("fail") && jsonObject.getString("Response").toLowerCase().contains("invalid");
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
