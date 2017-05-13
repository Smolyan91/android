package com.example.jobcollisions.model;

import java.util.UUID;

/**
 * Model activity
 * This class hosts data activity
 * @author SmolyanovIS
 */

public class Crime {

    private UUID id;
    private String mTitle;

    public Crime(){
        //generate unique id
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
