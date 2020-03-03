package com.sky.casper.skywalker_new_app.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sky.casper.skywalker_new_app.Adapter.SearchAdapter;
import com.sky.casper.skywalker_new_app.R;

public class SearchFragment extends Fragment {

    public SearchFragment(){
        //Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        SearchAdapter adapter = new SearchAdapter(new String[]{"Example One", "Example Two", "Example Three", "Example Four", "Example Five" , "Example Six" , "Example Seven"});
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

}