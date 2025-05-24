package com.Game.Scenes;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Color;

import com.DataStruct.DoublyLinkedList;
import com.Game.Engine.Global;
import com.Game.Objects.EnemyObject;
import com.Game.Objects.PlayerBulletBasic;
import com.Game.Objects.PlayerCharacter;
import com.Game.Objects.PlayerObject;
import com.DataStruct.Denode;

public class ShooterGame extends Scene{
    public PlayerCharacter player;
    public DoublyLinkedList<PlayerObject> friendlyObjects;
    public DoublyLinkedList<EnemyObject> enemyObjects;

    public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = false;
    
    public void switchScene() throws Exception{
        super.switchScene();
    }

    public void loadScene() throws Exception{
        //TODO: Make a loadScene function

        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) ->{
                Denode<PlayerObject> item;
                /*
                 * Delete dead objects
                 */

                item = friendlyObjects.getHead();
                while(item != null){
                    if (((PlayerObject)item.getData()).shouldDestroy){
                        Denode<PlayerObject> temp = item.getNext();
                        friendlyObjects.detachDenode((Denode<PlayerObject>)item);
                        item = temp;
                    } else {
                        item = item.getNext();
                    }
                }
                
                /*
                 * Collision Detection (QuadTree)
                 */

                /*
                 * Update game objects
                 */

                //  Update Player
                player.update(dt);

                //  Update friendlyObjects
                item = friendlyObjects.getHead();
                while(item != null){
                    ((PlayerObject)item.getData()).update(dt);
                    item = item.getNext();
                }
            }
        );

        Global.GAME_ENVIRONMENT.setDrawFunction(
            (g) ->{
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHints(Global.RH);

                g2d.setColor(new Color(0xFF0000));
                player.draw(g , Global.CANVAS);

                Denode<?> item = friendlyObjects.getHead();
                while( item != null){
                    ((PlayerObject)item.getData()).draw(g , Global.CANVAS);
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
                if(n == KeyEvent.VK_D) player.switchSpeed();                    
                if(n == KeyEvent.VK_C) player.startShooting();                    
            }
        );

        Global.KEYBOARD.setKeyReleaseFunction(
            (k) ->{
                int n = k.getKeyCode();
                if(n == KeyEvent.VK_UP) up = false;
                if(n == KeyEvent.VK_DOWN) down = false;
                if(n == KeyEvent.VK_LEFT) left = false;                  
                if(n == KeyEvent.VK_RIGHT) right = false;
                if(n == KeyEvent.VK_C) player.stopShooting();  
            }
        );


        //Load Classes
        PlayerCharacter.loadSprite();
        PlayerBulletBasic.loadSprite();

        //Load objects
        player = new PlayerCharacter();
        friendlyObjects = new DoublyLinkedList<>();
        enemyObjects = new DoublyLinkedList<>();
    }

    public void unloadScene() throws Exception{
        //TODO: Make an unloadScene function

        //Unload Objects
        player = null;
        friendlyObjects = null;
        enemyObjects = null;

        //Unload classes
        PlayerCharacter.unload();
        PlayerBulletBasic.unload();

    }

    public void resetListeners(){
        up = down = left = right = false;
    }

}
