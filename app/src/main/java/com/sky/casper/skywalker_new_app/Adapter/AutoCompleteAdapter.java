package com.sky.casper.skywalker_new_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sky.casper.skywalker_new_app.Helpers.ServerRequest;
import com.sky.casper.skywalker_new_app.Helpers.Settings;
import com.sky.casper.skywalker_new_app.R;

import java.util.concurrent.ExecutionException;

/* This adapter is filter which listens what the user writes and it gives back suggestions */
public class AutoCompleteAdapter extends BaseAdapter implements Filterable {
    private String[] mWords;
    private Context context;
    private String url_suggestions;  /// this url gives the suggestions

    public AutoCompleteAdapter(Context context) {
        this.context = context;
    }

    public AutoCompleteAdapter(Context context, String url){
        this.context = context;
        this.url_suggestions = url;
    }

    @Override
    public int getCount() {
        return mWords.length;
    }

    @Override
    public Object getItem(int i) {
        return mWords[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_suggestion, parent, false);
        }

        TextView word = (TextView) convertView.findViewById(R.id.suggestion);
        word.setText((String) getItem(i));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter myfilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {  /// takes the user word and returns suggestions
                FilterResults filterResults = new FilterResults();
                if(charSequence!=null){
                    String response="";

                    if(url_suggestions!=null) {
                        try {
                            response = new ServerRequest(context).execute(Settings.CONNECTION_TYPES.POST,"q", charSequence.toString(),Settings.URLS.URL_GET_SUGGESTIONS).get();  // get suggestions as json string
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        /// TODO database values
                    }

                    Gson gson = new Gson();
                    mWords = gson.fromJson(response, String[].class); /// decode suggested  words
                    filterResults.values = mWords;
                    filterResults.count = mWords.length;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if(filterResults != null && filterResults.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return myfilter;
    }
}
