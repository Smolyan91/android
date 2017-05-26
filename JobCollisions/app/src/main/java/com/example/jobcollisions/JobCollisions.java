package com.example.jobcollisions;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.example.jobcollisions.controller.CrimeFragment;
import com.example.jobcollisions.controller.SingleFragmentActivity;

public class JobCollisions extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
