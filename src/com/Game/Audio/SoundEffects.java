package com.Game.Audio;

import com.DataStruct.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Clip;

public class SoundEffects extends ArrayList<Clip> implements Sound{
    private int currentClip = 0;
    private int channels ;

    //To load a new SFX
    public SoundEffects(String relativePath , int channels) throws Exception{
        super(channels);
        this.channels = channels;
        for (int i = 0 ; i < channels ; i++){
            Clip temp = AudioSystem.getClip();
            temp.open(AudioSystem.getAudioInputStream(SoundEffects.class.getResource(relativePath)));
            FloatControl x = (FloatControl)temp.getControl(FloatControl.Type.MASTER_GAIN);
            double gain = .05; // number between 0 and 1 (loudest)
            float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            x.setValue(dB);
            this.addElement(temp);
        }
    }

    /*
     * Play SFX next in line. If next in line is still playing, reset it then play it
     */
    public void play(){
        int i = incrementClip();
        Clip curr = (Clip)arr[i];
        curr.stop();
        curr.flush();
        curr.setFramePosition(0);
        curr.start();
    }

    public void unload(){
        for(Clip t : this.getArray()){
            if(t != null){
                t.close();
            }
        }
        this.arr = null;
    }

    private int incrementClip(){
        return (++currentClip) % channels;
    }
}
