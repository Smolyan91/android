package com.example.crimeintent.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Администратор on 18.01.2017.
 * Централизованное хранилище объектов Crime
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    public static CrimeLab getInstanceCrimeLab(Context context) {
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setmTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);    //Для каждого второго объекта
            mCrimes.add(crime);
        }
    }

    public List<Crime> getmCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for (Crime crime : mCrimes) {
            if (crime.getmId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}
