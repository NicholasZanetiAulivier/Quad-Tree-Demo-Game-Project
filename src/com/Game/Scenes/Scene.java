package com.Game.Scenes;

import com.Game.Engine.Global;

public abstract class Scene {
    
    public void switchScene() throws Exception{
        if(Global.currentScene != null) Global.currentScene.unloadScene();
        this.loadScene();
        Global.currentScene = this;
    }
    //Call unloadScene() of previous scene
    //Change Global.currScene to this scene
    //Change the draw and update function of GameEnv
    //Call loadScene() of this scene

    public abstract void loadScene() throws Exception;
    //Load all needed variables
    //Load all sprites needed
    //Initialized things that need to be initialized

    public abstract void unloadScene() throws Exception;
}
