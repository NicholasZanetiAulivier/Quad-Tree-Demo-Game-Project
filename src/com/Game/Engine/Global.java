package com.Game.Engine;

import java.awt.RenderingHints;

import com.Game.Window.GameScreen;
import com.Game.Window.MainWindow;
import com.Game.Scenes.WithoutQuadTree;
import com.Game.Scenes.QuadTreeTest;
import com.Game.Scenes.Scene;
import com.Game.Scenes.WithQuadTree;
import com.Game.Events.Keyboard;
import com.Game.Events.Mouse;

public abstract class Global {

    public static GameEnv GAME_ENVIRONMENT = null;
    public static MainWindow MAIN_WINDOW = null;
    public static GameScreen CANVAS = null;
    public static Keyboard KEYBOARD = null;
    public static Mouse MOUSE = null;
    public static RenderingHints RH = null;
    public static Scene currentScene = null;
    public static Scene MainMenu = null;
    public static Scene QTreeTest = null;
    public static Scene MovingQuadTree = null;

    public static int originalHeight = 638;                             //Height at which the screen was drawn at the start
    public static int originalWidth = 815;                              //Width at which the screen was drawn at the start
    public static double DRAW_SCALE = 1;                                //DRAW_SCALE: Scale at which entities are drawn

    public static int realHeight = 600;
    public static int realWidth = 800;

    public static final double ASPECT_RATIO  = 4./3;                      //ASPECT_RATIO: Window scale(width/height)
    public static final int FPS = 60;

    public static void initScenes(){
        MainMenu = new WithoutQuadTree();
        QTreeTest = new QuadTreeTest();
        MovingQuadTree = new WithQuadTree();
    }
}
