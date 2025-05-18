package com.Game.Scenes;

import java.awt.Graphics2D;

import com.DataStruct.DoublyLinkedList;
import com.DataStruct.Denode;
import com.Game.Audio.Sound;
import com.Game.Audio.SoundEffects;
import com.Game.Callbacks.DrawFunc;
import com.Game.Engine.Global;
import com.Game.Objects.BasicObject;
import com.Game.Objects.Drawable;
import com.Game.Objects.Entity;

public class MainMenu extends Scene{
    public boolean s = true;
    public DoublyLinkedList<BasicObject> objectList;
    public String SPath;

    //Temp
    public Sound sfx ;

    /*
     * Describe assets used so that it is clear which ones should be initialized in loadScene
     * Using:
     * BasicSprite
     */

    @Override
    public void switchScene() throws Exception {
        if(Global.currentScene != null) Global.currentScene.unloadScene();
        this.loadScene();

        //Draw Function
        Global.GAME_ENVIRONMENT.setDrawFunction(
            (g) ->{
                Graphics2D g2d = (Graphics2D) g;

                Denode<?> item = this.objectList.getHead();

                while(item != null){
                    ((Drawable)item.getData()).draw(g2d , Global.CANVAS);
                    item = item.getNext();
                }
            }
        );
        
        //Update Function
        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) -> {
                Denode<?> item = this.objectList.getHead();
                if(Global.MOUSE.left_down) this.sfx.play();
            
                while(item != null){
                    ((Entity)item.getData()).update(dt);
                    item = item.getNext();
                }
            }
        );
        Global.currentScene = this;
    }

    @Override
    public void loadScene() throws Exception{
        BasicObject.loadSprite();
        this.objectList = new DoublyLinkedList<>();
        this.objectList.append(new BasicObject());

        //Temp
        sfx = new SoundEffects("sfx/Destroyed.wav", 8);
    }

    @Override
    public void unloadScene() throws Exception{
        BasicObject.freeSprite();
        this.objectList = null;
    }

}
