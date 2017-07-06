package com.example.igor.musicbox;

/**
 * Created by igor on 06.07.17.
 */

public class Sound {

    private String mAssertPath;
    private String nameSound;

    public Sound(String mAssertPath) {
        this.mAssertPath = mAssertPath;
        String [] componentMass = mAssertPath.split("/");
        String fileName = componentMass[componentMass.length -1];
        nameSound = fileName.replace(".wav", "");
    }

    public String getmAssertPath() {
        return mAssertPath;
    }

    public String getNameSound() {
        return nameSound;
    }
}
