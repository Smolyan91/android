package com.example.jobcollisions.model;

import java.util.Date;
import java.util.UUID;

/**
 * Model activity
 * This class hosts data activity
 * @author SmolyanovIS
 */

public class Crime {

    private UUID id;
    private String mTitle;
    private Date mDate; // date crime
    private boolean isSolved; // solved crime ?

    public Crime(){
        //generate unique id
        id = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return id;
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
