package com.sky.casper.skywalker_new_app.Adapter;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Pair;
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

import com.sky.casper.skywalker_new_app.Dialogs.WarningDialog;
import com.sky.casper.skywalker_new_app.DummyData.DummyData;
import com.sky.casper.skywalker_new_app.Helpers.Cache;
import com.sky.casper.skywalker_new_app.Helpers.DatabaseHelper;
import com.sky.casper.skywalker_new_app.Helpers.Settings;
import com.sky.casper.skywalker_new_app.Models.CVProfile;
import com.sky.casper.skywalker_new_app.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter {

    private CVProfile profile = null;
    private Fragment fragment;
    private DatabaseHelper db;

    private RecyclerView recyclerView;
    private List<CVProfile.BioInfo.BioAttributes> attributes;
    private LinearLayout form;
    private ConstraintLayout addAcademic;
    private LinearLayout addExperience;

    private List<Pair<String,String>> bios;
    private int deelete_bio_pos;

    /* This is for User info Tabs suck as academic work experience etc. */
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



   /* This is for uploading resume */
    public ListAdapter(String[] bs, Fragment fr, DatabaseHelper d){
        this.bios =new ArrayList<>();
        for(int i=0; i<bs.length; i++){
            if(bs[i]!=null)
                this.bios.add(new Pair("Cv"+(i+1),bs[i]));
        }
        this.fragment = fr;
        this.db = d;
    }

    /* Returns the selected resume which will be deleted*/
    public Pair<String,Integer> getDeletedBio(){
        return new Pair(this.bios.get(this.deelete_bio_pos).first,this.deelete_bio_pos);
    }


    /* Deletes the selected Resume*/
    public void deleteCv(){
        this.bios.remove(this.deelete_bio_pos);
        notifyDataSetChanged();
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
        if(profile != null){  // user info tab
            return this.attributes.size();
        }
        else { /// resume tab
            return this.bios.size();
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

            }
            else{ /// resume tab
                editBtn.setVisibility(View.GONE);
                mItemText.setLinksClickable(true);
                mItemText.setClickable(true);

            }

//            itemView.setOnClickListener(this);
        }

        public void bindView(int position) {
            if(profile == null) {  /// resume tab
                String domainFile = fragment.getResources().getString(R.string.view_cv)+Integer.toString(position+1);
                mItemText.setText(Html.fromHtml("<a href=\"" + (bios.get(position).second.startsWith("http") ? bios.get(position).second : Settings.URLS.MyServer+Settings.URLS.URL_GET_FILES+"/"+db.getUserId()+"/"+bios.get(position).first+"/"+new Cache(fragment.getActivity()).getServerToken().replaceAll("/","*")) + "\">" + domainFile + "</a>"));
                mItemText.setMovementMethod(LinkMovementMethod.getInstance());
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deelete_bio_pos = position;
                        new WarningDialog(fragment.getActivity(),fragment.getActivity().getResources().getString(R.string.delete_bio_warning),true, (WarningDialog.AsyncDialogAnswer) fragment).showDialog();

                    }
                });
            }
            else{ /// User info tab
                attribute = attributes.get(position);
                mItemText.setText(attribute.getTitle());
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

                        if(fragment.getClass().getSimpleName().toLowerCase().contains("academic")){ /// edit academic attributes
                            recyclerView = fragment.getView().findViewById(R.id.recyclerViewAcademic);
                            addAcademic = fragment.getView().findViewById(R.id.linearLayout_add);

                            recyclerView.setVisibility(View.GONE); // vanish  all the info
                            addAcademic.setVisibility(View.GONE);

                            if(attribute.getClass().getSimpleName().toLowerCase().contains("edu")){
                                form = fragment.getView().findViewById(R.id.linearLayout_academic);
                                form.setVisibility(View.VISIBLE);  // appear the form
                            }
                            else if(attribute.getClass().getSimpleName().toLowerCase().contains("lang")){
                                    /// TODO complete forms for other attributes such as language
                            }
                            else{

                            }
                        }
                        else{  /// edit work experience
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
        }



    }
}

