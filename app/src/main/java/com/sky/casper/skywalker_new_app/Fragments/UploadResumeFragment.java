package com.sky.casper.skywalker_new_app.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;
import com.sky.casper.skywalker_new_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadResumeFragment extends Fragment {


    public UploadResumeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_resume, container, false);
    }

}