package com.lunalevel.up.Models.Fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lunalevel.up.R;

import java.util.zip.Inflater;

public class welcomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(R.layout.activity_welcome_fragment,container,false);
    }
}
