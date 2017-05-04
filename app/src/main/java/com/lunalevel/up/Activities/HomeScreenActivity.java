
package com.lunalevel.up.Activities;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lunalevel.up.Models.Fragments.ScreenSlidePagerAdapter;
import com.lunalevel.up.R;

public class HomeScreenActivity extends FragmentActivity {

    ViewPager pager;
    //Create an adapter to fee the pager
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //instance the new objects
        pager=(ViewPager) findViewById(R.id.ViewPagerMain);
        adapter=new ScreenSlidePagerAdapter(getSupportFragmentManager(),3);

        //bind the adapter to the pager
        pager.setAdapter(adapter);








    }
}
