package com.Game.Environment;

import com.Game.Window.MainWindow;

public class GameEnv {
    private MainWindow main;

    public GameEnv(int width , int height , String name){
        main = new MainWindow(width,height,name);
        System.out.println(main);
    }

    public static GameEnv init(int width , int height , String name){
        GameEnv game = new GameEnv(width,height,name);
        System.out.println("Game Environment Successfully Initialized!");
        return game;
    }
}
