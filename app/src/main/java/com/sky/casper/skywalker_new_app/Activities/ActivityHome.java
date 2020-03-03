package com.sky.casper.skywalker_new_app.Activities;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;
import com.sky.casper.skywalker_new_app.CurvedBottomNavigationView;
import com.sky.casper.skywalker_new_app.Fragments.FavoriteFragment;
import com.sky.casper.skywalker_new_app.Fragments.MenuFragment;
import com.sky.casper.skywalker_new_app.Fragments.SearchFragment;
import com.sky.casper.skywalker_new_app.Helpers.DatabaseHelper;
import com.sky.casper.skywalker_new_app.Helpers.Settings;
import com.sky.casper.skywalker_new_app.R;
import com.sky.casper.skywalker_new_app.Skywalker;

import interfaces.ICommunicationFragments;

public class ActivityHome extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        ICommunicationFragments,MenuFragment.OnFragmentInteractionListener {

    private DatabaseHelper db;
    private ImageButton loginButton;//Variable for the login Button
    private ImageButton accountButton;
    private CurvedBottomNavigationView mView;
    private VectorMasterView heartVector;
    private VectorMasterView heartVector1;
    private VectorMasterView heartVector2;
    private float mYVal;
    private RelativeLayout mRelativeLayoutFabButton;
    private SearchFragment searchFragment;
    private MenuFragment menuFragment;
    private FavoriteFragment favoriteFragment;
    PathModel outline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mView = findViewById(R.id.customBottomBar);
        heartVector = findViewById(R.id.fab);
        heartVector1 = findViewById(R.id.fab1);
        heartVector2 = findViewById(R.id.fab2);

        mRelativeLayoutFabButton = findViewById(R.id.relativeLayoutFabButton);
        mView.inflateMenu(R.menu.bottom_menu);
        mView.setSelectedItemId(R.id.action_search);

        searchFragment = new SearchFragment();
        menuFragment = new MenuFragment();
        favoriteFragment = new FavoriteFragment();
        setFragment(searchFragment);

        //getting bottom navigation view and attaching the listener
        //BottomNavigationView navigation = findViewById(R.id.customBottomBar);
        mView.setOnNavigationItemSelectedListener(ActivityHome.this);

        /*Make Activity full screen and hide navigation bar*/
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        /*Make Activity full screen and hide navigation bar*/

        db = new DatabaseHelper(this);

        /*Going to Login Activity*/
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(view -> openLoginActivity());
        /*Going to Login Activity*/

