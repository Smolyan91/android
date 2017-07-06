package com.example.igor.musicbox;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 05.07.17.
 */

public class BeatBox {

    private static final String TAG = "BEAT_BOX";
    private static final String SOUND_FOLDER = "sample_sounds";

    private List<Sound> sounds = new ArrayList<>();

    //для обращения к активам
    private AssetManager assetManager;

    public BeatBox(Context context) {
        assetManager = context.getAssets();
        listSounds();
    }

    //наполнение списка активами
    private  void  listSounds(){
        String [] soundsNames;
        try {
            soundsNames = assetManager.list(SOUND_FOLDER);
            Log.i(TAG, "Found " + soundsNames.length + " sounds");
        }catch (IOException e){
            Log.e(TAG, "Can't get list asserts", e.fillInStackTrace());
            return;
        }

        for(String fileName: soundsNames){
            String assertPath = SOUND_FOLDER + "/" + fileName;
            Sound sound = new Sound(assertPath);
            sounds.add(sound);
        }
    }

    public List<Sound> getSounds() {
        return sounds;
    }
}
