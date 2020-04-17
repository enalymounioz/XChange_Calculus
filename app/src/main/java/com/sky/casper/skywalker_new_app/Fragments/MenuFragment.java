package com.sky.casper.skywalker_new_app.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

import com.sky.casper.skywalker_new_app.Helpers.Cache;
import com.sky.casper.skywalker_new_app.Helpers.DatabaseHelper;
import com.sky.casper.skywalker_new_app.Helpers.Settings;
import com.sky.casper.skywalker_new_app.Models.CVProfile;
import com.sky.casper.skywalker_new_app.R;
import interfaces.ICommunicationFragments;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MenuFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View vista;
    private Activity activity;
    private RelativeLayout layoutBackground;
    private GridLayout gridMenu;
    private CardView cardSettings,cardChat,cardSupport,cardShare,cardRate,cardInfo;
    private ICommunicationFragments interfaceCommunicationFragments;
    private TextView textNickName,textWelcome;
    private ImageView imageAvatar;
    private DatabaseHelper db;
    private Cache cache;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_menu, container, false);
        layoutBackground=vista.findViewById(R.id.idLayoutBackground);
        gridMenu=vista.findViewById(R.id.idGrid);
        cardSettings=vista.findViewById(R.id.cardSettings);
        cardChat=vista.findViewById(R.id.cardChat);
        cardSupport=vista.findViewById(R.id.cardSupport);
        cardShare=vista.findViewById(R.id.cardShare);
        cardRate=vista.findViewById(R.id.cardRate);
        cardInfo=vista.findViewById(R.id.cardInfo);
        textWelcome=vista.findViewById(R.id.textWelcome);
        textNickName=vista.findViewById(R.id.textNickName);
        imageAvatar=vista.findViewById(R.id.avatarImage);
        imageAvatar.setImageResource(R.drawable.app_logo);

        db = new DatabaseHelper(getActivity());
        cache = new Cache(getActivity());
        if(db.getUserId()!=null){
            CVProfile profile = cache.getCVProfile();
            if(profile == null){
                Settings.getCandidateDetails();
                profile = cache.getCVProfile();
            }
            textNickName.setText(profile.getName()); /// show user name
        }

        eventsMenu();

        return vista;
    }

    private void eventsMenu() {

        cardSettings.setOnClickListener(view -> interfaceCommunicationFragments.settingsButton());

        cardChat.setOnClickListener(view -> interfaceCommunicationFragments.chatButton());

        cardSupport.setOnClickListener(view -> interfaceCommunicationFragments.supportButton());

        cardShare.setOnClickListener(view -> interfaceCommunicationFragments.shareButton());

        cardRate.setOnClickListener(view -> interfaceCommunicationFragments.rateButton());

        cardInfo.setOnClickListener(view -> interfaceCommunicationFragments.infoButton());

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.activity=(Activity) context;
            interfaceCommunicationFragments= (ICommunicationFragments) this.activity;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //The fragment is visible, so we call the values ​​of the preferences
    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}