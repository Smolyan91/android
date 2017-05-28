package com.example.jobcollisions;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.example.jobcollisions.controller.CrimeFragment;
import com.example.jobcollisions.controller.SingleFragmentActivity;

import java.util.UUID;

public class JobCollisions extends SingleFragmentActivity {

    public static final String EXTRA_CRIME_ID = "com.example.jobcollisions.jobcollisions.crime_id";

    public static Intent mewIntent(Context packegeContext, UUID crimeId){
        Intent intent = new Intent(packegeContext, JobCollisions.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
