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

    public static GameEnv game;

    public GameEnv(int width , int height , String name){
        main = new MainWindow(width,height,name);
        System.out.println(main);
        game = this;
    }

    public static GameEnv init(int width , int height , String name){
        GameEnv game = new GameEnv(width,height,name);
        System.out.println("Game Environment Successfully Initialized!");
        return game;
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
     * Method to update game environment
     */
    public void update(long dt){
    }

    /*
     * Method to draw game objects to screen
     */
    public void draw(){
        main.repaint();
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
            update(dt);
            draw();
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
