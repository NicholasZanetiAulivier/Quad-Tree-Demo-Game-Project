package com.Game.Objects;

import java.awt.geom.Rectangle2D;

public interface CollisionObject {
    static final short CIRCLE = 1;

    public abstract Rectangle2D getBounds();
    public abstract boolean checkCollision(CollisionObject o);
    public abstract short getType();
    public abstract void isColliding(CollisionObject o);
}
