package com.sky.casper.skywalker_new_app.Helpers;

import org.json.JSONException;
import org.json.JSONObject;

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
}
