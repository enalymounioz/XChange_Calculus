package com.sky.casper.skywalker_new_app.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.OpenableColumns;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.casper.skywalker_new_app.Adapter.ListAdapter;
import com.sky.casper.skywalker_new_app.Dialogs.WarningDialog;
import com.sky.casper.skywalker_new_app.Helpers.Cache;
import com.sky.casper.skywalker_new_app.Helpers.DatabaseHelper;
import com.sky.casper.skywalker_new_app.Helpers.JsonHelper;
import com.sky.casper.skywalker_new_app.Helpers.ServerRequest;
import com.sky.casper.skywalker_new_app.Helpers.Settings;
import com.sky.casper.skywalker_new_app.R;

import org.json.JSONException;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadResumeFragment extends Fragment implements ServerRequest.AsyncResponse, WarningDialog.AsyncDialogAnswer {

    private DatabaseHelper db;
    private String[] bioUrls;
    private int numBios;
    private Pair<String,Integer> deleted_bio;
    private RecyclerView recyclerView;
    private Button fileChoose,fileSave;
    private TextView cv_title;
    private String displayName,path,postName="CvMob";
    private boolean uploadFile;
    private Cache cache;
    public UploadResumeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_resume, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewUploadCV);
        fileChoose = view.findViewById(R.id.button_upload);
        fileSave = view.findViewById(R.id.save_bio);

        cv_title = view.findViewById(R.id.textView_upload_cv);
        uploadFile = false; // flag to upload file, this is important for server response handler
        db = new DatabaseHelper(getActivity()); /// create database handler
        cache = new Cache(getActivity()); /// create cache handler
        bioUrls = new String[3];  /// contains urls of resumes
        numBios = 0;

        getBios();  /// download the available resume urls from server
        selectFile(); /// select a file from the device
        saveFile(); /// upload  the file to server

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }


    private void getBios(){
        new ServerRequest(getActivity(),this).execute(Settings.CONNECTION_TYPES.POST,"Id",db.getUserId(),Settings.URLS.BIOS_URL);
    }

    private void saveFile(){
        fileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile = true;
                new ServerRequest(getActivity(),UploadResumeFragment.this).execute(Settings.CONNECTION_TYPES.FILE,"Id",db.getUserId(),postName,displayName,path,Settings.URLS.UPLOAD_BIO_URL_CONN);
            }
        });
    }

    private void selectFile(){
        fileChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numBios != 3) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
//                    intent.setType("*/*");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    intent.setType(Settings.ACTION_TYPES.FILE_TYPES.length == 1 ? Settings.ACTION_TYPES.FILE_TYPES[0] : "*/*");
                    intent.setType(Settings.ACTION_TYPES.FILE_TYPES[0]);
                    if (Settings.ACTION_TYPES.FILE_TYPES.length > 0) {
                        intent.putExtra(Intent.EXTRA_MIME_TYPES, Settings.ACTION_TYPES.FILE_TYPES);
                    }
                } else {
                    String mimeTypesStr = "";
                    for (String mimeType : Settings.ACTION_TYPES.FILE_TYPES) {
                        mimeTypesStr += mimeType + "|";
                    }
                    intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
                }
                    startActivityForResult(
                            Intent.createChooser(intent, UploadResumeFragment.this.getResources().getString(R.string.choose_file)),
                            Settings.ACTION_TYPES.CHOOSE_FILE);
                }
                else{
                    Toast.makeText(getActivity(),UploadResumeFragment.this.getResources().getString(R.string.not_allowed_upload_cv),Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    @Override
    public void handleAnswer(String answer) {
        Log.e("BIOS ANSWER",answer);
        if(answer.equals(Settings.ERROR_MSG.ERROR_SRVR)){ // server exception or general server problem
            Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.server_error),Toast.LENGTH_LONG).show();
        }
        else if(answer.equals(Settings.ERROR_MSG.NO_INTERNET)){ // device is not connecting to the network
            Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.no_internet),Toast.LENGTH_LONG).show();
        }
        else{
            try {
                JsonHelper jsonHelper = new JsonHelper(answer);
                if(jsonHelper.containsAttribute("Status")){  /// Delete or Upload
                    if(uploadFile){   /// Upload
                        postName = "CvMob";
                        String message = jsonHelper.getMessage();
                        Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
                        if (jsonHelper.getStatus().toLowerCase().equals("success")) { /// success uploaded file
                            this.bioUrls = jsonHelper.decodeBioUrls();
                            this.numBios++;
                            ListAdapter listAdapter = new ListAdapter(this.bioUrls, this, db);
                            recyclerView.setAdapter(listAdapter);
                            fileSave.setVisibility(View.GONE);
                            cv_title.setText(this.getResources().getString(R.string.upload_your_cv));
                        }
                    }
                    else {  /// Delete
                        String message = jsonHelper.getMessage();
                        Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
                        if (jsonHelper.getStatus().toLowerCase().equals("success")) { /// success deleted file
//                        ListAdapter listAdapter = new ListAdapter(this.bioUrls, this, db);
                            ((ListAdapter) recyclerView.getAdapter()).deleteCv();
                            this.numBios--;

//                        recyclerView.notify();
                        }
                    }
                }
                else {   //// Get resumes' url
                    this.bioUrls = jsonHelper.decodeBioUrls();

                    for (String url : this.bioUrls) {
                        if (url != null) {
                            this.numBios++;
                        }
                    }
                    ListAdapter listAdapter = new ListAdapter(this.bioUrls, this, db);
                    recyclerView.setAdapter(listAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void warningResponse(String answ) {  // Handler of warning dialog rsponse for deletion a file
        if(answ.equals(this.getResources().getString(R.string.yes))){
            deleted_bio = ((ListAdapter)this.recyclerView.getAdapter()).getDeletedBio();
            bioUrls[deleted_bio.second] = null;
            uploadFile = false;
            new ServerRequest(getActivity(),this).execute(Settings.CONNECTION_TYPES.POST,"Id",db.getUserId(),Settings.URLS.URL_DELETE_BIO+"/"+deleted_bio.first.replace("1",""));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {  //// Hsndler from chooser menu
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){ /// user choose successfully a file
            Log.e("RETURNED FILE CHOOOSE","resultCode"+resultCode+" requestCode "+requestCode+" data "+data.toString());
            Log.e("Ola","OK");
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            path = myFile.getAbsolutePath();
            displayName = null;

            if (uriString.startsWith("content://")) {  /// decode file info such as name and path
                Cursor cursor = null;
                try {
                    cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
            }

            Log.e("FILE INFO ","Name "+displayName+" Path "+path);
            path = cache.fileFromUri(uri,displayName).getAbsolutePath();

            int i;  /// create the post parameter for the upload request to server
            for(i=0; i<this.bioUrls.length; i++){
                if(bioUrls[i]==null){
                    postName += Integer.toString(i+1);
                    break;
                }
            }

            if(i==bioUrls.length){ /// User can not upload more than 3 resumes in server
                Toast.makeText(getActivity(),this.getResources().getString(R.string.not_allowed_upload_cv),Toast.LENGTH_LONG).show();
            }
            else{
                fileSave.setVisibility(View.VISIBLE);
                cv_title.setText(displayName);
            }

        }
    }
}