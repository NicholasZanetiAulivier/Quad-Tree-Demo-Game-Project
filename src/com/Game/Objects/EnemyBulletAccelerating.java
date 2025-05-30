package com.Game.Objects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;

import com.DataType.Vector2;
import com.Game.Engine.Global;

public class EnemyBulletAccelerating extends EnemyBulletBasic {
    private static BufferedImage[] sprite;
    private static final int BULLET_WIDTH = 16;
    private static final int BULLET_HEIGHT = 16;

    private static final int BULLET_VELOCITY = 100;
    private static final int BULLET_SLOWDOWN = 50;

    private static final int HITBOX_RADIUS = 6;
    private static final int HITBOX_X_OFFSET = 2;
    private static final int HITBOX_Y_OFFSET = 2;

    private Vector2 acceleration;
    private int currState = 0;
    private float fadeDelay = 1f;
    
    public EnemyBulletAccelerating(Vector2 pos , Vector2 dire){
        position = pos;
        direction = dire;
        direction.normalize();
        acceleration = Vector2.scale(direction, -1);
        acceleration.multiply(BULLET_SLOWDOWN);
        direction.multiply(BULLET_VELOCITY);
        hitbox = new HitboxCircular(position.x+HITBOX_X_OFFSET,position.y+HITBOX_Y_OFFSET,HITBOX_RADIUS);
    }

    public EnemyBulletAccelerating(float x , float y , float dirX , float dirY) throws Throwable{
        this(new Vector2(x,y) , new Vector2(dirX,dirY));
    }
    
    public static void loadSprite() throws IOException{
        BufferedImage temp = ImageIO.read(EnemyBulletSpread.class.getResource("rsc/Bullets/EnemyAcceleratingBullet.png"));

        sprite = new BufferedImage[5];

        sprite[0] = temp.getSubimage(0, 0, 8, 8);
        sprite[1] = temp.getSubimage(8, 0, 8, 8);
        sprite[2] = temp.getSubimage(0, 8, 8, 8);
        sprite[3] = temp.getSubimage(8, 8, 8, 8);
        sprite[4] = temp.getSubimage(0, 16, 8, 8);

        temp.flush();
        temp = null;
    }

    public static void unload(){
        for(BufferedImage i : sprite) i.flush();
        sprite = null;
    }

    @Override
    public void update(float dt){
        if(direction.getLength() == 0){
            fadeDelay -= dt;
            if(fadeDelay <= 0)
                shouldDestroy = true;
            return;
        }
        if(position.y > Global.realHeight || position.y < -70 || position.x < -70 || position.x > Global.realWidth) {
            shouldDestroy = true;
            return;
        }
        Vector2 halfAccel = Vector2.scale(acceleration , dt/2);
        direction.add(halfAccel);
        position.add(Vector2.scale(direction , dt));
        direction.add(halfAccel);
        if(Math.signum(direction.x) == Math.signum(acceleration.x)) direction.x = acceleration.x = 0;
        if(Math.signum(direction.y) == Math.signum(acceleration.y)) direction.y = acceleration.y = 0;
        hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);
    }

    @Override
    public void draw(Graphics g , ImageObserver o){
        g.drawImage(sprite[cycle()], Math.round(position.x), Math.round(position.y),BULLET_WIDTH, BULLET_HEIGHT, o);
    }
    
    @Override
    public short getType(){
        return CollisionObject.CIRCLE;
    }

    @Override
    public short getIdentity(){
        return CollisionObject.ENEMY_BULLET_ACCEL;
    }

    @Override
    public void isColliding(CollisionObject c){
    }

    public int cycle(){
        currState += 1;
        currState %= 20;
        return currState/5;
    }
}
