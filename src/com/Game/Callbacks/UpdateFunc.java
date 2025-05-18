package com.Game.Callbacks;

import com.Game.Scenes.Scene;

@FunctionalInterface
public interface UpdateFunc {
    //Use callbacks to access currentScene, OR access currentScene from Global
    public void update(float dt);
}
