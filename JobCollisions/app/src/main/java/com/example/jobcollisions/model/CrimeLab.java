package com.example.jobcollisions.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.jobcollisions.database.CrimeBaseHelper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by igor on 26.05.17.
 * Singleton
 * Контейнер для объектов Crime
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    private CrimeLab(Context context){
        context = context.getApplicationContext();
        sqLiteDatabase = new CrimeBaseHelper(context).getWritableDatabase();
    }

    public static CrimeLab getCrimeLab(Context context) {
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public void addCrime(Crime newCrime){
    }

    public void removeCrime(UUID id){
    }

    public List<Crime> getCrimeList() {
        return new LinkedList<>();
    }

    public Crime getCrime(UUID id){
        return null;
    }
}
