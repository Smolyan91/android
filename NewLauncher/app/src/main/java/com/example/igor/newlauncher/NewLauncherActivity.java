package com.example.igor.newlauncher;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;

public class NewLauncherActivity extends SingleFragmentActivity {

    public static final String INFO = "INFO: ";
    public static final String DEBAG = "DEBUG: ";
    public static final String ERROR = "ERROR: ";


    @Override
    protected Fragment createFragment() {
        Log.i(INFO, "NewLauncherActivity createFragment");
        return NewLauncherFragment.newInstance();
    }
}
