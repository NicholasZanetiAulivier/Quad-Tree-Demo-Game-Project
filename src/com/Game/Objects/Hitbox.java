package com.Game.Objects;

import java.awt.Rectangle;
import java.awt.Shape;

/*
 * Abstract class for hitboxes that will be bound to objects
 * as a field
 */
public abstract class Hitbox<T extends Shape> implements Drawable{
    protected Rectangle bounds;
    protected T hitbox;

    protected Hitbox(Rectangle bounds , T h){
        this.bounds = bounds;
        this.hitbox = h;
    }

    protected Hitbox(float x , float y , float width , float height , T h){
        this(new Rectangle((int)x,(int)y,(int)width,(int)height), h);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public T getHitbox(){
        return hitbox;
    }

    public void setPosition(float x ,float y){
        bounds.x = (int)x;
        bounds.y = (int)y;
    }

    public int getX(){
        return (int)bounds.getX();
    }

    public int getY(){
        return (int)bounds.getY();
    }
}
