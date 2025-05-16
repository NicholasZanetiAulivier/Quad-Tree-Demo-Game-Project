package com.Game.Engine;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineEvent.Type;

/*
 * Should be able to create an audio player,
 * Other classes can use this player any way they want
 */
public class Sound{
    private Clip clip;
    

    public Sound(String path) {
        try{
            this. clip = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream((new File(path)).toURI().toURL());
            clip.open(stream);
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent l){
                    Type t = l.getType();
                    if(t == LineEvent.Type.STOP){
                        clip.close();
                    }
                }
            });
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void play(){
        this.clip.setFramePosition(0);
        this.clip.loop(0);
        this.clip.start();
    }
}
