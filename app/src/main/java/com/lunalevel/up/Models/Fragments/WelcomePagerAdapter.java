package com.lunalevel.up.Models.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;



public class WelcomePagerAdapter extends FragmentStatePagerAdapter {
    private int NUM_OF_PAGES;

    public WelcomePagerAdapter(FragmentManager fm, int pageTotal, Bundle dataStore){
        super(fm);

        NUM_OF_PAGES=pageTotal;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return new welcomeFragment();
            case 1: return new welcomeInfoAddFragment();
            case 2: return new welcomeImageAddFragment();
            case 3: return new welcomeEndFragment();

            default: return null;

        }
    }


    @Override
    public int getCount() {
        return NUM_OF_PAGES;
    }
}
