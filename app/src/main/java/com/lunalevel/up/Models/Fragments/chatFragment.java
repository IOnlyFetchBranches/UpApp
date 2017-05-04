package com.lunalevel.up.Models.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lunalevel.up.R;


public class chatFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return (ViewGroup) inflater.inflate(R.layout.fragment_messaging,container,false);
    }


    public chatFragment(){
        super();
    }
}
