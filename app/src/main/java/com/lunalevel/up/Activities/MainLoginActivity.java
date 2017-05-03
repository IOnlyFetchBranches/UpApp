package com.lunalevel.up.Activities;

import android.content.ContentProvider;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;


public class MainLoginActivity extends AppCompatActivity  {



    // UI references.
    private AutoCompleteTextView emailEntry;
    private EditText passwordView;
    private ProgressBar progressBar;
    private View mLoginFormView;

    //preferences
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init global vars
        pref = getPreferences(MODE_PRIVATE);
        prefEditor=pref.edit();

        prefEditor.putBoolean("isFirstRun",true);
        prefEditor.apply();

        if(pref.getBoolean("isFirstRun",false))
        {
            //first run ops here
            System.out.println("First run tasks...");
            prefEditor.putBoolean("isFirstRun",false);//reset b
        }

        //debug bypass
        Intent debugTo=new Intent(this,HomeScreenActivity.class);
        startActivity(debugTo);




    }


}

