package com.Game.Objects;

import java.awt.geom.Rectangle2D;

/*
 * Abstract class for hitboxes that will be bound to objects
 * as a field
 */
public abstract class Hitbox implements CollisionObject , Drawable{
    protected Rectangle2D bounds;

    protected Hitbox(Rectangle2D bounds){
        this.bounds = bounds;
    }

    protected Hitbox(float x , float y , float width , float height){
        this(new Rectangle2D.Float(x,y,width,height));
    }

    @Override
    public Rectangle2D getBounds(){
        return bounds;
    }

    public void setPosition(float x ,float y){
        this.bounds.setRect(x, y, bounds.getWidth(), bounds.getHeight());
    }
}
