package com.sky.casper.skywalker_new_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sky.casper.skywalker_new_app.DummyData.DummyData;
import com.sky.casper.skywalker_new_app.R;

public class ListAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((ListViewHolder)holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return DummyData.title.length;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mItemText;
        private ImageButton mImageButton;

        public ListViewHolder(View itemView) {
            super(itemView);
            mItemText = itemView.findViewById(R.id.textView_profile_details);
            mImageButton = itemView.findViewById(R.id.image_button_delete);
            mImageButton = itemView.findViewById(R.id.image_button_edit);

            itemView.setOnClickListener(this);
        }

        public void bindView(int position) {
            mItemText.setText(DummyData.title[position]);
        }

        public void onClick(View view){

        }

     }
}

