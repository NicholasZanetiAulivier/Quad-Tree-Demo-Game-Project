package com.Game.Engine;

import java.awt.Color;

import com.Game.Window.MainWindow;


/*
 * Class to be initialized by main
 */
public class GameEnv {
    private MainWindow main;
    private static final int FPS = 60;
    private static final long FPS_TIME = 1_000_000_000/FPS;
    public boolean s = true;

    public GameEnv(int width , int height , String name){
        main = new MainWindow(width,height,name);
        System.out.println(main);
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
        int col;
        if (s) col = 0x000000;
        else col = 0xFFFFFF;
        main.getCanvas().setBackground(new Color(col));
        s = !s;
    }

    /*
     * Method to draw game objects to screen
     */
    public void draw(){
        main.repaint();
    }

    /*
     * Method to run game
     */
    public void run(){
        long currTime = System.nanoTime();
        long newTime;
        while(this.main.isVisible()){

            //Wait for the Frame to switch
            //Record newTime to find deltaTime between Frames
            newTime = waitFrame(currTime);

            update(newTime - currTime);
            currTime = System.nanoTime();  
            draw();
            System.out.println("Time "+ currTime);
        }
        System.out.println("Connection terminated");
    }


}
