package com.Game.Scenes;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Color;

import com.DataStruct.Denode;
import com.DataStruct.DoublyLinkedList;
import com.DataStruct.GameQuadTree;
import com.Game.Callbacks.DrawFunc;
import com.Game.Callbacks.KeyPressedFunc;
import com.Game.Callbacks.MousePressedFunc;
import com.Game.Engine.Global;
import com.Game.Objects.ArtificialCircle;
import com.Game.Objects.CollisionObject;
import com.Game.Objects.Drawable;
import com.Game.Objects.Entity;

public class QuadTreeTest extends Scene{
    public DoublyLinkedList<ArtificialCircle> circles;
    public GameQuadTree partition;
    public int count = 0;
    public ArtificialCircle currentCircle = null;

    
    public KeyPressedFunc k1 = null;
    public KeyPressedFunc k2 = null;
    public MousePressedFunc m1 = null;
            
    public DrawFunc entityDraw = (g) ->{
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHints(Global.RH);
        
        g2d.setColor(new Color(0xFF0000));
        
        Denode<?> item = this.circles.getHead();
        while(item != null){
            ((Drawable)item.getData()).draw(g2d , Global.CANVAS);
            // g2d.drawString(item.getData().toString(), 0, 100);
            item = item.getNext();
        }
    };
    
    public DrawFunc debugDraw = (g)->{
        g.setColor(Color.GRAY);
        partition.draw(g);
        g.setColor(Color.BLACK);
        ((Graphics2D)g).drawString(count+"" , 0 , 400);
        ((Graphics2D)g).drawString(currentCircle+"", 0, 100);
    };
   
    @Override
    public void switchScene() throws Exception {
        if(Global.currentScene != null) Global.currentScene.unloadScene();
        this.loadScene();

        //Draw Function
        Global.GAME_ENVIRONMENT.setDrawFunction(
            (g) ->{
                this.entityDraw.draw(g);
                count = 0;
            }
        );
        
        //Update Function
        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) -> {
                int mouseX = Global.MOUSE.x;
                int mouseY = Global.MOUSE.y;

                currentCircle.setX(mouseX-currentCircle.getRad());
                currentCircle.setY(mouseY-currentCircle.getRad());

                this.partition = new GameQuadTree(0, 0, Global.realWidth, Global.realHeight , 0);

                Denode<?> item = this.circles.getHead();

                //partition
                while(item != null){
                    partition.insert((ArtificialCircle)item.getData());
                    item = item.getNext();
                }
                item = circles.getHead();
                while(item != null){
                    if (Math.pow(mouseX-((ArtificialCircle)item.getData()).getCenterX() , 2) + Math.pow(mouseY-((ArtificialCircle)item.getData()).getCenterY() , 2) < Math.pow(((ArtificialCircle)item.getData()).getRad() , 2)) {
                        friendsCollide = true;
                    };
                    item = item.getNext();
                }

                // Denode<ArtificialCircle> pt = circles.getHead();
                // while(pt != null){
                //     ArtificialCircle currentCircle = pt.getData();
                //     Object[] others = partition.getCollisionArray(currentCircle);
                //     for (Object o : others){
                //         CollisionObject other = (CollisionObject) o;
                //         if (currentCircle != other)
                //             if (currentCircle.checkCollision(other))
                //                 currentCircle.isColliding(other);
                //         // if(friendsCollide) ((ArtificialCircle)other).setColliding(true);
                //         count++;
                //     }
                //     pt = pt.getNext();
                // }

                DoublyLinkedList<DoublyLinkedList<CollisionObject>> results = partition.retrieveAllCollisions();
                Denode<DoublyLinkedList<CollisionObject>> currentListNode = results.getHead();
                while(currentListNode != null){
                    Denode<CollisionObject> currentObject = currentListNode.getData().getHead();
                    while(currentObject != null){
                        CollisionObject p , n;
                        Denode<CollisionObject> other = currentObject.getNext();
                        while(other != null){
                            if ((p=other.getData()) != (n = currentObject.getData()))
                                if (n.checkCollision(p))
                                    n.isColliding(p);
                            // if(friendsCollide) ((ArtificialCircle)p).setColliding(true);
                            other = other.getNext();
                            count++;
                        }
                        currentObject = currentObject.getNext();
                    }
                    currentListNode = currentListNode.getNext();
                }
            }
        );
        
        Global.KEYBOARD.setKeyPressFunction(k1);
        Global.MOUSE.setMousePressFunction(m1);

        Global.currentScene = this;
    }

    @Override
    public void loadScene() throws Exception{
        this.circles = new DoublyLinkedList<>();
        this.currentCircle = new ArtificialCircle(0, 0, 0, 0, 6);
        this.circles.append(currentCircle);
        k1 = (k)->{
            if(k.getKeyCode() == KeyEvent.VK_F3){
                Global.GAME_ENVIRONMENT.setDrawFunction(
                    (g) ->{
                        this.entityDraw.draw(g);
                        this.debugDraw.draw(g);
                        count = 0;
                    }
                );
                Global.KEYBOARD.setKeyPressFunction(k2);
            };
        };
        
        k2 = (k)->{
            if(k.getKeyCode() == KeyEvent.VK_F3){
                Global.GAME_ENVIRONMENT.setDrawFunction(
                    (g) ->{
                        this.entityDraw.draw(g);
                        count = 0;
                    }
                );
                Global.KEYBOARD.setKeyPressFunction(k1);
            };
        };

        m1 = (m)->{
            this.currentCircle = new ArtificialCircle(0,0, 0, 0, 6);
            this.circles.append(currentCircle);
        };


    }

    @Override
    public void unloadScene() throws Exception{
        this.circles = null;
    }

}
