package com.Game.Objects;

import java.awt.geom.Rectangle2D;

public interface CollisionObject {
    static final short CIRCLE = 1;
    static final short RECTANGLE = 2;

    public abstract Rectangle2D getBounds();
    public abstract boolean checkCollision(CollisionObject o);
    public abstract short getType();
}
