package com.sky.casper.skywalker_new_app.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.http.GET;

import com.sky.casper.skywalker_new_app.Adapter.AutoCompleteAdapter;
import com.sky.casper.skywalker_new_app.Adapter.SearchAdapter;
import com.sky.casper.skywalker_new_app.Helpers.Cache;
import com.sky.casper.skywalker_new_app.Helpers.DatabaseHelper;
import com.sky.casper.skywalker_new_app.Helpers.JsonHelper;
import com.sky.casper.skywalker_new_app.Helpers.ServerRequest;
import com.sky.casper.skywalker_new_app.Helpers.Settings;
import com.sky.casper.skywalker_new_app.Models.Advert;
import com.sky.casper.skywalker_new_app.Models.PageInfo;
import com.sky.casper.skywalker_new_app.R;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class SearchFragment extends Fragment implements ServerRequest.AsyncResponse {

    private DatabaseHelper db;
    private Cache cache;
    private boolean firstLoad,loadMore,sendMail;
    private RecyclerView rv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page=1;
    private boolean no_anonymous_ads;
    private Advert[] adverts;
    private PageInfo pageInfo;
    private ProgressBar progressBar;
    private int previous_page;
    private AutoCompleteTextView searchWord;
    private AutoCompleteAdapter autoAdapter;
    private Button searchBtn;


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

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); /// prevent to open keyboard

        db = new DatabaseHelper(getActivity());
        cache = new Cache(getActivity());
        firstLoad = false;
        loadMore = false;
        sendMail = false;
        previous_page = 0;
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        searchWord = rootView.findViewById(R.id.etQuery);
        autoAdapter = new AutoCompleteAdapter(getActivity(), Settings.URLS.URL_GET_SUGGESTIONS);
        searchWord.setAdapter(autoAdapter);

        searchBtn = rootView.findViewById(R.id.btnSearch);

        rv = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);

        String json_ads = (String) cache.getSomething("ads.ser");
        firstLoad=true;
        no_anonymous_ads = false;
        handleAnswer(json_ads);
//        SearchAdapter adapter = new SearchAdapter(new String[]{"Example One", "Example Two", "Example Three", "Example Four", "Example Five" , "Example Six" , "Example Seven"});
//        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    if (loadMore == false && pageInfo.getCurrentPage() < pageInfo.getTotalPages()) {
                        loadMore = true;
                        String keywords = searchWord.getText().toString().replaceAll(" ","").equals("") ? "" : searchWord.getText().toString();
                        if(keywords==null){
                            keywords="";
                        }
                        new ServerRequest(getActivity(),SearchFragment.this).execute(Settings.CONNECTION_TYPES.POST,"page",Integer.toString(++page),"keywords",keywords,Settings.URLS.URL_ADS); /// TODO maybe should change later
                    }

                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                previous_page = page;
                page=0;
                firstLoad = true;
                String keywords = searchWord.getText().toString().replaceAll(" ","").equals("") ? "" : searchWord.getText().toString();
                if(keywords==null){
                    keywords="";
                }
                new ServerRequest(getActivity(),SearchFragment.this).execute(Settings.CONNECTION_TYPES.POST,"page",Integer.toString(++page),"keywords",keywords,Settings.URLS.URL_ADS); /// TODO maybe should change late
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous_page = page;
                page=0;
                firstLoad = true;
                String keywords = searchWord.getText().toString().replaceAll(" ","").equals("") ? "" : searchWord.getText().toString();
                if(keywords==null){
                    keywords="";
                }
                Log.e("FirstLoad","keywords="+keywords);
                new ServerRequest(getActivity(),SearchFragment.this).execute(Settings.CONNECTION_TYPES.POST,"page",Integer.toString(++page),"keywords",keywords,Settings.URLS.URL_ADS);
            }
        });

        return rootView;
    }

    private void setAdverts(JsonHelper jsonHelper){
        adverts = jsonHelper.decodeAdverts(no_anonymous_ads);
        pageInfo = jsonHelper.decodePagedsInfo();
        if(firstLoad){
            Log.e("Size ads","Length: "+adverts.length);
            SearchAdapter searchAdapter = new SearchAdapter(getActivity(),adverts,pageInfo);
            rv.setAdapter(searchAdapter);
        }
        else if(loadMore){
            ((SearchAdapter)rv.getAdapter()).addAds(adverts,pageInfo);
        }
    }

    @Override
    public void handleAnswer(String answer) {
        if(answer == null || answer.isEmpty()){
            Toast.makeText(getActivity(),SearchFragment.this.getResources().getString(R.string.general_error),Toast.LENGTH_LONG).show();
            if(previous_page!=0){
                page=previous_page;
            }
        }
        else if(answer.equals(Settings.ERROR_MSG.ERROR_SRVR)){ // server exception or general server problem
            Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.server_error),Toast.LENGTH_LONG).show();
            if(previous_page!=0){
                page=previous_page;
            }
        }
        else if(answer.equals(Settings.ERROR_MSG.NO_INTERNET)){ // device is not connecting to the network
            Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.no_internet),Toast.LENGTH_LONG).show();
            if(previous_page!=0){
                page=previous_page;
            }
        }
        else{
            Log.e("Handle answ",answer);
            try {
                JsonHelper jsonHelper = new JsonHelper(answer);

                if (firstLoad) {
                    setAdverts(jsonHelper);
                    firstLoad=false;
                    swipeRefreshLayout.setRefreshing(false);
                    previous_page = 0;
                } else if (loadMore) {
                    setAdverts(jsonHelper);
                    loadMore=false;
                } else if (sendMail) {

                } else {

                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}