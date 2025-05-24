package com.Game.Objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.DataType.Vector2;
import com.Game.Engine.Global;
import com.Game.Scenes.HitboxVisualizer;
import com.Game.Scenes.ShooterGame;

public class PlayerCharacter extends PlayerObject{
    private static BufferedImage sprite = null;
    
    private static final float PLAYER_SPEED = 250;
    private static final int SPRITE_WIDTH = 64;
    private static final int SPRITE_HEIGHT = 64;
    
    private static final float HITBOX_X_OFFSET = 30;
    private static final float HITBOX_Y_OFFSET = 26;
    private static final int HITBOX_WIDTH = 4;
    private static final int HITBOX_HEIGHT = 8;

    private Vector2 position;
    private RectangularHitbox hitbox;
    

    public PlayerCharacter(){
        position = new Vector2(Global.realWidth/2,Global.realHeight/2);
        hitbox = new RectangularHitbox(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET, HITBOX_WIDTH,HITBOX_HEIGHT);
        
    }

    public static void loadSprite() throws IOException{
        sprite = ImageIO.read(PlayerCharacter.class.getResource("rsc/Players/Ship_11.png"));
    }

    public static void unload(){
        sprite = null;
    }

    public void setX(float x){
        position.setX(x);
        hitbox.setPosition(x+HITBOX_X_OFFSET, hitbox.getY());   
    }

    public void setY(float y){
        position.setY(y);
        hitbox.setPosition(hitbox.getX(), y+HITBOX_Y_OFFSET);
    }

    public void setPosition(float x , float y){
        position.setX(x);
        position.setY(y);
        hitbox.setPosition(x+HITBOX_X_OFFSET, y+HITBOX_Y_OFFSET);
    }

    public Hitbox getHitbox(){
        return hitbox;
    }
    
    // @Override
    // public void update(float dt){
    //     //TODO: make update function for player
    //     ShooterGame currScene = (ShooterGame)Global.currentScene;
    //     Vector2 speed = new Vector2(0,0);
    //     if(currScene.up) speed.y -= 1;
    //     if(currScene.down) speed.y += 1;
    //     if(currScene.left) speed.x -= 1;
    //     if(currScene.right) speed.x += 1;

    //     speed.normalize();
    //     speed.multiply(PLAYER_SPEED*dt);
    //     position.add(speed);
    //     System.out.println(speed);
    //     hitbox.setPosition(position.x+speed.x, position.y+speed.y);
    // }

    /*
     * TEMPOORARY
     */
    public void update(float dt){
        // HitboxVisualizer currScene = (HitboxVisualizer)Global.currentScene;
    }

    @Override
    public void draw(Graphics g , ImageObserver observer){
        //TODO: make draw function for player
        g.drawImage(sprite, Math.round(position.x), Math.round(position.y), SPRITE_WIDTH,SPRITE_HEIGHT,observer);
    }

    @Override
    public boolean hasCollisions(){
        return true;
    }
}
