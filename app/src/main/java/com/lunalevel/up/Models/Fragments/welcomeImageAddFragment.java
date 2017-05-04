package com.lunalevel.up.Models.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.lunalevel.up.R;

public class welcomeImageAddFragment extends Fragment{
    //this needs to be able to link with the system image picker or something
    //then it needs to be able to upload it to the server under (userprofileid)/data/image
    //it then needs to grab that image from the server and set it as the preview image

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
