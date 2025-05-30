package com.Game.Objects;

import java.awt.Rectangle;

import com.DataType.Vector2;
import com.Game.Engine.Global;

public abstract class Item extends PlayerObject{
    protected static final float FALL_SPEED = 200;
    protected static final float MAGNET_SPEED = 300;
    protected static final float DISTANCE_THRESHOLD = 100;
    
    public Vector2 position;
    public int points;

    public Item(float x , float y , int p){
        position = new Vector2(x, y);
        points = p;
    }

    public Item(){
        this(0,0,0);
    }

    public void update(float dt){
        Vector2 vectorFromPlayer = Vector2.subtract(new Vector2(Global.Game.player.position.x+PlayerCharacter.SPRITE_WIDTH/2, Global.Game.player.position.y+PlayerCharacter.SPRITE_HEIGHT/2), position);
        float distFromPlayer = vectorFromPlayer.getLength();
        if(distFromPlayer < DISTANCE_THRESHOLD){
            vectorFromPlayer.normalize();
            vectorFromPlayer.multiply(MAGNET_SPEED*dt);
            position.add(vectorFromPlayer);
        } else{
            position.y += FALL_SPEED *dt;
        }
        hitbox.setPosition(position.x, position.y);
    }

    @Override
    public void isColliding(CollisionObject c){
        Global.Game.points += points;
        shouldDestroy = true;
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
        return CollisionObject.ITEM;
    }

    @Override 
    public Rectangle getBounds(){
        return hitbox.getBounds();
    }
}
