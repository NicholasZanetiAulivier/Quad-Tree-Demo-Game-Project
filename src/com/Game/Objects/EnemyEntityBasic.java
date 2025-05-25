package com.Game.Objects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import com.DataType.Vector2;
import com.Game.Engine.Global;

public class EnemyEntityBasic extends EnemyObject{
    private static BufferedImage sprite;

    private static final int SPRITE_WIDTH = 64;
    private static final int SPRITE_HEIGHT = 64;

    private static final float HITBOX_X_OFFSET = 16;
    private static final float HITBOX_Y_OFFSET = 20;
    private static final int HITBOX_WIDTH = 35;
    private static final int HITBOX_HEIGHT = 32;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    public EnemyEntityBasic(float x , float y){
        position = new Vector2(x, y);
        velocity = new Vector2(0,100);
        acceleration = new Vector2(0,1000);
        hitbox = new RectangularHitbox(x+HITBOX_X_OFFSET, y+HITBOX_Y_OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT);
    }

    public static void loadSprite() throws IOException{
        BufferedImage temp = ImageIO.read(EnemyEntityBasic.class.getResource("rsc/Enemies/BasicEnemy.png"));
        sprite = new BufferedImage(temp.getWidth(), temp.getHeight(),temp.getType());
        Graphics2D g = sprite.createGraphics();
        g.rotate(Math.toRadians(180) , temp.getWidth()/2 , temp.getHeight()/2);
        g.drawImage(temp , null , 0 , 0);
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

    @Override
    public void update(float dt){
        //TODO: make basic enemy update function
        if(position.y > Global.realHeight) shouldDestroy = true;

        Vector2 halfAccel = Vector2.scale(acceleration , 0.5f*dt);
        velocity.add(halfAccel);
        this.position.add(Vector2.scale(velocity , dt));
        velocity.add(halfAccel);
        hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);
    }

    @Override
    public void draw(Graphics g , ImageObserver observer){
        g.drawImage(sprite, Math.round(position.x), Math.round(position.y), SPRITE_WIDTH,SPRITE_HEIGHT,observer);
    }

    @Override
    public boolean hasCollisions(){
        return true;
    }

    @Override
    public Rectangle2D getBounds(){
        return hitbox.getBounds();
    }

    @Override
    public short getType(){
        return CollisionObject.RECTANGLE;
    }

    @Override
    public short getIdentity(){
        return CollisionObject.ENEMY_BASIC;
    }

    @Override
    public void isColliding(CollisionObject c){
        //TODO: do this
    }
}
