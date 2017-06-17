package com.example.jobcollisions.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.jobcollisions.model.Crime;

import java.util.Date;
import java.util.UUID;

/**
 * Created by igor on 11.06.17.
 */

public class CrimeCursorWrapper extends CursorWrapper{

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime(){
        String uuidString = getString(getColumnIndex(CrimeDBSchema.CrimeTable.Columns.UUID));
        String title      = getString(getColumnIndex(CrimeDBSchema.CrimeTable.Columns.TITLE));
        long date         = getLong  (getColumnIndex(CrimeDBSchema.CrimeTable.Columns.DATE));
        int isSolved      = getInt   (getColumnIndex(CrimeDBSchema.CrimeTable.Columns.SOLVED));
        String suspect    = getString(getColumnIndex(CrimeDBSchema.CrimeTable.Columns.SUSPECT));
        String phoneNumber= getString(getColumnIndex(CrimeDBSchema.CrimeTable.Columns.PHONE_NUM));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved == 1);
        crime.setSuspectName(suspect);
        crime.setPhoneNumber(phoneNumber);
        return crime;
    }
}
