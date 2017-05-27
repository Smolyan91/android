package com.example.jobcollisions.model;

import android.content.Context;

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
    private List<Crime> crimeList;

    private CrimeLab(Context context){
        crimeList = new LinkedList<>();
        //TODO временная инициализация для проверки
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime №_" + i);
            crime.setSolved(i%2 == 0);
            crimeList.add(crime);
        }
    }

    public static CrimeLab getCrimeLab(Context context) {
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public List<Crime> getCrimeList() {
        return crimeList;
    }

    public Crime getCrime(UUID id){
        for (Crime crime :  crimeList){
            if (crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
}
