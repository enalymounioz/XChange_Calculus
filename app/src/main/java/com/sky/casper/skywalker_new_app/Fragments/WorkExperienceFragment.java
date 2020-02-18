package com.sky.casper.skywalker_new_app.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.casper.skywalker_new_app.Adapter.ListAdapter;
import com.sky.casper.skywalker_new_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkExperienceFragment extends Fragment {


    public WorkExperienceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_experience, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewWork);

        ListAdapter listAdapter = new ListAdapter();
        recyclerView.setAdapter(listAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }

}