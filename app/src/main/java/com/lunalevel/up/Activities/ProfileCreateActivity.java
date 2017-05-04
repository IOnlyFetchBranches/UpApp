package com.lunalevel.up.Activities;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lunalevel.up.Models.Fragments.WelcomePagerAdapter;
import com.lunalevel.up.R;

public class ProfileCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle tempStore=new Bundle();
        setContentView(R.layout.activity_profile_create);
        ViewPager welcomePager=(ViewPager) findViewById(R.id.welcome_profile_view_pager);
        PagerAdapter adapter=new WelcomePagerAdapter(getSupportFragmentManager(),4,tempStore);
        welcomePager.setAdapter(adapter);
    }
}
