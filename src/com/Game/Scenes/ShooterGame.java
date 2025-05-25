package com.Game.Scenes;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Color;

import com.DataStruct.DoublyLinkedList;
import com.Game.Engine.Global;
import com.Game.Objects.EnemyEntityBasic;
import com.Game.Objects.EnemyObject;
import com.Game.Objects.PlayerBulletBasic;
import com.Game.Objects.PlayerCharacter;
import com.Game.Objects.PlayerBullet;
import com.DataStruct.Denode;

public class ShooterGame extends Scene{
    public PlayerCharacter player;
    public DoublyLinkedList<PlayerBullet> friendlyBullets;
    public DoublyLinkedList<EnemyEntityBasic> enemyShips;

    public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = false;

    public float timeCooldown = 3f;
    public int difficulty = 0;
    
    public void switchScene() throws Exception{
        super.switchScene();
    }

    public void loadScene() throws Exception{
        //TODO: Make a loadScene function

        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) ->{
                Denode<PlayerBullet> friendlyBullet;
                Denode<EnemyEntityBasic> enemy;
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
                
                /*
                 * Collision Detection (QuadTree)
                 */

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
            }
        );

        Global.GAME_ENVIRONMENT.setDrawFunction(
            (g) ->{
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHints(Global.RH);

                g2d.setColor(new Color(0xFF0000));
                player.draw(g , Global.CANVAS);

                Denode<PlayerBullet> friendlyBullet = friendlyBullets.getHead();
                while( friendlyBullet != null){
                    friendlyBullet.getData().draw(g , Global.CANVAS);
                    friendlyBullet = friendlyBullet.getNext();
                }

                Denode<EnemyEntityBasic> enemy = enemyShips.getHead();
                while(enemy != null){
                    enemy.getData().draw(g, Global.CANVAS);
                    enemy = enemy.getNext();
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
        EnemyEntityBasic.loadSprite();

        //Load objects
        player = new PlayerCharacter();
        friendlyBullets = new DoublyLinkedList<>();
        enemyShips = new DoublyLinkedList<>();
    }

    public void unloadScene() throws Exception{
        //TODO: Make an unloadScene function

        //Unload Objects
        player = null;
        friendlyBullets = null;
        enemyShips = null;

        //Unload classes
        PlayerCharacter.unload();
        PlayerBulletBasic.unload();
        EnemyEntityBasic.unload();

    }

    public void resetListeners(){
        up = down = left = right = false;
    }

    public void runRandomScriptedEvent(int dif){
        if(dif == 0 ){
            timeCooldown = .1f;
            for (int i = 0 ; i < (int)(Math.random() * 100) ; i++){
                enemyShips.append(new EnemyEntityBasic((float)Math.random() * 600+100, -64));
            }
        }
    }

}
