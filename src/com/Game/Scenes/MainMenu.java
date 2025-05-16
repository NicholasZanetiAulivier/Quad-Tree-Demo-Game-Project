package com.Game.Scenes;

import java.awt.Graphics;
import java.awt.Graphics2D;

import com.DataStruct.DoublyLinkedList;
import com.DataStruct.Denode;
import com.Game.Callbacks.DrawFunc;
import com.Game.Callbacks.UpdateFunc;
import com.Game.Engine.Global;
import com.Game.Objects.BasicObject;
import com.Game.Objects.Drawable;
import com.Game.Objects.Entity;

public class MainMenu extends Scene{
    public boolean s = true;
    public DoublyLinkedList<BasicObject> objectList;
    
    /*
     * Describe assets used so that it is clear which ones should be initialized in loadScene
     * Using:
     * BasicSprite
     */
    
    private static final DrawFunc draw = new DrawFunc() {
        @Override
        public void draw(Graphics g , Scene currentScene){
            Graphics2D g2d = (Graphics2D) g;

            Denode<?> item = ((MainMenu)currentScene).objectList.getHead();

            while(item != null){
                ((Drawable)item.getData()).draw(g2d , Global.CANVAS);
                item = item.getNext();
            }
        }
    };

    private static final UpdateFunc update = new UpdateFunc() {  
        @Override
        public void update(double dt , Scene currentScene){
            Denode<?> item = ((MainMenu)currentScene).objectList.getHead();
            
            while(item != null){
                ((Entity)item.getData()).update(dt);
                item = item.getNext();
            }
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
        BasicObject.loadSprite("rsc/img/spriteTest.jpg");
        this.objectList = new DoublyLinkedList<>();
        this.objectList.append(new BasicObject());
    }

    @Override
    public void unloadScene() throws Exception{
        BasicObject.freeSprite();
        this.objectList = null;
    }

}
