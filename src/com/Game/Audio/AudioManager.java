package com.Game.Audio;

public abstract class AudioManager {
    //
    private static SoundEffects[] SFXchannels;
    private static int currentSize = 0;
    private static int SFXAmount ;

    public static void reserveSFXChannelAmount(int amount){
        AudioManager.SFXchannels = new SoundEffects[amount];
        currentSize = 0;
        SFXAmount = amount;
    }


    public static int loadSFX(String relativePath , int numOfThreads) throws Exception{
        if (SFXchannels == null) throw new Exception("setTheAmount of channels first");
        if(currentSize == SFXAmount) throw new Exception("Too many Channels, please create a larger channel array");
        SFXchannels[currentSize] = new SoundEffects(relativePath, numOfThreads );
        currentSize++;
        return currentSize-1;
    }

    public static void playSFX(int channelNo){
        SFXchannels[channelNo].play();
    }
}
