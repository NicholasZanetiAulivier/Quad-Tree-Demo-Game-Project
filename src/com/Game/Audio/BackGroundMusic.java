package com.Game.Audio;

import com.DataStruct.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;

public class BackGroundMusic extends ArrayList<Clip> implements Sound {
    private int currentMusic;
    private int amount;
    private boolean playing = true;

    public BackGroundMusic(String[] paths) throws Exception{
        amount = paths.length;
        currentMusic = paths.length-1;
        super(paths.length);
        for (String name : paths){
            Clip temp = AudioSystem.getClip();
            temp.addLineListener((e)->{
                if(e.getType() == LineEvent.Type.STOP){
                    if(playing)
                    this.play();
                }
            });
            temp.open(AudioSystem.getAudioInputStream(SoundEffects.class.getResource(name)));
            FloatControl x = (FloatControl)temp.getControl(FloatControl.Type.MASTER_GAIN);
            double gain = .5; // number between 0 and 1 (loudest)
            float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            x.setValue(dB);
            this.addElement(temp);
        }
    }

    public void play(){
        playing = true;
        int i = incrementNext();
        Clip curr = (Clip)arr[i];
        curr.stop();
        curr.flush();
        curr.setFramePosition(0);
        curr.start();
    }

    public void stop(){
        playing = false;
        Clip curr = (Clip)arr[currentMusic%amount];
        curr.stop();
        curr.flush();
    }

    public void unload(){
        for(Clip t : this.getArray()){
            if(t != null){
                t.close();
            }
        }
        this.arr = null;
    }

    private int incrementNext(){
        return (++currentMusic) % amount;
    }
}