        /*Going to Account Activity*/
        loginButton = findViewById(R.id.account_button);
        loginButton.setOnClickListener(view -> openAccountActivity());
        /*Going to Account Activity*/
        /*Going to Login Activity*/
        loginButton = findViewById(R.id.login_button);
        if(db.getUserId() == null) {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openLoginActivity();
                }
            });
        }
        else{
            loginButton.setVisibility(View.GONE);
        }
        /*Going to Home Activity*/


    }

    /*Switch between menu items and fragments*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_menu:
                tet(6);
                // find the correct path using name
                mRelativeLayoutFabButton.setX(mView.firstCurveControlPoint1.x );
                heartVector.setVisibility(View.VISIBLE);
                heartVector1.setVisibility(View.GONE);
                heartVector2.setVisibility(View.GONE);
                selectAnimation(heartVector);
                setFragment(menuFragment);
                break;
            case R.id.action_search:
                tet(2);
                mRelativeLayoutFabButton.setX(mView.firstCurveControlPoint1.x );
                heartVector.setVisibility(View.GONE);
                heartVector1.setVisibility(View.VISIBLE);
                heartVector2.setVisibility(View.GONE);
                selectAnimation(heartVector1);
                setFragment(searchFragment);
                break;
            case R.id.action_favorite:
                tet();
                mRelativeLayoutFabButton.setX(mView.firstCurveControlPoint1.x ) ;
                heartVector.setVisibility(View.GONE);
                heartVector1.setVisibility(View.GONE);
                heartVector2.setVisibility(View.VISIBLE);
                selectAnimation(heartVector2);
                setFragment(favoriteFragment);
                break;
        }

        return true;
    }

    private void setFragment (Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_fragments,fragment);
        fragmentTransaction.commit();

    }


    private void selectAnimation(final VectorMasterView heartVector) {

        outline = heartVector.getPathModelByName("outline");
        outline.setStrokeColor(Color.parseColor("#ffffff"));
        outline.setTrimPathEnd(0.0f);
        // initialise valueAnimator and pass start and end float values
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            // set trim end value and update view
            outline.setTrimPathEnd((Float) valueAnimator1.getAnimatedValue());
            heartVector.update();
        });
        valueAnimator.start();
    }

    private void tet(int i) {

        // get width and height of navigation bar
        // Navigation bar bounds (width & height)
        // the coordinates (x,y) of the start point before curve
        mView.firstCurveStartPoint.set((mView.navigationBarWidth / i) - (mView.CURVE_CIRCLE_RADIUS * 2) - (mView.CURVE_CIRCLE_RADIUS / 3), 0);
        // the coordinates (x,y) of the end point after curve
        mView.firstCurveEndPoint.set(mView.navigationBarWidth / i, mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4));
        // same thing for the second curve
        mView.secondCurveStartPoint = mView.firstCurveEndPoint;
        mView.secondCurveEndPoint.set((mView.navigationBarWidth / i) + (mView.CURVE_CIRCLE_RADIUS * 2) + (mView.CURVE_CIRCLE_RADIUS / 3), 0);

        // the coordinates (x,y)  of the 1st control point on a cubic curve
        mView.firstCurveControlPoint1.set(mView.firstCurveStartPoint.x + mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4), mView.firstCurveStartPoint.y);
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mView.firstCurveControlPoint2.set(mView.firstCurveEndPoint.x - (mView.CURVE_CIRCLE_RADIUS * 2) + mView.CURVE_CIRCLE_RADIUS, mView.firstCurveEndPoint.y);

        mView.secondCurveControlPoint1.set(mView.secondCurveStartPoint.x + (mView.CURVE_CIRCLE_RADIUS * 2) - mView.CURVE_CIRCLE_RADIUS, mView.secondCurveStartPoint.y);
        mView.secondCurveControlPoint2.set(mView.secondCurveEndPoint.x - (mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4)), mView.secondCurveEndPoint.y);



    }

    private void tet() {

        // get width and height of navigation bar
        // Navigation bar bounds (width & height)
        // the coordinates (x,y) of the start point before curve
        mView.firstCurveStartPoint.set((mView.navigationBarWidth * 10/12) - (mView.CURVE_CIRCLE_RADIUS * 2) - (mView.CURVE_CIRCLE_RADIUS / 3), 0);
        // the coordinates (x,y) of the end point after curve
        mView.firstCurveEndPoint.set(mView.navigationBarWidth  * 10/12, mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4));
        // same thing for the second curve
        mView.secondCurveStartPoint = mView.firstCurveEndPoint;
        mView.secondCurveEndPoint.set((mView.navigationBarWidth  * 10/12) + (mView.CURVE_CIRCLE_RADIUS * 2) + (mView.CURVE_CIRCLE_RADIUS / 3), 0);

        // the coordinates (x,y)  of the 1st control point on a cubic curve
        mView.firstCurveControlPoint1.set(mView.firstCurveStartPoint.x + mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4), mView.firstCurveStartPoint.y);
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mView.firstCurveControlPoint2.set(mView.firstCurveEndPoint.x - (mView.CURVE_CIRCLE_RADIUS * 2) + mView.CURVE_CIRCLE_RADIUS, mView.firstCurveEndPoint.y);

        mView.secondCurveControlPoint1.set(mView.secondCurveStartPoint.x + (mView.CURVE_CIRCLE_RADIUS * 2) - mView.CURVE_CIRCLE_RADIUS, mView.secondCurveStartPoint.y);
        mView.secondCurveControlPoint2.set(mView.secondCurveEndPoint.x - (mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4)), mView.secondCurveEndPoint.y);
    }

    /*back button press is controlled*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(ActivityHome.this.getResources().getString(R.string.exit_message))
                    .setPositiveButton(ActivityHome.this.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            dialog.dismiss();
                        startActivity(intent);
                    })
                    .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
                    .setNegativeButton(ActivityHome.this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void showMenu() {}

    @Override
    public void settingsButton() {

        Intent intent = new Intent(this, ActivitySettings.class);
        startActivity(intent);

    }

    @Override
    public void chatButton() {
        Toast.makeText(getApplicationContext(),"Available in future updates",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void supportButton() {
        Toast.makeText(getApplicationContext(),"Contact Support from the activity",Toast.LENGTH_SHORT).show();
        // getSupportFragmentManager().beginTransaction().replace(R.id.containerFragments,rankingFragment).commit();
    }

    @Override
    public void shareButton() {
        Toast.makeText(getApplicationContext(),"Share the app from the activity",Toast.LENGTH_SHORT).show();
       // Intent intent=new Intent(this, ContainerInstructionsActivity.class);
       // startActivity(intent);
    }

    @Override
    public void rateButton() {
        Toast.makeText(getApplicationContext(),"Rate in the Play Store from the activity",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void infoButton() {
        Toast.makeText(getApplicationContext(),"Information from the activity",Toast.LENGTH_SHORT).show();
        //Intent intent=new Intent(this, AboutActivity.class);
        //startActivity(intent);
    }
}