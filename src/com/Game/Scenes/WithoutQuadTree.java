package com.Game.Scenes;

import java.awt.Color;
import java.awt.Graphics2D;

import com.DataStruct.DoublyLinkedList;
import com.DataStruct.Denode;
import com.Game.Engine.Global;
import com.Game.Objects.ArtificialCircle;
import com.Game.Objects.Drawable;
import com.Game.Objects.Entity;

public class WithoutQuadTree extends Scene{
    public DoublyLinkedList<ArtificialCircle> circles;
   
    @Override
    public void switchScene() throws Exception {
        if(Global.currentScene != null) Global.currentScene.unloadScene();
        this.loadScene();

        //Draw Function
        Global.GAME_ENVIRONMENT.setDrawFunction(
            (g) ->{
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHints(Global.RH);

                g2d.setColor(new Color(0xFF0000));

                Denode<?> item = this.circles.getHead();
                while(item != null){
                    ((Drawable)item.getData()).draw(g2d , Global.CANVAS);
                    // g2d.drawString(item.getData().toString(), 0, 100);
                    item = item.getNext();
                }
                g2d.drawString(Global.MOUSE.x + ","+Global.MOUSE.y , 0 , 400);
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
        for (int i = 0 ; i < 1000  ; i++)
            this.circles.append(
                new ArtificialCircle(
                    (float)Math.random()*(Global.realWidth-60), 
                    (float)Math.random()*(Global.realHeight-60), 
                    (float)Math.random()*500, 
                    (float)Math.random()*500, 5)
            );
    }

    @Override
    public void unloadScene() throws Exception{
        this.circles = null;
    }

}
