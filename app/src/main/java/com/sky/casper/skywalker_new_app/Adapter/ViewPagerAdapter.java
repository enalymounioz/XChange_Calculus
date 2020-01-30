package com.sky.casper.skywalker_new_app.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List <String> fragmentListTitle = new ArrayList<>();


    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentListTitle.size();
    }

    public CharSequence getPageTitle(int i){
        return fragmentListTitle.get(i);
    }

    public void AddFragment (Fragment fragment, String Title){
        fragmentList.add(fragment);
        fragmentListTitle.add(Title);
    }
}

