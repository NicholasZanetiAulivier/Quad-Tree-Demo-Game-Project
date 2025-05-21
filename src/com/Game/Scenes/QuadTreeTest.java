package com.Game.Scenes;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Color;

import com.DataStruct.Denode;
import com.DataStruct.DoublyLinkedList;
import com.DataStruct.QuadTree;
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
    public QuadTree partition;
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
        count =0;
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

                this.partition = new QuadTree((byte)0, 0, 0, Global.realWidth, Global.realHeight);

                Denode<?> item = this.circles.getHead();

                //partition
                while(item != null){
                    partition.insert((ArtificialCircle)item.getData());
                    item = item.getNext();
                }
            
                item = circles.getHead();
                while(item != null){


                    //Try commenting this part to see how bad the collision detection is dealing with the frames
                    CollisionObject n = (CollisionObject)item.getData();
                    boolean friendsCollide = false;
                    if (Math.pow(mouseX-((ArtificialCircle)n).getCenterX() , 2) + Math.pow(mouseY-((ArtificialCircle)n).getCenterY() , 2) < Math.pow(((ArtificialCircle)n).getRad() , 2)) {
                        friendsCollide = true;
                    };
                    CollisionObject p ;
                    Denode<?> other = partition.retrieve((ArtificialCircle)n).getHead();
                    while(other != null){
                        if ((p=(CollisionObject)other.getData()) != n) if (n.checkCollision(p)) n.isColliding(p);
                        if(friendsCollide) ((ArtificialCircle)p).setColliding(true);
                        other = other.getNext();
                        count++;
                    }
                    //

                    // ((Entity)item.getData()).update(dt);
                    item = item.getNext();
                }
            }
        );
        
        Global.KEYBOARD.setKeyPressFunction(k1);
        Global.MOUSE.setMousePressFunction(m1);

        Global.currentScene = this;
    }

    @Override
    public void loadScene() throws Exception{
        this.partition = new QuadTree((byte)0, 0, 0, Global.realWidth, Global.realHeight);
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
