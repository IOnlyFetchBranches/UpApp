package com.lunalevel.up.Models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;


public class Profile implements Serializable {
    private String fName,lName,state, address, bio,uName;
    private boolean isActive;

    protected Profile(@NonNull String name, @NonNull String userName, @Nullable String bio, @NonNull String username){
        //begin init process
    }


}
