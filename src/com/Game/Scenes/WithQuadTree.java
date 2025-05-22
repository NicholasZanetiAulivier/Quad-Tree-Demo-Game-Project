package com.Game.Scenes;

import java.awt.Color;
import java.awt.Graphics2D;

import com.DataStruct.DoublyLinkedList;
import com.DataStruct.GameQuadTree;
import com.DataStruct.Denode;
import com.Game.Engine.Global;
import com.Game.Objects.ArtificialCircle;
import com.Game.Objects.CollisionObject;
import com.Game.Objects.Drawable;
import com.Game.Objects.Entity;

public class WithQuadTree extends Scene{
    public DoublyLinkedList<ArtificialCircle> circles;
    public GameQuadTree partition;
    public int count = 0;
   
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

                g.setColor(Color.GRAY);
                partition.draw(g);
                g.setColor(Color.BLACK);
                g2d.drawString(count+"" , 0 , 400);
                count =0;
            }
        );
        
        //Update Function
        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) -> {
                // this.partition = new GameQuadTree((byte)0, 0, 0, Global.realWidth, Global.realHeight);

                // Denode<?> item = this.circles.getHead();

                // //partition
                // while(item != null){
                //     partition.insert((ArtificialCircle)item.getData());
                //     item = item.getNext();
                // }
            
                // item = circles.getHead();
                // while(item != null){

                //     //Try commenting this part to see how bad the collision detection is dealing with the frames
                //     CollisionObject n = (CollisionObject)item.getData();
                //     CollisionObject p ;
                //     Denode<?> other = partition.retrieve((ArtificialCircle)n).getHead();
                //     while(other != null){
                //         if ((p=(CollisionObject)other.getData()) != n) if (n.checkCollision(p)) n.isColliding(p);
                //         other = other.getNext();
                //         count++;
                //     }
                //     //

                //     ((Entity)item.getData()).update(dt);
                //     item = item.getNext();
                // }
            }
        );
        Global.currentScene = this;
    }

    @Override
    public void loadScene() throws Exception{
        this.partition = new GameQuadTree((byte)0, 0, 0, Global.realWidth, Global.realHeight);
        this.circles = new DoublyLinkedList<>();
        for (int i = 0 ; i < 10000  ; i++)  
            this.circles.append(
                new ArtificialCircle(
                    (float)Math.random()*(Global.realWidth-60), 
                    (float)Math.random()*(Global.realHeight-60), 
                    (float)Math.random()*100-50, 
                    (float)Math.random()*100-50, 2)
            );
    }

    @Override
    public void unloadScene() throws Exception{
        this.circles = null;
    }

}
