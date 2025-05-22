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

                // g.setColor(Color.GRAY);
                // partition.draw(g);
                g.setColor(Color.BLACK);
                g2d.drawString(count+"" , 0 , 400);
                count =0;
            }
        );
        
        //Update Function
        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) -> {
                this.partition = new GameQuadTree(0, 0, Global.realWidth, Global.realHeight,1);

                Denode<?> item = this.circles.getHead();

                //partition
                while(item != null){
                    partition.insert((ArtificialCircle)item.getData());
                    item = item.getNext();
                }
            
                DoublyLinkedList<DoublyLinkedList<CollisionObject>> results = partition.retrieveAllCollisions();
                Denode<DoublyLinkedList<CollisionObject>> currentListNode = results.getHead();
                while(currentListNode != null){
                    Denode<CollisionObject> currentObject = currentListNode.getData().getHead();
                    while(currentObject != null){
                        CollisionObject p , n;
                        Denode<CollisionObject> other = currentObject.getNext();
                        while(other != null){
                            if ((p=other.getData()) != (n = currentObject.getData()))
                                if (n.checkCollision(p)){
                                    n.isColliding(p);
                                }
                            other = other.getNext();
                            count++;
                        }
                        currentObject = currentObject.getNext();
                    }
                    currentListNode = currentListNode.getNext();
                }

                item = this.circles.getHead();

                //partition
                while(item != null){
                    ((ArtificialCircle)item.getData()).update(dt);
                    item = item.getNext();
                }

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
                    (float)Math.random()*(Global.realWidth-4), 
                    (float)Math.random()*(Global.realHeight-4), 
                    (float)Math.random()*100-50, 
                    (float)Math.random()*100-50, 2)
            );
    }

    @Override
    public void unloadScene() throws Exception{
        this.circles = null;
    }

}
