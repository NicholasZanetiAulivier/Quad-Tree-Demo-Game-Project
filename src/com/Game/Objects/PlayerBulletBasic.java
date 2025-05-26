package com.Game.Objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import java.io.IOException;

import com.DataType.Vector2;

public class PlayerBulletBasic extends PlayerBullet{
    private static BufferedImage sprite;
    private static BufferedImage popSprite;

    private static final float BULLET_VELOCITY = 1750;
    private static final int BULLET_WIDTH = 16;
    private static final int BULLET_HEIGHT = 16;

    private static final int HITBOX_WIDTH = 7;
    private static final int HITBOX_HEIGHT = 17;
    private static final int HITBOX_X_OFFSET = 5;
    private static final int HITBOX_Y_OFFSET = 0;

    private Vector2 position;

    private boolean stall = false;
    private float waitTime = 0.f;

    public PlayerBulletBasic(float x , float y){
        position = new Vector2(x, (int)(y+Math.random()*10-20));
        direction = new Vector2(((float)Math.random()/4-.125f), -1);
        direction.normalize();
        hitbox = new RectangularHitbox(x+HITBOX_X_OFFSET, y+HITBOX_Y_OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT);
    }
    
    public static void loadSprite() throws IOException{
        sprite = ImageIO.read(PlayerBulletBasic.class.getResource("rsc/Bullets/basic.png"));
        popSprite = ImageIO.read(PlayerBulletBasic.class.getResource("rsc/Bullets/crossPop.png"));
    }

    public static void unload(){
        sprite = null;
    }

    @Override
    public void update(float dt){
        if(stall){
            if((waitTime -= dt) <= 0) shouldDestroy = true;
            return;
        }
        if(position.y < -80) shouldDestroy = true;
        position.add(Vector2.scale(direction, dt*BULLET_VELOCITY));
        hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);
    }

    @Override
    public void draw(Graphics g , ImageObserver o){
        if(stall) g.drawImage(popSprite ,  Math.round(position.x),Math.round(position.y),BULLET_WIDTH , BULLET_HEIGHT , o);
        else g.drawImage(sprite, Math.round(position.x),Math.round(position.y),BULLET_WIDTH , BULLET_HEIGHT , o);
    }

    @Override
    public boolean hasCollisions(){
        return true;
    }

    @Override
    public short getType(){
        return CollisionObject.RECTANGLE;
    }

    @Override
    public short getIdentity(){
        return CollisionObject.PLAYER_BULLET_BASIC;
    }

    @Override
    public void checkCollision(CollisionObject c){
        super.checkCollision(c);
    }

    @Override
    public Rectangle2D getBounds(){
        return hitbox.getBounds();
    }

    @Override
    public void isColliding(CollisionObject c){
        //TODO: do this
        if(!stall){
            stall = true;
            waitTime = 0.07f;
        }
    }

    // @Override
    // public void finalize(){
    //     System.out.println("de");
    // }
}
