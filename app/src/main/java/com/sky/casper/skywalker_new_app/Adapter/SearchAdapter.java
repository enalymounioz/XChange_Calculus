package com.sky.casper.skywalker_new_app.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sky.casper.skywalker_new_app.Models.Advert;
import com.sky.casper.skywalker_new_app.Models.PageInfo;
import com.sky.casper.skywalker_new_app.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{

    private Advert[] ads;
    private Context context;
    private PageInfo pagingInfo;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardView;
        public TextView mTilte,mCompany;
        public ImageView mImageLogo;
        public AppCompatImageButton mImageFav,mImageMail;
        public ProgressBar progressBar;
        public ProgressBar progressBarAds;

        public MyViewHolder(View v){
            super(v);

            mCardView = v.findViewById(R.id.cardView);
            mTilte = v.findViewById(R.id.tvTitle);
            mCompany = v.findViewById(R.id.tvSource);
            mImageFav = v.findViewById(R.id.image_button_fav);
            mImageMail = v.findViewById(R.id.image_button_quick_send);
            mImageLogo = v.findViewById(R.id.image);
            progressBar =v.findViewById(R.id.loader);
            progressBarAds = v.findViewById(R.id.loaderAds);

        }

    }



    public SearchAdapter(Context ctx, Advert[] advs, PageInfo pI){
        this.ads = advs;
        this.context = ctx;
        this.pagingInfo = pI;
    }

    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search,parent,false);
        MyViewHolder viewHolder = new  MyViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Log.e("POSITION ",""+position);
        if(position <= this.ads.length-1){  /// show ad
            holder.progressBarAds.setVisibility(View.GONE);
            holder.mCardView.setVisibility(View.VISIBLE);
            holder.mCompany.setText(ads[position].isAnonymous() ? ads[position].getAnonymous_title() : ads[position].getClientName());
            Glide.with(context).load(ads[position].getImage_url()).listener(new RequestListener<Drawable>() { /// load image
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.mImageLogo);
            holder.mTilte.setText(ads[position].getTitle());
            Log.e("URL IMAGE",ads[position].getImage_url());
        }
        else{ /// this is the last item which is the progressbar
            holder.progressBarAds.setVisibility(View.VISIBLE);
            holder.mCardView.setVisibility(View.GONE);
        }



    }




    public void addAds(Advert[] new_ads, PageInfo pI){
        this.pagingInfo = pI;
//        notifyItemRemoved(getItemCount()-1);
        Advert[] concatenate_arr = new Advert[ads.length + new_ads.length];
        System.arraycopy(ads, 0, concatenate_arr, 0, ads.length);
        System.arraycopy(new_ads, 0, concatenate_arr, ads.length, new_ads.length);
        this.ads = concatenate_arr;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(pagingInfo.getCurrentPage()>=pagingInfo.getTotalPages()) {
            return this.ads.length;
        }
        else{
            return this.ads.length+1;
        }
    }




}
