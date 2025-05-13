package com.Game.Engine;

public abstract class Scene {
    
    public abstract void switchScene() throws Exception;
    public abstract void loadScene() throws Exception;
    public abstract void unloadScene() throws Exception;
}
