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
import com.Game.Objects.EnemyBulletSpread;
import com.Game.Objects.EnemyEntityBasic;
import com.Game.Objects.EnemyEntityHoming;
import com.Game.Objects.EnemyEntityShooterBasic;
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

    public float timeCooldown = 1f;
    public int difficulty = 3;
    
    public void switchScene() throws Exception{
        super.switchScene();
    }

    @SuppressWarnings("all")
    public void loadScene() throws Exception{
        //TODO: Make a loadScene function

        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) ->{
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
                while(enemy != null){
                    CollisionObject e = (CollisionObject)enemy.getData();
                    Denode<CollisionObject> pBullet = partition.retrieve(e).getHead();
                    while(pBullet != null){
                        CollisionObject p = pBullet.getData();
                        e.checkCollision(p);
                        pBullet = pBullet.getNext(); 
                    }
                    enemy = enemy.getNext();
                }

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
                if(timeCooldown <= 0) runRandomScriptedEvent(difficulty);


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
            }
        );

        Global.KEYBOARD.setKeyReleaseFunction(
            (k) ->{
                int n = k.getKeyCode();
                if(n == KeyEvent.VK_UP) up = false;
                if(n == KeyEvent.VK_DOWN) down = false;
                if(n == KeyEvent.VK_LEFT) left = false;                  
                if(n == KeyEvent.VK_RIGHT) right = false;
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
        EnemyBulletBasic.loadSprite();
        EnemyBulletSpread.loadSprite();
        EnemyBulletAccelerating.loadSprite();
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
        EnemyBulletBasic.unload();
        EnemyBulletSpread.unload();
        EnemyEntityShooterSpread.unload();
        EnemyBulletAccelerating.unload();
        Item_10.unload();
    }

    public void resetListeners(){
        up = down = left = right = false;
    }

    public void spawn(int id , int count){
        switch(id){
            case CollisionObject.ENEMY_BASIC : {
                for (int i = 0 ; i < count ; i++)
                    enemyShips.append(new EnemyEntityBasic((float)Math.random() * 600+100, -64));
                break;
            }
            case CollisionObject.ENEMY_HOMING : {
                for (int i = 0 ; i < count ; i++)
                    enemyShips.append(new EnemyEntityHoming((float)Math.random()*600+100, -64));
                break;
            }
            case CollisionObject.ENEMY_SHOOTER_BASIC: {
                for (int i = 0 ; i < count ; i++)
                    enemyShips.append(new EnemyEntityShooterBasic((float)Math.random()*600+100, -64));
                break;
            }
            case CollisionObject.ENEMY_SHOOTER_SPREAD :{
                for(int i = 0 ; i < count ; i++)
                    enemyShips.append(new EnemyEntityShooterSpread((float)Math.random()*600+100, -64));
                break;
            }
            case CollisionObject.ENEMY_SHOOTER_STRAFE : {
                for (int i = 0 ; i < count ; i++)
                    enemyShips.append(new EnemyEntityShooterStrafe((float)Math.random()*600+100, -64 , (float)Math.random()-.5f , (float)Math.random()));
            }
        }
    }

    public void runRandomScriptedEvent(int dif){
        if(dif == 0 ){
            timeCooldown = .1f;
            int c = (int)Math.round(Math.random()*10);
            spawn(CollisionObject.ENEMY_BASIC , c);
            return;
        }
        if(dif == 1){
            timeCooldown = .2f;
            int c = (int)Math.round(Math.random() * 10);
            spawn(CollisionObject.ENEMY_HOMING , c);
        }
        if(dif == 2){
            timeCooldown = .5f;
            int c = (int)(Math.round(Math.random()*10));
            spawn(CollisionObject.ENEMY_SHOOTER_BASIC , c);
        }
        if (dif == 3){
            timeCooldown = 2f;
            spawn(CollisionObject.ENEMY_BASIC , 5);
            spawn(CollisionObject.ENEMY_HOMING , 10);
            spawn(CollisionObject.ENEMY_SHOOTER_BASIC , 5);
            spawn(CollisionObject.ENEMY_SHOOTER_SPREAD , 3);
            spawn(CollisionObject.ENEMY_SHOOTER_STRAFE , 10);
        }
        if(dif == 4){
            timeCooldown = .5f;
            spawn(CollisionObject.ENEMY_SHOOTER_SPREAD , 10);
        }
        if(dif == 5){
            timeCooldown = 1f;
            spawn(CollisionObject.ENEMY_SHOOTER_STRAFE , 10);
        }
    }

}
