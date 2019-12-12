package com.sky.casper.skywalker_new_app.Helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import static java.net.Proxy.Type.HTTP;

public class ServerRequest extends AsyncTask<String, String, String> {

    public interface AsyncResponse { // interface which is implemented by the activity in order to handle the server answer
        void handleAnswer(String answer);
    }

    private String connectionType;
    private Context context;
    private AsyncResponse response;

    public ServerRequest(Context context) {
        this.context = context;
    }

    public ServerRequest(Context context, AsyncResponse r){ // get context and where to handle the response
        this.context = context;
        this.response = r;
    }

    @Override
    protected String doInBackground(String... data) { // execute the request to server
        if(Settings.isNetworkConnected(context)){ // check internet connection
            connectionType = data[0];               // request type (simple get or post request or handling file )
            if(connectionType.equals(Settings.CONNECTION_TYPES.FILE)){
                // not constructed yet
                // maybe we'll use different types for download upload etc.
                return null;
            }
            else if(connectionType.equals(Settings.CONNECTION_TYPES.JSON)){
                String json = data[1];
                String url = data[2];
                return postJson(url,json);
            }
            else{
                String url = data[data.length-1];        /// get url
                HashMap<String,String> paramsValues = new HashMap<>(); /// parameter name : value
                for(int i=1; i<data.length-1; i+=2){
                    paramsValues.put(data[i],data[i+1]); /// put name : value
                }
                return performRequest(paramsValues,url); /// do the request
            }
        }
        else{
            return Settings.ERROR_MSG.NO_INTERNET;
        }
    }

    @Override
    protected void onPreExecute() { /// do things before execute the request such as showing dialog for loading
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) { // do things after the execution such as dismiss dialog
        if(response!=null) {
            response.handleAnswer(s);
        }
    }

    /*   convert the parameters and values into string  in order to pass them in the output stream of the connection */
    private String getPostDataString(Map<String, String> postValues) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean firstAppend = true;

        for (Map.Entry<String,String> entry : postValues.entrySet()){

            if(firstAppend){
                firstAppend = false;
            }
            else{
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));

        }

        return result.toString();
    }

    private String performRequest(HashMap<String, String> requestValues, String requestURL) {
        URL url;
        String response = "";
        HttpURLConnection conn = null;

        try{

            requestURL = requestURL.replaceAll(" ","%20").trim(); // clean the url string
            Log.e("URL",requestURL);
            url = new URL(requestURL);   // initialise url
            conn = (HttpURLConnection) url.openConnection(); // open connection with server
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            if(connectionType.equals(Settings.CONNECTION_TYPES.POST)){ /// if the method is post
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream(); /// open output stream

                if (requestValues != null) {
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(requestValues)); /// pass the data to the stream

                    writer.flush();
                    writer.close();
                    os.close();
                }
            }
            else{ /// if the method is get
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept-Charset", "UTF-8");
            }




            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) { // if the server does not throw exception
                String line;

                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) { /// take the answer
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

    private String postJson(String urlAdress, String json){  //// post to server a json
        InputStream inputStream = null;
        String result = Settings.ERROR_MSG.ERROR_SRVR;

        try{
            URL url = new URL(urlAdress); //// set the parametrs of connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            Log.e("JSON",json);
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());  /// open connection
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(json); /// send json

            os.flush();
            os.close();

            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) { // if the server does not throw exception
                String line;

                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result="";
                while ((line=br.readLine()) != null) { /// take the answer
                    result+=line;

                }
            }
            else {
                result=Settings.ERROR_MSG.ERROR_SRVR;

            }

            conn.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

}
