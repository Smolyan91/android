package com.example.igor.musicbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BeatBox {

    private static final String TAG = "BEAT_BOX";
    private static final String SOUND_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private List<Sound> sounds = new ArrayList<>();
    private SoundPool soundPool;

    //для обращения к активам
    private AssetManager assetManager;

    public BeatBox(Context context) {
        assetManager = context.getAssets();
        //кол-во песен, какой аудиопоток, дискретизация (рекомендуется)
        soundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
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
            try {
                String assertPath = SOUND_FOLDER + "/" + fileName;
                Sound sound = new Sound(assertPath);
                load(sound);
                sounds.add(sound);
            }catch (IOException e){
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public List<Sound> getSounds() {
        return sounds;
    }

    //для загрузки звуков п пул
    public void load(Sound sound)
            throws  IOException{
        AssetFileDescriptor assetFileDescriptor = assetManager.openFd(sound.getmAssertPath());
        Log.i("Assert path ", sound.getmAssertPath());
        int soundId = soundPool.load(assetFileDescriptor, 1);
        Log.i("Target id :", soundId + "");
        sound.setSoundId(soundId);
    }

    public void play(Sound sound){
        Integer soundId = sound.getSoundId();
        if (soundId == null) return;
        soundPool.play(soundId,1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release (){
        soundPool.release();
    }
}
