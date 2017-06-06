package com.example.jobcollisions.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jobcollisions.database.CrimeBaseHelper;
import com.example.jobcollisions.database.CrimeDBSchema.CrimeTable;

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

    /***
     * Выставка данных в базу
     * @param newCrime
     */
    public void addCrime(Crime newCrime){
        ContentValues contentValues = getContentValues(newCrime);
        /***
         * Arguments:
         * CrimeTable.NAME - имя таблицы, в которую вставляем
         * null (nullColumnHack)- позволяет при значении отличном от null не упасть
           базе при не переданных значениях contentValues
         * contentValue - данные
         */
        sqLiteDatabase.insert(CrimeTable.NAME, null, contentValues);
    }

    /***
     * Используем заполнитель '?' для предотвращения sql injection,
     * если бы просто передали строку uuidCrime в условие WHERE
     * Получилось условие: " =?", new String[]{uuidCrime}
     * @param crime
     */
    public void updateCrime(Crime crime){
        String uuidCrime = crime.getId().toString();
        ContentValues contentValues = getContentValues(crime);
        sqLiteDatabase.update(CrimeTable.NAME,contentValues,
                CrimeTable.Columns.UUID + " =?", new String[]{uuidCrime});
    }

    /***
     * Запрос данных из БД
     * @param whereCls
     * @param whereArgs
     * @return
     */
    private Cursor queryDataCrime(String whereCls, String[] whereArgs){
        Cursor cursor = sqLiteDatabase.query(
                CrimeTable.NAME,
                null, //Columns = null выбирает все столбцы
                whereCls,
                whereArgs,
                null, //groupBy
                null, //having
                null  //orderBy
        );
        return cursor;
    }

    public void removeCrime(UUID id){
    }

    public List<Crime> getCrimeList() {
        return new LinkedList<>();
    }

    public Crime getCrime(UUID id){
        return null;
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrimeTable.Columns.UUID, crime.getId().toString());
        contentValues.put(CrimeTable.Columns.TITLE, crime.getTitle());
        contentValues.put(CrimeTable.Columns.DATE, crime.getDate().getTime());
        contentValues.put(CrimeTable.Columns.TITLE, crime.isSolved() ? 1:0);

        return contentValues;
    }
}
