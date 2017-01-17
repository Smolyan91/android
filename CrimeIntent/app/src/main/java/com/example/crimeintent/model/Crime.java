package com.example.crimeintent.model;

import java.util.UUID;

/**
 * Created by Администратор on 17.01.2017.
 */

public class Crime {

    private UUID mId;
    private String mTitle;
    public Crime(){
        mId = UUID.randomUUID();

    }

    public UUID getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
