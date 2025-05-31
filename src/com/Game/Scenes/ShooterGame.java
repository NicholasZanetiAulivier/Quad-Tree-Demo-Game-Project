package com.Game.Scenes;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.Color;

import com.DataStruct.DoublyLinkedList;
import com.DataStruct.GameQuadTree;
import com.Game.Engine.Global;
import com.Game.Objects.CollisionObject;
import com.Game.Objects.EnemyBullet;
import com.Game.Objects.EnemyBulletAccelerating;
import com.Game.Objects.EnemyBulletBasic;
import com.Game.Objects.EnemyBulletExploding;
import com.Game.Objects.EnemyBulletSpread;
import com.Game.Objects.EnemyEntityBasic;
import com.Game.Objects.EnemyEntityHoming;
import com.Game.Objects.EnemyEntityShooterBasic;
import com.Game.Objects.EnemyEntityShooterBomb;
import com.Game.Objects.EnemyEntityShooterSpread;
import com.Game.Objects.EnemyEntityShooterStrafe;
import com.Game.Objects.PlayerBulletBasic;
import com.Game.Objects.PlayerBulletBouncing;
import com.Game.Objects.PlayerCharacter;
import com.Game.Objects.PlayerBullet;
import com.DataStruct.Denode;
import com.Game.Objects.Item;
import com.Game.Objects.Item_10;

/*
 * Needs at least 4 layers of canvas
 */

public class ShooterGame extends Scene{
    public PlayerCharacter player;
    public DoublyLinkedList<PlayerBullet> friendlyBullets;
    public DoublyLinkedList<EnemyEntityBasic> enemyShips;
    public DoublyLinkedList<EnemyBullet>[] enemyBullets;
    public DoublyLinkedList<Item> items;

    public GameQuadTree partition ;

    public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = false;

    public int points=0;

    public boolean retry = false;
    public float timeCooldown = 1f;
    public int wave = 0;
    public int phase = 0;
    public float survivedFor = 0;
    
    public void switchScene() throws Exception{
        super.switchScene();
    }

