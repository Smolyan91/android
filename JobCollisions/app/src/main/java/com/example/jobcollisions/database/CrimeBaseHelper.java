package com.example.jobcollisions.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class CrimeBaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "crime_db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + CrimeDBSchema.CrimeTable.NAME +
                        "(" +
                        "_id integer primary key autoincrement, " +
                        CrimeDBSchema.CrimeTable.Columns.UUID + ", " +
                        CrimeDBSchema.CrimeTable.Columns.TITLE + ", " +
                        CrimeDBSchema.CrimeTable.Columns.DATE + ", " +
                        CrimeDBSchema.CrimeTable.Columns.SOLVED +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
