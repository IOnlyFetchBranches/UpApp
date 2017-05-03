package com.lunalevel.up.Models;

import android.content.Context;
import android.location.Location;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.lunalevel.up.Tools.*;

public class Profile implements Serializable, Comparable<Profile> {
    private String fName,lName,state, address, bio,uName, gender;
    private boolean isBanned,isAdmin; //also hidden behind validated call
    private Image avatar;

    private Integer id,points,postCount; //should only be changed by validated app call, (will store hash on server to validate these ops)

    private  String[] alpha={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    private int firstLetter;

    private Location tempLocation; //def need to code securely for this, make sure its deleted from the backend when not needed


    protected List<Profile> followList=new ArrayList<>();

    protected Profile(@NonNull String name, @NonNull String userName, @Nullable String bio, @NonNull String username){
        //begin init process
        this.fName=name.substring(0,name.indexOf(",")); this.lName=name.substring(name.indexOf(",",name.length())); //extract names
        index();

    }

    //private critical operations
    private void index(){
        //basic indexing, horribly optimized likely but working

        for(int i=0;i<alpha.length;i++) {
            if (fName.substring(0, 1).equalsIgnoreCase(alpha[i])) {
                firstLetter = i;
                break;
            }
        }

    }

    //0-25 scale of the alphbet
    public int getFirstLetter() {
        return firstLetter;
    }




    //public functions called freely around app
    public int compareTo(@NonNull Profile otherProfile){
        return id.compareTo(otherProfile.getId());
    }
    public void followUser(@NonNull Profile user){

        if(followList.size()>1)
            Collections.sort(followList);
        //ensure the list is sorted

        if(Search.isProfileInList(user,followList)){


        }
        else{

        }
    }



    //converters for UI
    public LinearLayout toLayout(Context context){
        LinearLayout userTouchable=new LinearLayout(context);

        //design for touchable user icons



        return userTouchable;
    }

    //getters


    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getBio() {
        return bio;
    }

    public Image getAvatar() {
        return avatar;
    }

    public List<Profile> getFollowList() {
        return followList;
    }

    public int getId() {
        return id;
    }

    public long getPostCount() {
        return postCount;
    }

    public String getGender() {
        return gender;
    }

    public String getuName() {
        return uName;
    }

    public String getState() {
        return state;
    }

    public long getPoints() {
        return points;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

}
