package com.Game.Engine;

import com.Game.Window.GameScreen;
import com.Game.Scenes.MainMenu;
import com.Game.Scenes.Scene;

public abstract class Global {
    public static GameEnv GAME_ENVIRONMENT = null;
    public static GameScreen CANVAS = null;
    public static Scene currentScene = null;
    public static Scene MainMenu = null;

    public static int originalWidth;
    public static int originalHeight;
    public static double DRAW_SCALE = 1;                        //DRAW_SCALE: Scale at which entities are drawn

    public static final double ASPECT_RATIO  = (double)1/1;     //ASPECT_RATIO: Window scale(width/height)

    public static void initScenes(){
        MainMenu = new MainMenu();
    }
}
