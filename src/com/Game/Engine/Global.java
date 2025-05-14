package com.Game.Engine;

import com.Game.Window.GameScreen;
import com.Game.Scenes.MainMenu;
import com.Game.Scenes.Scene;

public abstract class Global {
    public static GameEnv GAME_ENVIRONMENT = null;
    public static GameScreen CANVAS = null;
    public static Scene currentScene = null;
    public static Scene MainMenu = null;

    public static int originalHeight = 700;                            //Height at which the screen was drawn at the start
    public static int originalWidth = 700;                             //Width at which the screen was drawn at the start
    public static double DRAW_SCALE = 1;                                //DRAW_SCALE: Scale at which entities are drawn

    public static final double ASPECT_RATIO  = 1.;             //ASPECT_RATIO: Window scale(width/height)

    public static void initScenes(){
        MainMenu = new MainMenu();
    }
}
