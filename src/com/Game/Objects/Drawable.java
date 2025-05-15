package com.Game.Objects;

import java.awt.Graphics;
import java.awt.image.*;

/*
 * Drawable interface should be implemented by spritebased entities.
 */

public interface Drawable {

    public void draw(Graphics g , ImageObserver observer);
}
