package com.example.igor.newlauncher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by igor on 26.07.17.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public static final String INFO = "INFO: ";

    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        Log.i(INFO, "SingleFragmentActivity onCreate");

        if (fragment == null) {
            Log.i(INFO, "SingleFragmentActivity frgment == null");
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }
}