    @SuppressWarnings("all")
    public void loadScene() throws Exception{
        //TODO: Make a loadScene function

        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) ->{

                if (retry){
                    friendlyBullets = new DoublyLinkedList<>();
                    enemyShips = new DoublyLinkedList<>();
                    items = new DoublyLinkedList<>();
                    for(int i = 0 ; i < 4 ; i++)
                        enemyBullets[i] = new DoublyLinkedList<>();
                    player = new PlayerCharacter();

                    points = 0;
                    timeCooldown = 1f;
                    wave = 0;
                    phase = 0;
                    retry = false;
                    return;
                }

                Denode<PlayerBullet> friendlyBullet;
                Denode<EnemyEntityBasic> enemy;
                Denode<EnemyBullet> enemyBullet;
                Denode<Item> item;
                
                /*
                 * Collision Detection (QuadTree)
                 */

                partition = new GameQuadTree(0, 0, Global.realWidth, Global.realHeight,1);
                
                //Insert player bullet ke QuadTree
                friendlyBullet = friendlyBullets.getHead();
                while(friendlyBullet != null){
                    partition.insert((CollisionObject)friendlyBullet.getData());
                    friendlyBullet = friendlyBullet.getNext();
                }
                

                //Collision Detect enemy-player bullet
                enemy = enemyShips.getHead();
                int checks = 0;
                
                /*
                 * THIS IS THE QUADTREE IMPLEMENTATION
                 */

                while(enemy != null){
                    CollisionObject e = (CollisionObject)enemy.getData();
                    Denode<CollisionObject> pBullet = partition.retrieve(e).getHead();
                    while(pBullet != null){
                        CollisionObject p = pBullet.getData();
                        e.checkCollision(p);
                        pBullet = pBullet.getNext();
                        checks++; 
                    }
                    enemy = enemy.getNext();
                }

                /*
                 * END QUADTREE IMPLEMENTATION
                 */

                /*
                * BRUTEFORCE IMPLEMENTATION
                */

                // while (enemy != null){
                //     CollisionObject e = (CollisionObject)enemy.getData();
                //     Denode<PlayerBullet> pBullet = friendlyBullets.getHead();
                //     while(pBullet != null){
                //         CollisionObject p = (CollisionObject)pBullet.getData();
                //         e.checkCollision(p);
                //         pBullet = pBullet.getNext();
                //         checks++;
                //     }
                //     enemy = enemy.getNext();
                // }
                
                /*
                * END BRUITEFORCE IMPLEMENTATION
                */

                // System.out.println(checks);


                //Collision detect player-enemyBullet
                if(!player.dead){
                    for (int i = 0 ; i < 4 ; i++){
                        enemyBullet = enemyBullets[i].getHead();
                        while(enemyBullet != null){

                            CollisionObject p = enemyBullet.getData();
                            player.checkCollision(p);
                            enemyBullet = enemyBullet.getNext();
                        }
                    }
                    //Collision detect player-enemy
                    enemy = enemyShips.getHead();
                    while(enemy != null){
                        CollisionObject p = enemy.getData();
                        player.checkCollision(p);
                        enemy = enemy.getNext();
                    }

                    //Collision detect player-item
                    item = items.getHead();
                    while(item != null){
                        CollisionObject p = item.getData();
                        player.checkCollision(p);
                        item = item.getNext();
                    }
                }

                
                 /*
                 * Delete dead objects
                 */

                friendlyBullet = friendlyBullets.getHead();
                while(friendlyBullet != null){
                    if (friendlyBullet.getData().shouldDestroy){
                        Denode<PlayerBullet> temp = friendlyBullet.getNext();
                        friendlyBullets.detachDenode(friendlyBullet);
                        friendlyBullet = temp;
                    } else {
                        friendlyBullet = friendlyBullet.getNext();
                    }
                }

                item = items.getHead();
                while(item != null){
                    if (item.getData().shouldDestroy){
                        Denode<Item> temp = item.getNext();
                        items.detachDenode(item);
                        item = temp;
                    } else {
                        item = item.getNext();
                    }
                }

                enemy = enemyShips.getHead();
                while(enemy != null){
                    if (enemy.getData().shouldDestroy){
                        Denode<EnemyEntityBasic> temp = enemy.getNext();
                        enemyShips.detachDenode(enemy);
                        enemy = temp;
                    } else {
                        enemy = enemy.getNext();
                    }
                }

                for(int i = 0 ; i < 4 ; i++){
                    enemyBullet = enemyBullets[i].getHead();
                    while(enemyBullet != null){
                        if(enemyBullet.getData().shouldDestroy){
                            Denode<EnemyBullet> temp = enemyBullet.getNext();
                            enemyBullets[i].detachDenode(enemyBullet);
                            enemyBullet = temp;
                        } else{
                            enemyBullet = enemyBullet.getNext();
                        }
                    }
                }

                /*
                 * Update game objects
                 */

                //At certain times, randomly call a new wave
                timeCooldown -= dt;
                survivedFor += dt;
                if(timeCooldown <= 0) runRandomScriptedEvent();


                //  Update Player
                player.update(dt);

                //  Update friendlyBullets
                friendlyBullet = friendlyBullets.getHead();
                while(friendlyBullet != null){
                    friendlyBullet.getData().update(dt);
                    friendlyBullet = friendlyBullet.getNext();
                }

                //  Update enemyShips
                enemy = enemyShips.getHead();
                while(enemy != null){
                    enemy.getData().update(dt);
                    enemy = enemy.getNext();
                }

                for(int i = 0 ; i < 4 ; i++){
                    enemyBullet = enemyBullets[i].getHead();
                    while(enemyBullet != null){
                        enemyBullet.getData().update(dt);
                        enemyBullet = enemyBullet.getNext();
                    }
                }

                item = items.getHead();
                while(item != null){
                    item.getData().update(dt);
                    item = item.getNext();
                }
            }
        );

        Global.GAME_ENVIRONMENT.setDrawFunction(0,
            (g) ->{
                graphicsEnhance(g);
                Denode<EnemyBullet> enemyBullet = enemyBullets[0].getHead();
                while(enemyBullet != null){
                    enemyBullet.getData().draw(g,Global.CANVAS[0]);
                    enemyBullet = enemyBullet.getNext();
                }

            }
        );

        Global.GAME_ENVIRONMENT.setDrawFunction(1,
            (g) ->{
                graphicsEnhance(g);
                Denode<EnemyBullet> enemyBullet = enemyBullets[1].getHead();
                while(enemyBullet != null){
                    enemyBullet.getData().draw(g,Global.CANVAS[1]);
                    enemyBullet = enemyBullet.getNext();
                }

            }
        );

        Global.GAME_ENVIRONMENT.setDrawFunction(2,
            (g) ->{
                graphicsEnhance(g);
                Denode<EnemyBullet> enemyBullet = enemyBullets[2].getHead();
                while(enemyBullet != null){
                    enemyBullet.getData().draw(g,Global.CANVAS[2]);
                    enemyBullet = enemyBullet.getNext();
                }

            }
        );

        Global.GAME_ENVIRONMENT.setDrawFunction(3,
            (g) ->{
                graphicsEnhance(g);
                Denode<EnemyBullet> enemyBullet = enemyBullets[3].getHead();
                while(enemyBullet != null){
                    enemyBullet.getData().draw(g,Global.CANVAS[3]);
                    enemyBullet = enemyBullet.getNext();
                }

            }
        );

        Global.GAME_ENVIRONMENT.setDrawFunction(4, 
            (g) ->{
                graphicsEnhance(g);
                Denode<PlayerBullet> friendlyBullet = friendlyBullets.getHead();
                while( friendlyBullet != null){
                    friendlyBullet.getData().draw(g , Global.CANVAS[4]);
                    friendlyBullet = friendlyBullet.getNext();
                }
            }
        );

        Global.GAME_ENVIRONMENT.setDrawFunction(5,
            (g) ->{
                graphicsEnhance(g);
                Denode<EnemyEntityBasic> enemy = enemyShips.getHead();
                while(enemy != null){
                    enemy.getData().draw(g, Global.CANVAS[5]);
                    enemy = enemy.getNext();
                }
            }
        );

        Global.GAME_ENVIRONMENT.setDrawFunction(6, 
            (g) -> {
                graphicsEnhance(g);
                player.draw(g , Global.CANVAS[6]);
            }
        );

        Global.GAME_ENVIRONMENT.setDrawFunction(7, 
            (g) ->{
                graphicsEnhance(g);
                Denode<Item> item = items.getHead();
                while(item != null){
                    item.getData().draw(g, Global.CANVAS[7]);
                    item = item.getNext();
                }
            }
        );

        Global.KEYBOARD.setKeyPressFunction(
            (k) ->{
                int n = k.getKeyCode();
                if(n == KeyEvent.VK_UP) up = true;
                if(n == KeyEvent.VK_DOWN) down = true;
                if(n == KeyEvent.VK_LEFT) left = true;
                if(n == KeyEvent.VK_RIGHT) right = true;
                if(n == KeyEvent.VK_SHIFT) player.switchSpeed();
                if(n == KeyEvent.VK_Z) player.startShooting();
                if(n == KeyEvent.VK_R) retry = true;
            }
        );

        Global.KEYBOARD.setKeyReleaseFunction(
            (k) ->{
                int n = k.getKeyCode();
                if(n == KeyEvent.VK_UP) up = false;
                if(n == KeyEvent.VK_DOWN) down = false;
                if(n == KeyEvent.VK_LEFT) left = false;
                if(n == KeyEvent.VK_RIGHT) right = false;
                if(n == KeyEvent.VK_SHIFT) player.switchSpeed();
                if(n == KeyEvent.VK_Z) player.stopShooting();
            }
        );


        //Load Classes
        PlayerCharacter.loadSprite();
        PlayerBulletBasic.loadSprite();
        PlayerBulletBouncing.loadSprite();
        EnemyEntityBasic.loadSprite();
        EnemyEntityHoming.loadSprite();
        EnemyEntityShooterBasic.loadSprite();
        EnemyEntityShooterSpread.loadSprite();
        EnemyEntityShooterStrafe.loadSprite();
        EnemyEntityShooterBomb.loadSprite();
        EnemyBulletBasic.loadSprite();
        EnemyBulletSpread.loadSprite();
        EnemyBulletAccelerating.loadSprite();
        EnemyBulletExploding.loadSprite();
        Item_10.loadSprite();

        //Load objects
        player = new PlayerCharacter();
        friendlyBullets = new DoublyLinkedList<>();
        enemyShips = new DoublyLinkedList<>();
        enemyBullets = (DoublyLinkedList<EnemyBullet>[])(new DoublyLinkedList[4]);
        for(int i = 0 ; i < 4 ; i++){
            enemyBullets[i] = new DoublyLinkedList<>();
        }
        items = new DoublyLinkedList<>();
    }

    public static void graphicsEnhance(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHints(Global.RH);
        g2d.setColor(new Color(0xFF0000));
    }

    public void unloadScene() throws Exception{
        //TODO: Make an unloadScene function

        //Unload Objects
        player = null;
        friendlyBullets = null;
        enemyShips = null;
        enemyBullets = null;
        items = null;

        //Unload classes
        PlayerCharacter.unload();
        PlayerBulletBasic.unload();
        PlayerBulletBouncing.unload();
        EnemyEntityBasic.unload();
        EnemyEntityHoming.unload();
        EnemyEntityShooterBasic.unload();
        EnemyEntityShooterStrafe.unload();
        EnemyEntityShooterBomb.unload();
        EnemyBulletBasic.unload();
        EnemyBulletSpread.unload();
        EnemyEntityShooterSpread.unload();
        EnemyBulletAccelerating.unload();
        EnemyBulletExploding.unload();
        Item_10.unload();
    }

    public void resetListeners(){
        up = down = left = right = false;
    }

    public void spawn(int id , float xLoc, float yLoc , float xDir , float yDir , float xAccel , float yAccel){
        switch(id){
            case CollisionObject.ENEMY_BASIC : {
                enemyShips.append(new EnemyEntityBasic(xLoc , yLoc , xDir , yDir , xAccel , yAccel));
                break;
            }
            case CollisionObject.ENEMY_HOMING : {
                enemyShips.append(new EnemyEntityHoming(xLoc , yLoc));
                break;
            }
            case CollisionObject.ENEMY_SHOOTER_BASIC: {
                enemyShips.append(new EnemyEntityShooterBasic(xLoc , yLoc));
                break;
            }
            case CollisionObject.ENEMY_SHOOTER_SPREAD :{
                enemyShips.append(new EnemyEntityShooterSpread(xLoc , yLoc));
                break;
            }
            case CollisionObject.ENEMY_SHOOTER_STRAFE : {
                enemyShips.append(new EnemyEntityShooterStrafe(xLoc, yLoc, xDir, yDir));
                break;
            }
            case CollisionObject.ENEMY_SHOOTER_BOMB : {
                enemyShips.append(new EnemyEntityShooterBomb(xLoc , yLoc));
                break;
            }
        }
    }

    //Implicit wave and phase variables
    public void runRandomScriptedEvent(){
        //Just hardcode all the events
        switch(wave){

            //Wave 1
            case 0: {
                switch(phase++) {
                    case 0: {
                        timeCooldown = 1f;
                        float spacing = Global.realWidth/5;
                        for (int i = 0 ; i < 5 ; i++){
                            spawn(CollisionObject.ENEMY_BASIC , spacing*i,-64 , (float)Math.random()*100-50 , 200, (float)Math.random()*1000-500 , (float)Math.random()*200-250);
                        }
                        break;
                    }

                    case 1 : case 2: case 3:{
                        timeCooldown = .2f;
                        for (int i = 0 ; i < 6 ; i++){
                            spawn(CollisionObject.ENEMY_BASIC , (float)Math.random()*(Global.realWidth-100)+50,-64 , (float)Math.random()*200-100 , 900, (float)Math.random()*200-100 , -500);
                        }
                        if(points >= 500 || survivedFor > 60){
                            wave++;
                            phase = 0;
                            survivedFor = 0;
                            timeCooldown = 3f;
                            System.out.println("Wave 2");
                            return;
                        }
                        phase = phase % 4;
                        break;
                    }
                }
                break;
            }

            //Wave 2
            case 1:{
            }
        }
    }

}
