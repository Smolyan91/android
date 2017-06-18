package com.example.jobcollisions.model;

import android.util.Log;

import java.util.Date;
import java.util.UUID;

/**
 * Model activity
 * This class hosts data activity
 * @author SmolyanovIS
 */

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate; // date crime
    private boolean isSolved; // solved crime ?
    private String mSuspectName;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhotoFileName(){
        Log.i("getPFileName : ", "IMG_" + getId().toString() + ".jpg");
        return "IMG_" + getId().toString() + ".jpg";
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String phoneNumber;

    public String getSuspectName() {
        return mSuspectName;
    }

    public void setSuspectName(String mSuspectName) {
        this.mSuspectName = mSuspectName;
    }

    public Crime(){
        //generate unique id
        this(UUID.randomUUID());
    }

    public Crime(UUID id){
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }
}
