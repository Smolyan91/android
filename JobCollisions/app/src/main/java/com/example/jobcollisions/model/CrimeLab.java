package com.example.jobcollisions.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.jobcollisions.database.CrimeBaseHelper;
import com.example.jobcollisions.database.CrimeCursorWrapper;
import com.example.jobcollisions.database.CrimeDBSchema;
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
    private Context mContext;
    private SQLiteDatabase sqLiteDatabase;

    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        sqLiteDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

    public static CrimeLab getCrimeLab(Context context) {
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    /***
     * Выставка данных в базу
     * @param newCrime
     */
    public void addCrime(Crime newCrime){
        ContentValues contentValues = getContentValues(newCrime);
        sqLiteDatabase.insert(CrimeDBSchema.CrimeTable.NAME, null, contentValues);
    }

    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        ContentValues contentValues = getContentValues(crime);
        sqLiteDatabase.update(CrimeDBSchema.CrimeTable.NAME,
                contentValues, CrimeDBSchema.CrimeTable.Columns.UUID + " =?",
                new String[]{uuidString});
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor = sqLiteDatabase.query(
                CrimeDBSchema.CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CrimeCursorWrapper(cursor);
    }

    public void deleteCrime(Crime crime){
        String idCrimeStringForDelete = crime.getId().toString();
        sqLiteDatabase.delete(CrimeDBSchema.CrimeTable.NAME,
                CrimeDBSchema.CrimeTable.Columns.UUID + " = ?",
                new String[]{idCrimeStringForDelete});
    }

    public List<Crime> getCrimeList() {
        List<Crime> crimes = new LinkedList<>();
        CrimeCursorWrapper cursorWrapper = queryCrimes(null, null);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                crimes.add(cursorWrapper.getCrime());
                cursorWrapper.moveToNext();
            }
        }finally {
            cursorWrapper.close();
        }
        return crimes;
    }

    /***
     * возвращает только первый элемент
     * @param id
     * @return
     */
    public Crime getCrime(UUID id){

        CrimeCursorWrapper crimeCursorWrapper = queryCrimes(
                CrimeDBSchema.CrimeTable.Columns.UUID + " =?",
                new String[]{id.toString()}
        );
        try {
            if (crimeCursorWrapper.getCount() == 0){
                return null;
            }
            crimeCursorWrapper.moveToFirst();
            return crimeCursorWrapper.getCrime();
        }finally {
            crimeCursorWrapper.close();
        }
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrimeDBSchema.CrimeTable.Columns.UUID, crime.getId().toString());
        contentValues.put(CrimeDBSchema.CrimeTable.Columns.TITLE, crime.getTitle());
        contentValues.put(CrimeDBSchema.CrimeTable.Columns.DATE, crime.getDate().getTime());
        contentValues.put(CrimeDBSchema.CrimeTable.Columns.SOLVED, crime.isSolved()?  1:0);
        contentValues.put(CrimeDBSchema.CrimeTable.Columns.SUSPECT, crime.getSuspectName());
        return contentValues;
    }
}
