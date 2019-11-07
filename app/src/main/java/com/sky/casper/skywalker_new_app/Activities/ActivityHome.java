package com.sky.casper.skywalker_new_app.Activities;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
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
import com.sky.casper.skywalker_new_app.R;

import interfaces.ICommunicationFragments;

public class ActivityHome extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        ICommunicationFragments,MenuFragment.OnFragmentInteractionListener {

    private CurvedBottomNavigationView mView;
    private VectorMasterView heartVector;
    private VectorMasterView heartVector1;
    private VectorMasterView heartVector2;
    private float mYVal;
    private RelativeLayout mlinId;

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

        mlinId = findViewById(R.id.lin_id);
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


    }

    /*Switch between menu items and fragments*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_menu:
                tet(6);
                // find the correct path using name
                mlinId.setX(mView.firstCurveControlPoint1.x );
                heartVector.setVisibility(View.VISIBLE);
                heartVector1.setVisibility(View.GONE);
                heartVector2.setVisibility(View.GONE);
                selectAnimation(heartVector);
                setFragment(menuFragment);
                break;
            case R.id.action_search:
                tet(2);
                mlinId.setX(mView.firstCurveControlPoint1.x );
                heartVector.setVisibility(View.GONE);
                heartVector1.setVisibility(View.VISIBLE);
                heartVector2.setVisibility(View.GONE);
                selectAnimation(heartVector1);
                setFragment(searchFragment);
                break;
            case R.id.action_favorite:
                tet();
                mlinId.setX(mView.firstCurveControlPoint1.x ) ;
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
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // set trim end value and update view
                outline.setTrimPathEnd((Float) valueAnimator.getAnimatedValue());
                heartVector.update();
            }
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
        if (keyCode == event.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want Exit Skywalker.gr")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
    public void accountSettings() {
        Toast.makeText(getApplicationContext(),"Account Settings from the activity",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notificationSettings() {
        Toast.makeText(getApplicationContext(),"Notification Settings from the activity",Toast.LENGTH_SHORT).show();
        //Intent intent=new Intent(this, SettingsActivity.class);
        //startActivity(intent);
    }

    @Override
    public void contactSupport() {
        Toast.makeText(getApplicationContext(),"Contact Support from the activity",Toast.LENGTH_SHORT).show();
        // getSupportFragmentManager().beginTransaction().replace(R.id.containerFragments,rankingFragment).commit();
    }

    @Override
    public void shareApp() {
        Toast.makeText(getApplicationContext(),"Share the app from the activity",Toast.LENGTH_SHORT).show();
       // Intent intent=new Intent(this, ContainerInstructionsActivity.class);
       // startActivity(intent);
    }

    @Override
    public void rateApp() {
        Toast.makeText(getApplicationContext(),"Rate in the Play Store from the activity",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void helpInformation() {
        Toast.makeText(getApplicationContext(),"Information from the activity",Toast.LENGTH_SHORT).show();
        //Intent intent=new Intent(this, AboutActivity.class);
        //startActivity(intent);
    }
}