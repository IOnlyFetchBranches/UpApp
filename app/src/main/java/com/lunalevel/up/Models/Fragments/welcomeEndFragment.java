package com.lunalevel.up.Models.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lunalevel.up.R;

public class welcomeEndFragment extends Fragment{
//this needs to gather consent checkbox_age_consent, as well as data, package it and upload to user profile dir as complete-profile.data and delete profile.dat
    // then it needs to activate an intent to transfer into the home screen
    //possible consider using a bundle.putBoolean to indicate to teh next stage that its a new user
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(R.layout.activity_welcome_image_add_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);
    }
}
