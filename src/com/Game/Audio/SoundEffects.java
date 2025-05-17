package com.Game.Audio;

import com.DataStruct.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class SoundEffects extends ArrayList<Clip> implements Sound{
    private int currentClip = 0;
    private int channels ;

    //To load a new SFX
    public SoundEffects(String relativePath , int channels) throws Exception{
        super(channels);
        this.channels = channels;
        for (int i = 0 ; i < channels ; i++){
            Clip temp = AudioSystem.getClip();
            int j = i;
            temp.open(AudioSystem.getAudioInputStream(SoundEffects.class.getResource(relativePath)));
            temp.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent l){
                    if(l.getType() == LineEvent.Type.START){
                        System.out.println("Start" + j);
                    }
                    if(l.getType() == LineEvent.Type.STOP){
                        System.out.println("Stopped" + j);
                    }
                }
            });
            this.addElement(temp);
        }
    }

    public void play(){
        int i = incrementClip();
        Clip curr = (Clip)arr[i];
        curr.stop();
        curr.flush();
        curr.setFramePosition(0);
        curr.start();
    }

    private int decrementClip(){
        return ((((++currentClip) % channels) + channels) % channels);
    }

    private int incrementClip(){
        return (++currentClip) % channels;
    }
}
