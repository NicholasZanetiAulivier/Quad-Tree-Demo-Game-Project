package com.Game.Objects;

import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import com.DataType.Vector2;

public class EnemyBulletSpread extends EnemyBulletBasic{
    private static BufferedImage sprite;
    private static final int BULLET_WIDTH = 16;
    private static final int BULLET_HEIGHT = 16;
    private static final int BULLET_VELOCITY = 100;
    
    private static final int HITBOX_RADIUS = 6;
    private static final int HITBOX_X_OFFSET = 2;
    private static final int HITBOX_Y_OFFSET = 2;
    
    public EnemyBulletSpread(Vector2 pos , Vector2 dire){
        position = pos;
        direction = dire;
        direction.normalize();
        hitbox = new HitboxCircular(position.x+HITBOX_X_OFFSET,position.y+HITBOX_Y_OFFSET,HITBOX_RADIUS);
    }

    public EnemyBulletSpread(float x , float y , float dirX , float dirY) throws Throwable{
        this(new Vector2(x,y) , new Vector2(dirX,dirY));
    }
    
    public static void loadSprite() throws IOException{
        sprite = ImageIO.read(EnemyBulletSpread.class.getResource("rsc/Bullets/EnemyPurpleBullet.png"));
    }

    public static void unload(){
        sprite.flush();
        sprite = null;
    }

    @Override
    public void move(float dt){
        position.add(Vector2.scale(direction, dt*BULLET_VELOCITY));
        hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);
    }

    @Override
    public void draw(Graphics g , ImageObserver o){
        g.drawImage(sprite, Math.round(position.x), Math.round(position.y),BULLET_WIDTH, BULLET_HEIGHT, o);
    }

    @Override
    public boolean hasCollisions(){
        return true;
    }

    @Override
    public short getType(){
        return CollisionObject.CIRCLE;
    }

    @Override
    public short getIdentity(){
        return CollisionObject.ENEMY_BULLET_SPREAD;
    }

    @Override
    public void isColliding(CollisionObject c){
    }
}
