package com.Game.Scenes;

import java.awt.Graphics2D;

import com.DataStruct.DoublyLinkedList;
import com.DataStruct.Denode;
import com.Game.Audio.AudioManager;
import com.Game.Callbacks.DrawFunc;
import com.Game.Callbacks.UpdateFunc;
import com.Game.Engine.Global;
import com.Game.Objects.BasicObject;
import com.Game.Objects.Drawable;
import com.Game.Objects.Entity;

public class MainMenu extends Scene{
    public boolean s = true;
    public DoublyLinkedList<BasicObject> objectList;
    public String SPath;

    //Temp
    public int soundID ;

    /*
     * Describe assets used so that it is clear which ones should be initialized in loadScene
     * Using:
     * BasicSprite
     */
    
    private static final DrawFunc draw = (g , currentScene) ->{
            Graphics2D g2d = (Graphics2D) g;

            Denode<?> item = ((MainMenu)currentScene).objectList.getHead();

            while(item != null){
                ((Drawable)item.getData()).draw(g2d , Global.CANVAS);
                item = item.getNext();
            }
        };

    private static final UpdateFunc update = (dt,currentScene)->{
            Denode<?> item = ((MainMenu)currentScene).objectList.getHead();

            if(Global.MOUSE.left_down) AudioManager.playSFX(((MainMenu)currentScene).soundID);
            
            while(item != null){
                ((Entity)item.getData()).update(dt);
                item = item.getNext();
            }
        };

    @Override
    public void switchScene() throws Exception {
        if(Global.currentScene != null) Global.currentScene.unloadScene();
        this.loadScene();
        Global.GAME_ENVIRONMENT.setDrawFunction(draw);
        Global.GAME_ENVIRONMENT.setUpdateFunction(update);
        Global.currentScene = this;
    }

    @Override
    public void loadScene() throws Exception{
        BasicObject.loadSprite();
        this.objectList = new DoublyLinkedList<>();
        this.objectList.append(new BasicObject());
        AudioManager.reserveSFXChannelAmount(2);

        //Temp
        soundID = AudioManager.loadSFX("sfx/Destroyed.wav", 8);
    }

    @Override
    public void unloadScene() throws Exception{
        BasicObject.freeSprite();
        this.objectList = null;
    }

}
