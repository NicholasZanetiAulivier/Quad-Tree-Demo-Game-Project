package com.Game.Objects;

import java.awt.geom.Rectangle2D;

public interface CollisionObject {
    static final short CIRCLE = 1;
    static final short RECTANGLE = 2;
    
    static final short PLAYER = 1000;
    static final short PLAYER_BULLET_BASIC = 1001;
    
    static final short ENEMY_BASIC = 2000;
    static final short ENEMY_HOMING = 2001;
    static final short ENEMY_BULLET_BASIC = 2002;

    public abstract Rectangle2D getBounds();
    public abstract void checkCollision(CollisionObject o);
    //CheckCollision should check if object is colliding with other object.
    //If it is, then call this.isColliding and other object's isColliding
    public abstract void isColliding(CollisionObject o);
    public abstract short getType();
    public abstract short getIdentity();
}
