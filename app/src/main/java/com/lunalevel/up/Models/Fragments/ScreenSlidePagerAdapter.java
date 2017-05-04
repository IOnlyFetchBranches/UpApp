package com.lunalevel.up.Models.Fragments;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private int NUM_OF_PAGES;

    public ScreenSlidePagerAdapter(@NonNull FragmentManager manager,int pageAmount){
        super(manager);
        NUM_OF_PAGES=pageAmount;

    }

    @Override
    public int getCount() {
        return NUM_OF_PAGES;
    }

    @Override
    public Fragment getItem(int position) {
       switch(position){
           case 0: return new homeFragement();
           case 1: return new chatFragment();
           case 2: return new userpageFragment();
           default:
               return null;
       }
    }
}
