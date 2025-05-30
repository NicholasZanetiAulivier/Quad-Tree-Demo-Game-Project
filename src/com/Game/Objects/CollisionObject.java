package com.Game.Objects;

import java.awt.Rectangle;

public interface CollisionObject {
    static final short CIRCLE = 1;
    static final short RECTANGLE = 2;
    
    static final short PLAYER = 1000;
    static final short PLAYER_BULLET_BASIC = 1001;
    static final short PLAYER_BULLET_BOUNCING = 1002;
    
    static final short ENEMY_BASIC = 2000;
    static final short ENEMY_HOMING = 2001;
    static final short ENEMY_SHOOTER_SPREAD = 2005;
    static final short ENEMY_SHOOTER_STRAFE = 2007;
    static final short ENEMY_SHOOTER_BASIC = 2004;
    static final short ENEMY_BULLET_BASIC = 2002;
    static final short ENEMY_BULLET_SPREAD = 2003;
    static final short ENEMY_BULLET_ACCEL = 2006;

    static final short ITEM = 3000;

    public abstract Rectangle getBounds();
    public abstract Hitbox<?> getHitbox();
    public abstract void checkCollision(CollisionObject o);
    //CheckCollision should check if object is colliding with other object.
    //If it is, then call this.isColliding and other object's isColliding
    public abstract void isColliding(CollisionObject o);
    public abstract short getType();
    public abstract short getIdentity();
}
