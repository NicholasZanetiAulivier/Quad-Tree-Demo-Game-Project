package com.Game.Objects;

import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import java.awt.Graphics;

import javax.imageio.ImageIO;

import com.DataType.Vector2;
import com.Game.Engine.Global;

public class PlayerBulletBouncing extends PlayerBulletBasic{
    private static BufferedImage[] sprite;

    private static final float BULLET_VELOCITY = 1000;
    private static final int BULLET_WIDTH = 16;
    private static final int BULLET_HEIGHT = 16;

    private static final int HITBOX_RADIUS = 8;
    private static final int HITBOX_X_OFFSET = 0;
    private static final int HITBOX_Y_OFFSET = 0;

    private int currState = 0;

    public PlayerBulletBouncing(float x , float y , float xDir , float yDir){
        super(x,y , xDir , yDir);
        direction = new Vector2(xDir, yDir);
        direction.normalize();
        hitbox = new HitboxCircular(position.x + HITBOX_X_OFFSET , position.y + HITBOX_Y_OFFSET , HITBOX_RADIUS);
    }

    public static void loadSprite() throws IOException{
        sprite = new BufferedImage[4];
        BufferedImage temp = ImageIO.read(PlayerBulletBasic.class.getResource("rsc/Bullets/PlayerSpinningBullet.png"));

        sprite[0] = temp.getSubimage(0, 0, 16, 16);
        sprite[1] = temp.getSubimage(16, 0, 16, 16);
        sprite[2] = temp.getSubimage(0, 16, 16, 16);
        sprite[3] = temp.getSubimage(16, 0, 16, 16);
        temp.flush();
        temp = null;
    }

    public static void unload(){
        sprite = null;
    }

    public int cycle(){
        currState += 1;
        currState %= 16;
        return currState/4;
    }

    @Override
    public void draw(Graphics g , ImageObserver o){
        if(stall) g.drawImage(PlayerBulletBasic.popSprite ,  Math.round(position.x),Math.round(position.y), PlayerBulletBouncing.BULLET_WIDTH , PlayerBulletBouncing.BULLET_HEIGHT , o);
        else g.drawImage(sprite[cycle()] , Math.round(position.x) , Math.round(position.y) , PlayerBulletBouncing.BULLET_WIDTH , PlayerBulletBouncing.BULLET_HEIGHT , o);;
    }

    @Override
    protected void move(float dt){
        if(position.x >= Global.realWidth-PlayerBulletBouncing.BULLET_WIDTH || position.x <= 0) direction.x = -direction.x;
        if(position.y >= Global.realHeight) direction.y = -direction.y;

        position.add(Vector2.scale(direction, dt*BULLET_VELOCITY));
        hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);
    }

    @Override
    public short getType(){
        return CollisionObject.CIRCLE;
    }

    @Override
    public short getIdentity(){
        return CollisionObject.PLAYER_BULLET_BOUNCING;
    }

}
