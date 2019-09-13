package com.sky.casper.skywalker_new_app.Helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class ServerRequest extends AsyncTask<String, String, String> {

    public interface AsyncResponse {
        void handleAnswer(String answer);
    }

    private String connectionType;
    private Context context;
    private AsyncResponse response;

    public ServerRequest(Context ctx){
        this.context = ctx;
    }

    public ServerRequest(Context ctx, AsyncResponse r){
        this.context = ctx;
        this.response = r;
    }

    @Override
    protected String doInBackground(String... data) {
        if(Settings.checkInternetAccess(context)){
            connectionType = data[0];
            if(connectionType.equals(Settings.CONNECTION_TYPES.FILE)){
                return null;
            }
            else{
                String url = data[data.length-1];
                HashMap<String,String> paramsValues = new HashMap<>();
                for(int i=1; i<data.length-1; i+=2){
                    paramsValues.put(data[i],data[i+1]);
                }
                return performRequest(paramsValues,url);
            }
        }
        else{
            return Settings.ERROR_MSG.NO_INTERNET;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        response.handleAnswer(s);
    }

    private String getPostDataString(Map<String, String> postValues) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String,String> entry : postValues.entrySet()){
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private String performRequest(HashMap<String,String> requestValues, String requestURL){
        URL url;
        String response = "";
        HttpURLConnection conn = null;

        try{

            requestURL = requestURL.replaceAll(" ","%20").trim();
            Log.e("URL",requestURL);
            url = new URL(requestURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            if(connectionType.equals(Settings.CONNECTION_TYPES.POST)){
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();

                if(requestValues != null) {
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(requestValues));

                    writer.flush();
                    writer.close();
                    os.close();
                }
            }
            else{
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept-Charset", "UTF-8");
            }




            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;

                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;

                }
            }
            else {
                response=Settings.ERROR_MSG.ERROR_SRVR;

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return response;
    }

}
