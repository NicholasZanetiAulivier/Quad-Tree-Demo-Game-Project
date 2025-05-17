package com.Game.Audio;

import com.DataStruct.ArrayList;

import javax.sound.sampled.Clip;

public class SoundEffects extends ArrayList<Clip> implements Sound{
    
    public SoundEffects(String relativePath){
        super(4);
    }
}
