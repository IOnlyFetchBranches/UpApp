package com.lunalevel.up.Models;


import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

public class Post extends LinearLayout implements Serializable{
    Profile belongsTo;
    String group,text;

    protected Post(Context context,Profile user, String group, String text){
        super(context);

        //Create post header here
        TextView postHeader=new TextView(this.getContext());

        //styling
        postHeader.setAllCaps(true);
        postHeader.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        //set text
        postHeader.setText(text);



    }


}
