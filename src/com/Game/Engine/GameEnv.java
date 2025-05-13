package com.Game.Engine;

import java.awt.Color;

import com.Game.Window.MainWindow;


/*
 * Class to be initialized by main
 */
public class GameEnv {
    //Constants
    private static final int FPS = 60;
    private static final long FPS_TIME = 1_000_000_000/FPS;
    
    private MainWindow main;

    //Default update function: called in this.mainLoop()
    private static UpdateFunc gameUpdate = new UpdateFunc() {
        @Override
        public void update(long dt){

        }
    };
    
    public GameEnv(int width , int height , String name){
        main = new MainWindow(width,height,name);
        System.out.println(main);
        Global.GAME_ENVIRONMENT = this;
    }

    public static GameEnv init(int width , int height , String name) throws Exception{
        GameEnv game = new GameEnv(width,height,name);
        Global.initScenes();
        System.out.println("Game Environment Successfully Initialized!");
        game.main.getCanvas().setBackground(new Color(0xFFFFFF));
        Global.MainMenu.switchScene();
        return game;
    }

    //Public draw callback function
    public void setDrawFunction(DrawFunc f){
        Global.CANVAS.setDrawFunction(f);
    }

    //Public update callback function
    public void setUpdateFunction(UpdateFunc f){
        GameEnv.gameUpdate = f;
    }

    /*
     * Method to wait for Frame(single threaded approach)
     * Returns Time at new frame
     */
    public long waitFrame(long currTime){
        long toWait = currTime + FPS_TIME;
        while((currTime=System.nanoTime()) < toWait)continue;
        return currTime;
    }

    /*
     * Method to process events ( click, keypress, key release, etc)
     */
    public void handleEvents(){

    }

    /*
     * Method to be run as the main loop
     */
    public void mainLoop(){
        long currTime = System.nanoTime();
        long newTime = System.nanoTime();
        long dt;

        while(this.main.isVisible()){
            handleEvents();
            newTime = waitFrame(currTime);
            dt = newTime - currTime;
            gameUpdate.update(dt);
            main.repaint();
            currTime = System.nanoTime();
        }
    }

    /*
     * Method to run game
     */
    public void run(){
        try{
            mainLoop();
        }catch (Exception e){
            System.err.println(e);
        }
    }


}
