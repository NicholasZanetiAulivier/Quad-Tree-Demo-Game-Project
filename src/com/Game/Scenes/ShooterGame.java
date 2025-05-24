package com.Game.Scenes;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Color;

import com.DataStruct.DoublyLinkedList;
import com.Game.Engine.Global;
import com.Game.Objects.EnemyObject;
import com.Game.Objects.PlayerCharacter;
import com.Game.Objects.PlayerObject;

public class ShooterGame extends Scene{
    private PlayerCharacter player;
    private DoublyLinkedList<PlayerObject> friendlyObjects;
    private DoublyLinkedList<EnemyObject> enemyObjects;

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
                player.update(dt);
            }
        );

        Global.GAME_ENVIRONMENT.setDrawFunction(
            (g) ->{
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHints(Global.RH);

                g2d.setColor(new Color(0xFF0000));
                player.draw(g , Global.CANVAS);
            }
        );

        Global.KEYBOARD.setKeyPressFunction(
            (k) ->{
                int n = k.getKeyCode();
                if(n == KeyEvent.VK_UP) this.up = true;
                if(n == KeyEvent.VK_DOWN) this.down = true;
                if(n == KeyEvent.VK_LEFT) this.left = true;
                if(n == KeyEvent.VK_RIGHT) this.right = true;
            }
        );

        Global.KEYBOARD.setKeyReleaseFunction(
            (k) ->{
                int n = k.getKeyCode();
                if(n == KeyEvent.VK_UP) this.up = false;
                if(n == KeyEvent.VK_DOWN) this.down = false;
                if(n == KeyEvent.VK_LEFT) this.left = false;
                if(n == KeyEvent.VK_RIGHT) this.right = false;
            }
        );


        //Load Classes
        PlayerCharacter.loadSprite();

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

    }

    public void resetListeners(){
        up = down = left = right = false;
    }

}
