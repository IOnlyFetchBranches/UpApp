package com.lunalevel.up.Models;


import android.annotation.TargetApi;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

public class Page extends LinearLayout{

    public Page(Context context, List<Post> posts, int size){
        super(context);

        this.setVisibility(VISIBLE);



    }


}
