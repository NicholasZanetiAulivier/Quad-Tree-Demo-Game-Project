package com.Game.Engine;

import com.Game.Scenes.Scene;

public interface UpdateFunc {
    public void update(long dt , Scene currScene);
}
