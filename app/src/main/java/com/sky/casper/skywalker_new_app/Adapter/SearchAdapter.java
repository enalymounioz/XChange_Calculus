package com.sky.casper.skywalker_new_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sky.casper.skywalker_new_app.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{
    private String[] mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardView;
        public TextView mTextView;
        public ImageView mImageView;
        public AppCompatImageButton mImageButton;
        public MyViewHolder(View v){
            super(v);

            mCardView = v.findViewById(R.id.cardView);
            mTextView = v.findViewById(R.id.tvTitle);
            mTextView = v.findViewById(R.id.tvSource);
            mImageButton = v.findViewById(R.id.image_button_fav);
            mImageButton=v.findViewById(R.id.image_button_quick_send);

        }
    }

    public SearchAdapter(String[] myDataset){
        mDataset = myDataset;
    }

    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search,parent,false);
        MyViewHolder viewHolder =new  MyViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() { return mDataset.length; }


}
