package com.sky.casper.skywalker_new_app.Helpers;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Cache {
    Context ctx;

    public Cache(Context c){
        this.ctx = c;
    }

    public boolean saveUserToken(String token){
        deleteServerToken();
        String folders = ctx.getCacheDir().getAbsolutePath()+"/";
        String filePath = folders + "serverToken.txt";
        File f = new File(filePath);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter fw =  new FileWriter(filePath);
            BufferedWriter bw =  new BufferedWriter(fw);
            bw.write(token);
            Log.e("WRITEFILE",token+" WRITE");

            if (bw != null)
                bw.close();

            if (fw != null)
                fw.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean deleteServerToken(){
        File f = new File(ctx.getCacheDir().getAbsolutePath()+"/serverToken.txt");
        return f.delete();
    }

    public String getServerToken(){
        File f = new File(ctx.getCacheDir().getAbsolutePath()+"/serverToken.txt");

        if(f.exists()){
            String temp = "";
            Scanner scanner = null;
            try {
                scanner = new Scanner(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (scanner.hasNextLine()) {
                temp += scanner.nextLine();
            }
            return temp;
        }
        else{
            deleteServerToken();
            try {
                saveUserToken(new ServerRequest(ctx).execute(new DatabaseHelper(ctx).getUserId(),Settings.URLS.URL_TOKEN).get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return getServerToken();
        }
    }
}
