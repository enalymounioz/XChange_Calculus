package com.sky.casper.skywalker_new_app.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sky.casper.skywalker_new_app.Adapter.ListAdapter;
import com.sky.casper.skywalker_new_app.R;

public class AcademicFragment extends Fragment {

    private FloatingActionButton fabAddEducation, fabAddUniversity, fabAddLanguage, fabAddSeminars;
    private Animation fabOpen, fabClose,rotateForward,rotateBackward;
    private TextView textViewUniversity, textViewLanguage,textViewSeminars;
    boolean isOpen =false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.fragment_academic, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewAcademic);

        ListAdapter listAdapter = new ListAdapter();
        recyclerView.setAdapter(listAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        textViewUniversity = view.findViewById(R.id.textViewUniversity);
        textViewLanguage = view.findViewById(R.id.textViewLanguage);
        textViewSeminars = view.findViewById(R.id.textViewSeminars);

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
            Toast.makeText(getContext(),"University fab Clicked. Replace this Action",Toast.LENGTH_SHORT).show();
        });
        fabAddLanguage.setOnClickListener(view13 -> {
            animFab();
            Toast.makeText(getContext(),"Language fab Clicked. Replace this Action",Toast.LENGTH_SHORT).show();
        });
        fabAddSeminars.setOnClickListener(view14 -> {
            animFab();
            Toast.makeText(getContext(),"Seminars fab Clicked. Replace this Action",Toast.LENGTH_SHORT).show();
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


