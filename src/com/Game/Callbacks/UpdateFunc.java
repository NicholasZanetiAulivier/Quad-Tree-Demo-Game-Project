package com.Game.Callbacks;

@FunctionalInterface
public interface UpdateFunc {
    //Use callbacks to access currentScene, OR access currentScene from Global
    public void update(float dt);
}
