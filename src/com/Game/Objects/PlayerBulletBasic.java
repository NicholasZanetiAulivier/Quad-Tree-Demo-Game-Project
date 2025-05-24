package com.Game.Objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import java.io.IOException;

import com.DataType.Vector2;

public class PlayerBulletBasic extends PlayerBullet{
    private static BufferedImage sprite;

    private static final float BULLET_VELOCITY = 2000;
    private static final int BULLET_WIDTH = 16;
    private static final int BULLET_HEIGHT = 16;

    private Vector2 position;

    public PlayerBulletBasic(float x , float y){
        position = new Vector2(x, y);
        direction = new Vector2(((float)Math.random()/10-0.05f), -1);
        direction.normalize();
    }
    
    public static void loadSprite() throws IOException{
        sprite = ImageIO.read(PlayerBulletBasic.class.getResource("rsc/Bullets/basic.png"));
    }

    public static void unload(){
        sprite = null;
    }

    @Override
    public void update(float dt){
        if(position.y < -80) shouldDestroy = true;
        position.add(Vector2.scale(direction, dt*BULLET_VELOCITY));
    }

    @Override
    public void draw(Graphics g , ImageObserver o){
        g.drawImage(sprite, Math.round(position.x),Math.round(position.y),BULLET_WIDTH , BULLET_HEIGHT , o);
    }

    @Override
    public boolean hasCollisions(){
        return true;
    }

    // @Override
    // public void finalize(){
    //     System.out.println("de");
    // }
}
