package com.Game.Objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.Game.Engine.Global;
import com.Game.Scenes.ShooterGame;

public class PlayerCharacter extends PlayerObject{
    private static BufferedImage sprite = null;
    
    private static final float PLAYER_SPEED = 250;

    private float x = Global.realWidth /2;
    private float y = Global.realHeight/2;

    public PlayerCharacter(){
        x = Global.realWidth /2;
        y = Global.realHeight/2;
        hitbox = new RectangularHitbox(16, 16, 2,2);
        
    }

    public static void loadSprite() throws IOException{
        sprite = ImageIO.read(PlayerCharacter.class.getResource("rsc/Players/Ship_11.png"));
    }

    public static void unload(){
        sprite = null;
    }
    
    @Override
    public void update(float dt){
        //TODO: make update function for player
        ShooterGame currScene = (ShooterGame)Global.currentScene;
        if(currScene.up) y -= PLAYER_SPEED*dt;
        if(currScene.down) y += PLAYER_SPEED*dt;
        if(currScene.left) x -= PLAYER_SPEED*dt;
        if(currScene.right) x += PLAYER_SPEED*dt;

        hitbox.setPosition(x, y);
    }

    @Override
    public void draw(Graphics g , ImageObserver observer){
        //TODO: make draw function for player
        g.drawImage(sprite, Math.round(x), Math.round(y), 64,64,observer);
    }

    @Override
    public boolean hasCollisions(){
        return true;
    }
}
