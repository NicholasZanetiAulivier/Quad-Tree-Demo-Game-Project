package com.Game.Callbacks;

import java.awt.Graphics;

@FunctionalInterface
public interface DrawFunc {
    //Use callbacks to access currentScene, OR access currentScene from Global
    public void draw(Graphics g);
}
