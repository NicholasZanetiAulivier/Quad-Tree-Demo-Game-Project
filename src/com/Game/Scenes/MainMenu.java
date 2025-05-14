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
    public static boolean s = true;
    public static DoublyLinkedList<Drawable> drawableList;
    
    private static DrawFunc draw = new DrawFunc() {
        @Override
        public void draw(Graphics g){
            Graphics2D g2d = (Graphics2D) g;

            Denode<Drawable> item = MainMenu.drawableList.getHead();

            while(item != null){
                item.getData().draw(g2d , Global.CANVAS);
                item = item.getNext();
            }
        }
    };

    private static UpdateFunc update = new UpdateFunc() {  
        @Override
        public void update(long dt){
            Global.CANVAS.setBackground(new Color(0xFFFFFF));
        }
    };

    @Override
    public void switchScene() throws Exception {
        if(Global.currentScene != null) Global.currentScene.unloadScene();
        this.loadScene();
        Global.GAME_ENVIRONMENT.setDrawFunction(draw);
        Global.GAME_ENVIRONMENT.setUpdateFunction(update);
    }

    @Override
    public void loadScene() throws Exception{
        BasicSprite.loadSprite("rsc/spriteTest.jpg");
        MainMenu.drawableList = new DoublyLinkedList<>();
        MainMenu.drawableList.append(new BasicSprite());
    }

    @Override
    public void unloadScene() throws Exception{
        MainMenu.drawableList = null;
    }

}
