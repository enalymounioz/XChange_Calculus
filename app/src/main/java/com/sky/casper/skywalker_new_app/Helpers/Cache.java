package com.sky.casper.skywalker_new_app.Helpers;

import android.content.Context;
import android.util.Log;

import com.sky.casper.skywalker_new_app.Models.CVProfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Cache {
    Context ctx;

    public Cache(Context c){
        this.ctx = c;
    }

    public boolean saveUserToken(String token){ //// save user token in a file
        deleteServerToken(); /// delete file if exists
        String folders = ctx.getCacheDir().getAbsolutePath()+"/";
        String filePath = folders + "serverToken.txt";
        File f = new File(filePath); /// make the file
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter fw =  new FileWriter(filePath); //// write the token into the file
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

    public boolean deleteServerToken(){  /// delete file
        File f = new File(ctx.getCacheDir().getAbsolutePath()+"/serverToken.txt");
        return f.delete();
    }

    public String getServerToken(){ /// get the token from the file
        File f = new File(ctx.getCacheDir().getAbsolutePath()+"/serverToken.txt");

        if(f.exists()){ /// read the file
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
                saveUserToken(new ServerRequest(ctx).execute(new DatabaseHelper(ctx).getUserId(),Settings.URLS.URL_TOKEN).get()); //// get the token from the server if not exist
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return getServerToken();
        }
    }

    public boolean saveProfile(CVProfile profile){ /// save the CVprofile object
        deleteProfile();
        try {
            FileOutputStream fos = new FileOutputStream(ctx.getCacheDir().getAbsolutePath() + "/profile.ser"); //// create the file
            ObjectOutputStream oos = new ObjectOutputStream(fos); /// and write the object
            oos.writeObject(profile);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean deleteProfile(){
        return new File(ctx.getCacheDir().getAbsolutePath()+"/profile.ser").delete();

    }

    public CVProfile getCVProfile(){
        CVProfile tempProfile;
        try{
            FileInputStream fis = new FileInputStream(ctx.getCacheDir().getAbsolutePath()+"/profile.ser");
            ObjectInputStream ois = new ObjectInputStream(fis); /// read the file and take the object
            tempProfile = (CVProfile) ois.readObject();
            ois.close();
            return tempProfile;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
