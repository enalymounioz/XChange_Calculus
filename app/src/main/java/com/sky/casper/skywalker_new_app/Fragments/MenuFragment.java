package com.sky.casper.skywalker_new_app.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ImageButton buttonHelp;
    View vista;
    Activity activity;
    RelativeLayout layoutBackground;
    GridLayout gridMenu;
    CardView cardPlay,cardSettings,cardRanking,cardHelp,cardUser,cardInfo;

    ICommunicationFragments interfaceCommunicationFragments;

    TextView textNickName;
    ImageView imageAvatar;

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
        cardPlay=vista.findViewById(R.id.cardPlay);
        buttonHelp=vista.findViewById(R.id.buttonHelp);
        cardSettings=vista.findViewById(R.id.cardSettings);
        cardRanking=vista.findViewById(R.id.cardRanking);
        cardHelp=vista.findViewById(R.id.cardHelp);
        cardUser=vista.findViewById(R.id.cardUser);
        cardInfo=vista.findViewById(R.id.cardInfo);
        textNickName=vista.findViewById(R.id.textNickName);
        imageAvatar=vista.findViewById(R.id.avatarImage);



        imageAvatar.setImageResource(R.drawable.app_logo);
        eventsMenu();

        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                createSimpleDialog().show();
            }
        });

        return vista;
    }

    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Help")
                .setMessage("This application can help you look for a job. In order to personalize your search, Account or the Application" +
                        " ,you can do so from the Menu tab. ")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        return builder.create();
    }

    //Allows you to assign preferences and change the mode and color of the custom banner


    private void eventsMenu() {

        cardPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceCommunicationFragments.accountSettings();
            }
        });

        cardSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceCommunicationFragments.notificationSettings();
            }
        });

        cardRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceCommunicationFragments.contactSupport();
            }
        });

        cardHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceCommunicationFragments.shareApp();
            }
        });

        cardUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceCommunicationFragments.rateApp();
            }
        });

        cardInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceCommunicationFragments.helpInformation();
            }
        });

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