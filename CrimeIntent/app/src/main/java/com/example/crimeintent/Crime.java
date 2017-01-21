package com.example.crimeintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Администратор on 17.01.2017.
 *
 */

public class Crime {

    private UUID mId;
    private String mTitle; //заголовок преступления
    private Date mDate; //дата соверш преступления
    private boolean mSolved; //раскрыто ли преступление было

    public Crime(){
        mId = UUID.randomUUID();
        mDate = new Date();
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }
}
