package com.example.crimeintent;

import android.support.v4.app.Fragment;

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
