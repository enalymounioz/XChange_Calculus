package com.sky.casper.skywalker_new_app.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sky.casper.skywalker_new_app.Adapter.ListAdapter;
import com.sky.casper.skywalker_new_app.Helpers.Cache;
import com.sky.casper.skywalker_new_app.Helpers.DatabaseHelper;
import com.sky.casper.skywalker_new_app.Helpers.Settings;
import com.sky.casper.skywalker_new_app.Models.CVProfile;
import com.sky.casper.skywalker_new_app.Models.Type;
import com.sky.casper.skywalker_new_app.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkExperienceFragment extends Fragment {

    private FloatingActionButton fabAddWork;
    private RecyclerView recyclerView;
    private LinearLayout experienceForm,addLinear;
    private CVProfile profile;
    private Cache cache;
    private DatabaseHelper db;

    public WorkExperienceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_experience, container, false);
        cache = new Cache(getActivity());
        profile = cache.getCVProfile();
        db = new DatabaseHelper(getActivity());
        if(profile == null){
            Settings.getCandidateDetails();
            profile = cache.getCVProfile();
        }

        recyclerView = view.findViewById(R.id.recyclerViewWork);
        fabAddWork = view.findViewById (R.id.fabImageButtonWork);

        experienceForm = view.findViewById(R.id.linearLayout_work_experience);
        addLinear = view.findViewById(R.id.linearLayout_add);

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

}