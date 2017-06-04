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
    }

    public static CrimeLab getCrimeLab(Context context) {
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public void addCrime(Crime newCrime){
        this.crimeList.add(newCrime);
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
