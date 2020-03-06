package com.sky.casper.skywalker_new_app.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.sky.casper.skywalker_new_app.DummyData.DummyData;
import com.sky.casper.skywalker_new_app.Helpers.DatabaseHelper;
import com.sky.casper.skywalker_new_app.Models.CVProfile;
import com.sky.casper.skywalker_new_app.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter {

    private CVProfile profile = null;
    private Fragment fragment;
    private List<CVProfile.BioInfo.BioAttributes> attributes;
    private RecyclerView recyclerView;
    private LinearLayout form;
    private ConstraintLayout addAcademic;
    private LinearLayout addExperience;
    private DatabaseHelper db;

    public ListAdapter(CVProfile pr, Fragment fr, DatabaseHelper d){
        this.profile = pr;
        this.fragment = fr;
        this.db = d;
        if(this.fragment.getClass().getSimpleName().toLowerCase().contains("academic")){
            this.attributes = profile.getAcademic();
        }
        else if(this.fragment.getClass().getSimpleName().toLowerCase().contains("experience")){
           this.attributes = profile.getExperience();
        }
    }

    public ListAdapter(){

    }

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
        if(profile != null){
            return this.attributes.size();
        }
        else {
            return DummyData.title.length;
        }
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView mItemText;
        private ImageButton editBtn,deleteBtn;
        private CVProfile.BioInfo.BioAttributes attribute;

        public ListViewHolder(View itemView) {
            super(itemView);
            mItemText = itemView.findViewById(R.id.textView_profile_details);
            deleteBtn = itemView.findViewById(R.id.image_button_delete);
            editBtn = itemView.findViewById(R.id.image_button_edit);

            if(profile != null){
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /// TODO Delete record
                    }
                });


                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //// TODO edit action gone -> visible

                        if(fragment.getClass().getSimpleName().toLowerCase().contains("academic")){
                            recyclerView = fragment.getView().findViewById(R.id.recyclerViewAcademic);
                            addAcademic = fragment.getView().findViewById(R.id.linearLayout_add);

                            recyclerView.setVisibility(View.GONE);
                            addAcademic.setVisibility(View.GONE);

                            if(attribute.getClass().getSimpleName().toLowerCase().contains("edu")){
                                form = fragment.getView().findViewById(R.id.linearLayout_academic);
                                form.setVisibility(View.VISIBLE);
                            }
                            else if(attribute.getClass().getSimpleName().toLowerCase().contains("lang")){

                            }
                            else{

                            }
                        }
                        else{
                            recyclerView = fragment.getView().findViewById(R.id.recyclerViewWork);
                            form = fragment.getView().findViewById(R.id.linearLayout_work_experience);
                            addExperience = fragment.getView().findViewById(R.id.linearLayout_add);

                            recyclerView.setVisibility(View.GONE);
                            addExperience.setVisibility(View.GONE);
                            form.setVisibility(View.VISIBLE);


                        }
                    }
                });
            }

//            itemView.setOnClickListener(this);
        }

        public void bindView(int position) {
            if(profile == null) {
                mItemText.setText(DummyData.title[position]);
            }
            else{
                attribute = attributes.get(position);
                mItemText.setText(attribute.getTitle());
            }
        }


    }
}

