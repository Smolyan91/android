package com.example.jobcollisions.controller.crime_list;

import android.support.v4.app.Fragment;

import com.example.jobcollisions.controller.SingleFragmentActivity;


public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
