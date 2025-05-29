package com.Game.Objects;

import java.awt.Rectangle;

/*
 * Abstract class for hitboxes that will be bound to objects
 * as a field
 */
public abstract class Hitbox implements Drawable{
    protected Rectangle bounds;

    protected Hitbox(Rectangle bounds){
        this.bounds = bounds;
    }

    protected Hitbox(float x , float y , float width , float height){
        this(new Rectangle((int)x,(int)y,(int)width,(int)height));
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void setPosition(float x ,float y){
        this.bounds.x = (int)x;
        this.bounds.y = (int)y;
    }

    public int getX(){
        return (int)bounds.getX();
    }

    public int getY(){
        return (int)bounds.getY();
    }
}
