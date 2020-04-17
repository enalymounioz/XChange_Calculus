package com.sky.casper.skywalker_new_app.Helpers;

import android.util.Log;
import android.util.Pair;

import com.google.gson.JsonObject;
import com.sky.casper.skywalker_new_app.Models.Advert;
import com.sky.casper.skywalker_new_app.Models.PageInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonHelper {
    private String jsonString;
    private JSONObject jsonObject;

    public  JsonHelper(String strJson) throws JSONException {
            this.jsonString = strJson;
            this.jsonObject = new JSONObject(this.jsonString);
    }

    public String getStatus() throws JSONException {
        try {
            return jsonObject.getString("Status");
        }catch (JSONException e){
            return jsonObject.getString("status");
        }
    }

    public String getMessage() throws JSONException {
        try {
            return jsonObject.getString("Message");
        } catch (JSONException e) {
            e.printStackTrace();
            return jsonObject.getString("Response");
        }
    }

    public boolean containsAttribute(String attribute){
        return jsonObject.has(attribute);
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

    public String[] decodeBioUrls(){   /// decode resumes'  url
        String url1,url2,url3;
        String[] bios = new String[3];
        try {
            url1 = this.getAttribute("Cv");
            url2 = this.getAttribute("Cv2");
            url3 = this.getAttribute("Cv3");

            if(url1.equals("")){
                bios[0] = null;
            }
            else{
                bios[0] = url1;
            }

            if(url2.equals("")){
                bios[1] = null;
            }
            else{
                bios[1] = url2;
            }

            if(url3.equals("")){
                bios[2] = null;
            }
            else{
                bios[2] = url3;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bios;
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

    public Advert[] decodeAdverts(boolean no_anonymous) { /// decode ads from json string
        Advert[] result = null;
        boolean anonymous;
        try {
            JSONArray jsonArray = jsonArray = jsonObject.getJSONArray("Items");
            int size = jsonArray.length();
            result = new Advert[size];
            for (int i = 0; i < size; i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String title = obj.getString(Settings.ADS.TITLE);
                String text = obj.getString(Settings.ADS.TEXT);
                String img = obj.getString(Settings.ADS.LOGO);
                String id = obj.getString(Settings.ADS.ID);
                String empT = obj.getString(Settings.ADS.EMPTYPE);
                String region = obj.getString(Settings.ADS.LOCATION);
                String date = obj.getString(Settings.ADS.PUBLISH_DATE);
                String name = obj.getString(Settings.ADS.NAME);
                String client = obj.getString(Settings.ADS.CLIENT_ID);
                String isAnonymous = obj.getString(Settings.ADS.IS_ANONYMOUS);
                String clientName = obj.getString(Settings.ADS.CLIENT_NAME);
                String anonymuos_title = obj.getString(Settings.ADS.ANONYMOUS_TITLE);
                if (isAnonymous.equals("1")) {
                    anonymous = true;
                } else {
                    anonymous = false;
                }
                if (anonymous && no_anonymous) {
                    continue;
                }
//                text = parseText(text);
//                region = parseHtml(region);
                Log.e("JSONAD", i + "");
                result[i] = new Advert(text, img, title, id, empT, region, date, client, name, anonymous, clientName, null, anonymuos_title);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public PageInfo decodePagedsInfo(){  /// decode page info from a json string
        int total_pages=0,total_objs=0,from=0,to=0,curr_page=0;
        try {
            total_pages=jsonObject.getInt(Settings.ADS.TOTAL_PAGES);
            total_objs=jsonObject.getInt(Settings.ADS.TOTAL_ADS);
            from=jsonObject.getInt(Settings.ADS.START_ADS);
            to=jsonObject.getInt(Settings.ADS.END_ADS);
            curr_page=jsonObject.getInt(Settings.ADS.PAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new PageInfo(total_pages,total_objs,curr_page,from,to);
    }
}
