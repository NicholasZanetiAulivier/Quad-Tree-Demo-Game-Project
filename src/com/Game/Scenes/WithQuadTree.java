package com.Game.Scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.DataStruct.DoublyLinkedList;
import com.DataStruct.GameQuadTree;
import com.DataStruct.Denode;
import com.Game.Callbacks.DrawFunc;
import com.Game.Callbacks.KeyPressedFunc;
import com.Game.Engine.Global;
import com.Game.Objects.ArtificialCircle;
import com.Game.Objects.CollisionObject;
import com.Game.Objects.Drawable;

public class WithQuadTree extends Scene{
    public DoublyLinkedList<ArtificialCircle> circles;
    public GameQuadTree partition;
    public GameQuadTree reference;
    
    public Future<GameQuadTree> QuadTreeBuildTask;
    public Callable<GameQuadTree> QuadTreeCall = () ->{
        GameQuadTree res = new GameQuadTree(0, 0, Global.realWidth, Global.realHeight,1);
        Denode<ArtificialCircle> item = circles.getHead();
        while (item != null){
            res.insert(item.getData());
            item = item.getNext();
        };
        return res;
    };
    public ExecutorService executor = Executors.newFixedThreadPool(1);
    public int count = 0;
   
    public KeyPressedFunc k1;
    public KeyPressedFunc k2;
    public DrawFunc entityDraw = (g) ->{
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHints(Global.RH);

        g2d.setColor(new Color(0xFF0000));

        Denode<?> item = this.circles.getHead();
        while(item != null){
            ((Drawable)item.getData()).draw(g2d , Global.CANVAS);
            // g2d.drawString(item.getData().toString(), 0, 100);
            item = item.getNext();
        };
    };
    
    public DrawFunc debugDraw = (g)->{
        g.setColor(Color.CYAN);
        reference = partition;
        reference.draw(g);
        g.setColor(Color.BLACK);
        ((Graphics2D)g).drawString(count+"" , 0 , 400);
        // System.out.println(count);
    };

    @Override
    public void switchScene() throws Exception {
        if(Global.currentScene != null) Global.currentScene.unloadScene();
        this.loadScene();

        //Draw Function
        Global.GAME_ENVIRONMENT.setDrawFunction(
            (g)->{
                entityDraw.draw(g);
                count=0;
            }
        );
        
        //Update Function
        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) -> {
                try{
                    this.partition = (GameQuadTree)QuadTreeBuildTask.get();
                } catch(Exception e){
                    this.partition = new GameQuadTree(0, 0, Global.realWidth, Global.realHeight,1);
                    Denode<?> item = this.circles.getHead();
    
                    //partition
                    while(item != null){
                        partition.insert((ArtificialCircle)item.getData());
                        item = item.getNext();
                    }
                    System.out.println("null");
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

                
                Denode<?> item = this.circles.getHead();
                
                //partition
                while(item != null){
                    ((ArtificialCircle)item.getData()).update(dt);
                    item = item.getNext();
                }
                
                QuadTreeBuildTask = executor.submit(QuadTreeCall);
            }
        );

        Global.KEYBOARD.setKeyPressFunction(k1);
        Global.currentScene = this;
        QuadTreeBuildTask = executor.submit(()->{throw new Exception();});
    }

    @Override
    public void loadScene() throws Exception{
        this.partition = new GameQuadTree((byte)0, 0, 0, Global.realWidth, Global.realHeight);
        this.circles = new DoublyLinkedList<>();
        for (int i = 0 ; i < 20000  ; i++)  
            this.circles.append(
                new ArtificialCircle(
                    (float)Math.random()*(Global.realWidth-2), 
                    (float)Math.random()*(Global.realHeight-2), 
                    (float)Math.random()*1000-500, 
                    (float)Math.random()*1000-500, 1)
            );
        
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
    }

    @Override
    public void unloadScene() throws Exception{
        this.circles = null;
    }

}
