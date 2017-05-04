package com.lunalevel.up.Models.Fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.lunalevel.up.R;

public class welcomeInfoAddFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(R.layout.activity_welcome_info_add_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((Spinner) view.findViewById(R.id.welcome_info_gender_chooser)).setPrompt(getString(R.string.prompt_gender));
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(welcomeInfoAddFragment.this.getContext(), R.array.gender_list,android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) view.findViewById(R.id.welcome_info_gender_chooser)).setAdapter(adapter);

        super.onViewCreated(view, savedInstanceState);
    }
}
