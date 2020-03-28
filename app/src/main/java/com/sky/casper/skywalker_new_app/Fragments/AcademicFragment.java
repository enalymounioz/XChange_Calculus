package com.sky.casper.skywalker_new_app.Fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import java.util.Map;

public class AcademicFragment extends Fragment {

    private FloatingActionButton fabAddEducation, fabAddUniversity, fabAddLanguage, fabAddSeminars;
    private Animation fabOpen, fabClose,rotateForward,rotateBackward;
    private TextView textViewUniversity, textViewLanguage,textViewSeminars;
    private LinearLayout universityForm,languageForm,seminarForm;
    private ConstraintLayout addLinear;
    private RecyclerView recyclerView;
    boolean isOpen =false;
    private CVProfile profile;
    private Cache cache;
    private DatabaseHelper db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_academic, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewAcademic);
        cache = new Cache(getActivity()); // create cache handler
        profile = cache.getCVProfile(); /// get profile from cache
        db = new DatabaseHelper(getActivity()); // create database handler
        if(profile == null){ // if profile not exist in cache, download it from server
            Settings.getCandidateDetails(); /// Get the profile
            profile = cache.getCVProfile();
        }
        ListAdapter listAdapter = new ListAdapter(profile,this,db); /// construct he adapter
        recyclerView.setAdapter(listAdapter);  // set the adapter to th recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        textViewUniversity = view.findViewById(R.id.textViewUniversity);
        universityForm = view.findViewById(R.id.linearLayout_academic);
        textViewLanguage = view.findViewById(R.id.textViewLanguage);
        textViewSeminars = view.findViewById(R.id.textViewSeminars);

        addLinear = view.findViewById(R.id.linearLayout_add);

        /*Floating action Button for Academic*/
        fabAddEducation = view.findViewById (R.id.fabImageButtonEducation);
        fabAddUniversity = view.findViewById(R.id.fabImageButtonUniversity);
        fabAddLanguage = view.findViewById(R.id.fabImageButtonLanguage);
        fabAddSeminars = view.findViewById(R.id.fabImageButtonSeminars);

        fabOpen = AnimationUtils.loadAnimation(getContext(),R.anim.fab_edit_picture_profile_open);
        fabClose = AnimationUtils.loadAnimation(getContext(),R.anim.fab_edit_picture_profile_close);

        rotateForward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        rotateBackward= AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backwards);

        /*Floating action Button for Academic*/
        fabAddEducation.setOnClickListener(view1 -> animFab()
        );
        fabAddUniversity.setOnClickListener(view12 -> {
            animFab();
//            Toast.makeText(getContext(),"University fab Clicked. Replace this Action",Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
            addLinear.setVisibility(View.GONE);
            universityForm.setVisibility(View.VISIBLE);
            Map<String, List<Type>> levelsUniversities = db.getTypesAndParents(Settings.ACADEMIC.JSON_UNIVERSITY_TYPE);

        });
        fabAddLanguage.setOnClickListener(view13 -> {
            animFab();
//            Toast.makeText(getContext(),"Language fab Clicked. Replace this Action",Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
            addLinear.setVisibility(View.GONE);
            /// TODO Language form, get languages and certifications from db
            List<Type> languages = db.getPrimaryTypes(Settings.ACADEMIC.JSON_FOREIGN_LANG_TYPE);
            Map<String,List<Type>> certifications = db.getTypesAndParents(Settings.ACADEMIC.JSON_CERTIFICATION_LANG_TYPE);

        });
        fabAddSeminars.setOnClickListener(view14 -> {
            animFab();
            recyclerView.setVisibility(View.GONE);
            addLinear.setVisibility(View.GONE);
//            Toast.makeText(getContext(),"Seminars fab Clicked. Replace this Action",Toast.LENGTH_SHORT).show();
            /// TODO seminar form
        });

        return view;
    }
    private void animFab(){
        if (isOpen){
            fabAddEducation.startAnimation(rotateBackward);
            fabAddUniversity.startAnimation(fabClose);
            fabAddLanguage.startAnimation(fabClose);
            fabAddSeminars.startAnimation(fabClose);
            textViewUniversity.startAnimation(fabClose);
            textViewLanguage.startAnimation(fabClose);
            textViewSeminars.startAnimation(fabClose);

            fabAddUniversity.setClickable(false);
            fabAddLanguage.setClickable(false);
            fabAddSeminars.setClickable(false);
            isOpen=false;
        }
        else{
            fabAddEducation.startAnimation(rotateForward);
            fabAddUniversity.startAnimation(fabOpen);
            fabAddLanguage.startAnimation(fabOpen);
            fabAddSeminars.startAnimation(fabOpen);
            textViewUniversity.startAnimation(fabOpen);
            textViewLanguage.startAnimation(fabOpen);
            textViewSeminars.startAnimation(fabOpen);
            fabAddUniversity.setClickable(true);
            fabAddLanguage.setClickable(true);
            fabAddSeminars.setClickable(true);
            isOpen=true;

        }
    }
}


