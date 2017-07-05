package com.example.igor.musicbox;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MusicBox extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        Log.i("INFO", "MusicBox");
        return MusicBoxFragment.newInstance();
    }
}
