package com.sky.casper.skywalker_new_app.Fragments;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sky.casper.skywalker_new_app.Adapter.ListAdapter;
import com.sky.casper.skywalker_new_app.Helpers.Cache;
import com.sky.casper.skywalker_new_app.Helpers.DatabaseHelper;
import com.sky.casper.skywalker_new_app.Helpers.JsonHelper;
import com.sky.casper.skywalker_new_app.Helpers.ServerRequest;
import com.sky.casper.skywalker_new_app.Helpers.Settings;
import com.sky.casper.skywalker_new_app.Models.CVProfile;
import com.sky.casper.skywalker_new_app.Models.Type;
import com.sky.casper.skywalker_new_app.R;

import org.json.JSONException;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkExperienceFragment extends Fragment implements ServerRequest.AsyncResponse {

    private FloatingActionButton fabAddWork;
    private RecyclerView recyclerView;
    private LinearLayout experienceForm,addLinear;
    private Cache cache;
    private DatabaseHelper db;
    private AppCompatCheckBox public_cvs;

    public WorkExperienceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_experience, container, false);
        cache = new Cache(getActivity());
        CVProfile profile = cache.getCVProfile();
        db = new DatabaseHelper(getActivity());
        if(profile == null){
            Settings.getCandidateDetails();
            profile = cache.getCVProfile();
        }
        public_cvs = view.findViewById(R.id.checkbox_public);

        recyclerView = view.findViewById(R.id.recyclerViewWork);
        fabAddWork = view.findViewById (R.id.fabImageButtonWork);

        experienceForm = view.findViewById(R.id.linearLayout_work_experience);
        addLinear = view.findViewById(R.id.linearLayout_add);

        public_cvs.setChecked( profile.getPrivacy(Settings.BIO_INFO.BioPermissions.AllowWorkExperiences) == null ? false : profile.getPrivacy(Settings.BIO_INFO.BioPermissions.AllowWorkExperiences)); /// set privacy value

        public_cvs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {  /// when the user changes the privacy value then a server request will be called to change tht on server
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                new ServerRequest(getActivity(),  WorkExperienceFragment.this).execute(Settings.CONNECTION_TYPES.POST, "Id",db.getUserId(),"Token",cache.getServerToken(),
                        "Attribute",Settings.BIO_INFO.BioPermissions.AllowWorkExperiences, "Visibility", isChecked ? "1" : "0",Settings.URLS.CHANGE_PRIVACY);
            }
        });

        ListAdapter listAdapter = new ListAdapter(profile,this,db);
        recyclerView.setAdapter(listAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        fabAddWork.setOnClickListener(view12 -> {
            recyclerView.setVisibility(View.GONE); /// Vanish the list
            addLinear.setVisibility(View.GONE);
            experienceForm.setVisibility(View.VISIBLE);  /// show work experience form
            List<Type> jobPositions = db.getPrimaryTypes(Settings.JOB_CATEGORIES.JSON_JOBPOSITION_TYPE);
            // TODO appers job position in order to the user choose the the desired value
        });


        return view;
    }

    @Override
    public void handleAnswer(String answer) {
        Log.e("WORK EXPER ANSWER",answer);
        if(answer.equals(Settings.ERROR_MSG.ERROR_SRVR)){ // server exception or general server problem
            Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.server_error),Toast.LENGTH_LONG).show();
        }
        else if(answer.equals(Settings.ERROR_MSG.NO_INTERNET)){ // device is not connecting to the network
            Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.no_internet),Toast.LENGTH_LONG).show();
        }
        else{
            try{
                JsonHelper jsonHelper = new JsonHelper(answer);
                if(jsonHelper.containsAttribute("status")){   //// privacy flag changed
                    if(jsonHelper.getStatus().toLowerCase().equals("success")){   //// success
                        Toast.makeText(getActivity(),this.getResources().getString(R.string.success_changes_privacy),Toast.LENGTH_LONG).show();

                        CVProfile profile = cache.getCVProfile();
                        if(profile == null){
                            Settings.getCandidateDetails();
                            profile = cache.getCVProfile();
                        }

                        profile.setPrivacy(Settings.BIO_INFO.BioPermissions.AllowWorkExperiences,public_cvs.isChecked()); /// save the profile when the flaf changed

                        cache.saveProfile(profile);
                    }
                    else{  /// fail
                        public_cvs.setChecked(!public_cvs.isChecked());
                        Toast.makeText(getActivity(),this.getResources().getString(R.string.general_error),Toast.LENGTH_LONG).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}