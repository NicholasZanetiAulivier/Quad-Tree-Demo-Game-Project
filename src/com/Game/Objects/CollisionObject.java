package com.Game.Objects;

public interface CollisionObject {
    static short CIRCLE = 1;

    public boolean checkCollision(CollisionObject o);
    public short getType();
    public void isColliding(CollisionObject o);
}
