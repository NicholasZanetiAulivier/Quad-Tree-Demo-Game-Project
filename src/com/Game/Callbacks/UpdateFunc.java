package com.Game.Callbacks;

import com.Game.Scenes.Scene;

@FunctionalInterface
public interface UpdateFunc {
    public void update(float dt , Scene currScene);
}
