package com.example.jobcollisions.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.jobcollisions.model.Crime;

import java.util.Date;
import java.util.UUID;

/**
 * Для повышения уровня абстракции при извлечении данных из БД
 * Cursor делает это на низком уровне и код по извлечению много раз дублируется
 * Данный слой доступа к данным(DAO CrimeCursorWrapper) позволяет использовать
 * дополнительно созданные методы для доступа к данным
 */

public class CrimeCursorWrapper extends CursorWrapper {

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime(){
        String uuidString = getString(getColumnIndex(CrimeDBSchema.CrimeTable.Columns.UUID));
        String title = getString(getColumnIndex(CrimeDBSchema.CrimeTable.Columns.TITLE));
        long date = getLong(getColumnIndex(CrimeDBSchema.CrimeTable.Columns.DATE));
        int isSolved = getInt(getColumnIndex(CrimeDBSchema.CrimeTable.Columns.SOLVED));
        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0   );
        return crime;
    }
}
