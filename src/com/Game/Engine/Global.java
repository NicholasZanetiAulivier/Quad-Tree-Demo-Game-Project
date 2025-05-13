package com.Game.Engine;

import com.Game.Window.GameScreen;
import com.Game.Scenes.MainMenu;

public abstract class Global {
    public static GameEnv GAME_ENVIRONMENT = null;
    public static GameScreen CANVAS = null;
    public static Scene currentScene = null;
    public static Scene MainMenu = null;

    public static void initScenes(){
        MainMenu = new MainMenu();
    }
}
