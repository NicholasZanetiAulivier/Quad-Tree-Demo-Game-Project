package com.Game.Audio;

import com.DataStruct.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class BackGroundMusic extends ArrayList<Clip> implements Sound {
    private int currentMusic;
    private int amount;

    public BackGroundMusic(String[] paths) throws Exception{
        amount = paths.length;
        currentMusic = paths.length-1;
        super(paths.length);
        for (String name : paths){
            Clip temp = AudioSystem.getClip();
            temp.addLineListener((e)->{
                if(e.getType() == LineEvent.Type.STOP){
                    this.play();
                }
            });
            temp.open(AudioSystem.getAudioInputStream(SoundEffects.class.getResource(name)));
            this.addElement(temp);
        }
    }

    public void play(){
        int i = incrementNext();
        Clip curr = (Clip)arr[i];
        curr.stop();
        curr.flush();
        curr.setFramePosition(0);
        curr.start();
    }

    private int incrementNext(){
        return (++currentMusic) % amount;
    }
}
