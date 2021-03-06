package com.example.jobcollisions.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CrimeBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + CrimeDBSchema.CrimeTable.NAME +
        "( " + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        CrimeDBSchema.CrimeTable.Columns.UUID + ", " +
        CrimeDBSchema.CrimeTable.Columns.TITLE + ", " +
        CrimeDBSchema.CrimeTable.Columns.DATE + ", " +
        CrimeDBSchema.CrimeTable.Columns.SOLVED + ", " +
        CrimeDBSchema.CrimeTable.Columns.SUSPECT + ", " +
        CrimeDBSchema.CrimeTable.Columns.PHONE_NUM + " )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
