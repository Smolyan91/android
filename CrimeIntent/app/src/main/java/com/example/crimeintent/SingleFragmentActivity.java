package com.example.crimeintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by Администратор on 18.01.2017.
 *  Абстрактный класс для создания активности
 *  В дальшнейшем каждый новый класс активности будет наслеовать этот класс и переопределять метод createFragment()
 *  Создает менеджера фрагментов из библиотеки поддержки
 */

public  abstract class SingleFragmentActivity extends FragmentActivity {

    protected abstract Fragment createFragment();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer,fragment)
                    .commit();
        }
    }
}
