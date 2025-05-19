package com.Game.Scenes;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;

import com.DataStruct.DoublyLinkedList;
import com.DataStruct.Denode;
import com.Game.Engine.Global;
import com.Game.Objects.ArtificialCircle;
import com.Game.Objects.Drawable;
import com.Game.Objects.Entity;

public class MainMenu extends Scene{
    public DoublyLinkedList<ArtificialCircle> circles;
   
    @Override
    public void switchScene() throws Exception {
        if(Global.currentScene != null) Global.currentScene.unloadScene();
        this.loadScene();

        //Draw Function
        Global.GAME_ENVIRONMENT.setDrawFunction(
            (g) ->{
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Denode<?> item = this.circles.getHead();

                while(item != null){
                    ((Drawable)item.getData()).draw(g2d , Global.CANVAS);
                    item = item.getNext();
                }
            }
        );
        
        //Update Function
        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) -> {
                Denode<?> item = this.circles.getHead();
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
        this.circles = new DoublyLinkedList<>();
        this.circles.append(new ArtificialCircle(0, 0, 0, 0, 100));

    }

    @Override
    public void unloadScene() throws Exception{
        this.circles = null;
    }

}
