package com.Game.Objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import java.io.IOException;

import com.DataType.Vector2;

public class PlayerBulletBasic extends PlayerBullet{
    private static BufferedImage sprite[];
    protected static BufferedImage popSprite;

    private static final float BULLET_VELOCITY = 1750;
    public static final int BULLET_WIDTH = 6;
    public static final int BULLET_HEIGHT = 16;

    private static final int HITBOX_WIDTH = 7;
    private static final int HITBOX_HEIGHT = 16;
    private static final int HITBOX_X_OFFSET = 0;
    private static final int HITBOX_Y_OFFSET = 0;

    protected Vector2 position;

    protected boolean stall = false;
    protected float waitTime = 0.f;
    protected int cycleNum = 0;

    public PlayerBulletBasic(float x , float y , float xDir , float yDir){
        position = new Vector2(x, y);
        direction = new Vector2(xDir , yDir);
        direction.normalize();
        hitbox = new HitboxRectangular(x+HITBOX_X_OFFSET, y+HITBOX_Y_OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT);
    }
    
    public static void loadSprite() throws IOException{
        sprite = new BufferedImage[2];
        BufferedImage temp = ImageIO.read(PlayerBulletBasic.class.getResource("rsc/Bullets/basic.png"));
        sprite[0] = temp.getSubimage(0, 0, 6, 16);
        sprite[1] = temp.getSubimage(6, 0, 6, 16);
        popSprite = ImageIO.read(PlayerBulletBasic.class.getResource("rsc/Bullets/crossPop.png"));
    }

    public static void unload(){
        sprite = null;
    }

    @Override
    public void update(float dt){
        if(stall(dt)) return;
        if(destroyCheck()) return;
        move(dt);
    }

    protected boolean stall(float dt){
        if(stall){
            if((waitTime -= dt) <= 0) shouldDestroy = true;
            return true;
        }
        return false;
    }

    protected boolean destroyCheck(){
        if(position.y < -80){
            shouldDestroy = true;
            return true;
        }
        return false;
    }

    protected void move(float dt){
        position.add(Vector2.scale(direction, dt*BULLET_VELOCITY));
        hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);
    }

    @Override
    public void draw(Graphics g , ImageObserver o){
        if(stall) g.drawImage(popSprite ,  Math.round(position.x),Math.round(position.y),BULLET_WIDTH , BULLET_HEIGHT , o);
        else g.drawImage(sprite[cycle()], Math.round(position.x),Math.round(position.y),BULLET_WIDTH , BULLET_HEIGHT , o);
    }

    private int cycle(){
        cycleNum = ++cycleNum % 8;
        return cycleNum/4;
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
    public Rectangle getBounds(){
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
