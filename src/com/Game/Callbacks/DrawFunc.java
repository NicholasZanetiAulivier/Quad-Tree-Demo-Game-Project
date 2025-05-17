package com.Game.Callbacks;

import com.Game.Scenes.Scene;

import java.awt.Graphics;

@FunctionalInterface
public interface DrawFunc {
    public void draw(Graphics g, Scene currentScene);
}
