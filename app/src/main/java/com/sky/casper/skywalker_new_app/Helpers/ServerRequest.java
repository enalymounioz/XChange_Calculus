package com.sky.casper.skywalker_new_app.Helpers;

import android.content.Context;
import android.net.Uri;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
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
                if(data[1].equals("Id")){ /// id data contains Id name parameter then upload the file
                    String id_post_name = data[1];
                    String id = data[2];
                    String post_cv_name = data[3];
                    String file_name = data[4];
                    String path = data[5];
                    String url = data[6];
                    return uploadConnectedFiles(url,post_cv_name,file_name,path,id_post_name,id);
                }
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
            conn.setReadTimeout(150000);
            conn.setConnectTimeout(150000);
            if(connectionType.equals(Settings.CONNECTION_TYPES.POST)){ /// if the method is post
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream(); /// open output stream

                if (requestValues != null) {
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, StandardCharsets.UTF_8));
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


    private String uploadConnectedFiles(String...values){  /// Upload resume to the user's profile in server, at the same time this functions sends useful parameters such as user Id.
        int value_pointer = 1;
        String url = values[0];
        int fbyte,buffersize,cbuffer;
        int maxbuffer = 1024*1024;
        final String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****" + Long.toString(System.currentTimeMillis())+ "*****";
        BufferedReader reader = null;
        String responseMsg="";

        try {
            URL url_upload = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url_upload.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data; charset=UTF-8; boundary=" + boundary);
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            FileInputStream fileInputStreamCV=null;
            FileInputStream fileInputStreamLetter=null;


            String postName = "\"" +values[value_pointer++]+ "\"";
            String CvName = values[value_pointer++];
            String sourceCv;
            String CvPath = values[value_pointer++];

            if(CvName.startsWith("http")){  /// the file might be a link
                sourceCv = CvName;
            }
            else {
                if(CvPath==null|| CvPath.equals("")){
                    sourceCv = "";
                }
                else{
                    if(CvPath.contains(CvName))
                    {
                        sourceCv = CvPath;
                    }
                    else {
                        sourceCv = CvPath + "/" + CvName;
                    }
                }
            }


            Log.e("UPLOADFUNC"," "+sourceCv+" "+postName+" "+CvName+" "+CvPath);
            if(sourceCv.startsWith("http")){
                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name="+postName+";"+lineEnd);
                dos.writeBytes("Content-Type: text/plain; charset=UTF-8"+lineEnd);
                dos.writeBytes(lineEnd+CvPath+lineEnd);
                dos.writeBytes(lineEnd);
            }
            else if(!sourceCv.equals("")){

                Log.e("TYPEUPL","FILE "+sourceCv);

                File sfile = new File(sourceCv);
                try {  /// connect the stream with file in order to send the file as the buffer size initiate
                    fileInputStreamCV = new FileInputStream(String.valueOf(context.getContentResolver().openInputStream(Uri.parse(sfile.getPath()))));
                }catch (Exception e){
                    fileInputStreamCV = new FileInputStream(sfile);
                }

                /*Send the useful parameters */
                CvName = Settings.randomIdentifier()+"."+CvName.split("\\.")[CvName.split("\\.").length-1];
                Log.e("NEWNAME ",CvName+" "+postName+" "+CvName.split(".").length+" ");
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form/data; name="+postName+"; filename=\"" + CvName + "\"" + lineEnd);
                dos.writeBytes("Content-Type: text/plain; charset=UTF-8"+lineEnd);
                dos.writeBytes(lineEnd);

                fbyte = fileInputStreamCV.available();
                buffersize = Math.min(maxbuffer, fbyte);
                byte[] buffer = new byte[buffersize];
                cbuffer = fileInputStreamCV.read(buffer, 0, buffersize);
                while (cbuffer > 0) {
                    dos.write(buffer, 0, buffersize);
                    fbyte = fileInputStreamCV.available();
                    buffersize = Math.min(maxbuffer, fbyte);
                    cbuffer = fileInputStreamCV.read(buffer, 0, buffersize);
                }
                dos.writeBytes(lineEnd);
            }


            String id_name = "\""+values[value_pointer++]+"\"";
            dos.writeBytes(twoHyphens+boundary+lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name="+id_name+";"+lineEnd);
            dos.writeBytes("Content-Type: text/plain; charset=UTF-8"+lineEnd);
            dos.writeBytes(lineEnd+values[value_pointer]+lineEnd);
            dos.writeBytes(lineEnd);


            int responseCode=conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) { //// Get the response from the server
                String line;
                if(fileInputStreamLetter!=null) {
                    fileInputStreamLetter.close();
                }
                if(fileInputStreamCV!=null){
                    fileInputStreamCV.close();
                }
                dos.flush();
                dos.close();

                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    responseMsg+=line;

                }
            }
            else {
                responseMsg=Settings.ERROR_MSG.ERROR_SRVR;
            }
            Log.e("RESPONSEMSG",responseMsg);
        }catch(Exception e){
            e.printStackTrace();
        }

        return responseMsg;

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
