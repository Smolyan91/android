package com.example.crimeintent;


import android.support.v4.app.Fragment;

import com.example.crimeintent.controller.CrimeFragment;

public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
