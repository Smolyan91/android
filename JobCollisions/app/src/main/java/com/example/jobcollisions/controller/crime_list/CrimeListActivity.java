package com.example.jobcollisions.controller.crime_list;

import android.support.v4.app.Fragment;

import com.example.jobcollisions.controller.SingleFragmentActivity;

/**
 * Created by igor on 27.05.17.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
