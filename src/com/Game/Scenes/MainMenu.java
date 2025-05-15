package com.Game.Scenes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import com.DataStruct.DoublyLinkedList;
import com.DataStruct.Denode;
import com.Game.Engine.DrawFunc;
import com.Game.Engine.Global;
import com.Game.Engine.UpdateFunc;
import com.Game.Objects.BasicSprite;
import com.Game.Objects.Drawable;

public class MainMenu extends Scene{
    public boolean s = true;
    public DoublyLinkedList<Drawable> drawableList;
    
    /*
     * Describe assets used so that it is clear which ones should be initialized in loadScene
     * Using:
     * BasicSprite
     */
    
    private static final DrawFunc draw = new DrawFunc() {
        @Override
        public void draw(Graphics g , Scene currentScene){
            Graphics2D g2d = (Graphics2D) g;

            Denode<Drawable> item = ((MainMenu)currentScene).drawableList.getHead();

            while(item != null){
                item.getData().draw(g2d , Global.CANVAS);
                item = item.getNext();
            }
        }
    };

    private static final UpdateFunc update = new UpdateFunc() {  
        @Override
        public void update(long dt , Scene currentScene){
            if(((MainMenu)currentScene).s) Global.CANVAS.setBackground(new Color(0x0000000));
            else Global.CANVAS.setBackground(new Color(0xFFFFFF));
            ((MainMenu)currentScene).s = !((MainMenu)currentScene).s;
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
        BasicSprite.loadSprite("rsc/spriteTest.jpg");
        this.drawableList = new DoublyLinkedList<>();
        this.drawableList.append(new BasicSprite());
    }

    @Override
    public void unloadScene() throws Exception{
        BasicSprite.freeSprite();
        this.drawableList = null;
    }

}
