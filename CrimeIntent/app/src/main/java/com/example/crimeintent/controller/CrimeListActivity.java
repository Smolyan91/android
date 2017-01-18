package com.example.crimeintent.controller;

import android.support.v4.app.Fragment;

import com.example.crimeintent.SingleFragmentActivity;

/**
 * Created by Администратор on 18.01.2017.
 * Для отображения списка на уровне контроллера создаем данную активность и новый фрагмент CrimeListFragment
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
