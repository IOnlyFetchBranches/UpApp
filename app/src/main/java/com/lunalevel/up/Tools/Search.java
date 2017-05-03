package com.lunalevel.up.Tools;


import com.lunalevel.up.Models.Profile;

import java.math.BigInteger;
import java.util.List;

public class Search {

    public static boolean isProfileInList(Profile profile,List<Profile> idSortedProfileList){
        if(binarySearch(profile.getId(),idSortedProfileList,0,idSortedProfileList.size()) ==-1)
            return false;

        else
            return true;
    }


    public static int binarySearch(int val,List<Profile> sortedList,int lo,int hi){

        if(hi<lo){
            return -1;
        } //not found

        int mid=(lo+hi)/2;

        if(val > sortedList.get(mid).getId())
            return binarySearch(val,sortedList,mid+1,sortedList.size());
        else if(val < sortedList.get(mid).getId()){
            return binarySearch(val,sortedList,0,mid-1);
        }
        else {
            return mid;
        }

    }

}
