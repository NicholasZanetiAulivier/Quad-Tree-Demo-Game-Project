package com.Game.Engine;

import com.Game.Window.MainWindow;

import java.awt.RenderingHints;

import com.Game.Callbacks.DrawFunc;
import com.Game.Callbacks.UpdateFunc;
import com.Game.Events.Keyboard;
import com.Game.Events.Mouse;

//Handle events with EventHandlers

/*
 * Class to be initialized by main
 */
public class GameEnv implements Runnable{
    private static final long FPS_TIME = 1_000_000_000/Global.FPS;
    private static boolean isInitialized = false;

    public int frames = 0;

    //Default update function: called in this.mainLoop()
    @SuppressWarnings("all")
    private static UpdateFunc gameUpdate = (d) -> {};
    
    public GameEnv(int width , int height , String name , int numOfCanvases)throws Exception{
        if (isInitialized) {
            Global.MAIN_WINDOW.dispose();
            throw new ExceptionInInitializerError("Game Environment has already been Initialized");
        }else {
            Global.GAME_ENVIRONMENT = this;
            
            Global.MAIN_WINDOW = new MainWindow(width,height,name , numOfCanvases);
            System.out.println(Global.MAIN_WINDOW);

            Global.RH = new RenderingHints(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
            Global.KEYBOARD = new Keyboard();
            Global.MAIN_WINDOW.addKeyListener(Global.KEYBOARD);

            Global.MOUSE = new Mouse();
            for(int i = 0 ; i < numOfCanvases ; i++){
                Global.CANVAS[i].addMouseListener(Global.MOUSE);
                Global.CANVAS[i].addMouseMotionListener(Global.MOUSE);
            }
            
            Global.initScenes();
            System.out.println("Game Env Successfully Initialized");
            
            GameEnv.isInitialized = true;
        }
    }

    //Public draw callback function
    public void setDrawFunction(int id , DrawFunc f){
        if(id == 999){
            Global.DEBUG_CANVAS.setDrawFunction(f);
            return;
        }
        Global.CANVAS[id].setDrawFunction(f);
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
        
        //Get frame info
        if (frames++ > 59) {System.out.println(System.nanoTime()); frames =0 ;}
        return currTime;
    }

    /*
     * Method to be run as the main loop
     */
    public void mainLoop(){
        long currTime = System.nanoTime();
        long newTime = System.nanoTime();

        while(Global.MAIN_WINDOW.isVisible()){
            newTime = waitFrame(currTime);
            gameUpdate.update((float)(newTime-currTime)/1_000_000_000);
            Global.MAIN_WINDOW.repaint();
            currTime = System.nanoTime();

            // Get memory info
            // System.out.println(Runtime.getRuntime().totalMemory()/(1024*1024));
            // System.out.println(Runtime.getRuntime().freeMemory()/(1024*1024));
        }
    }

    /*
     * Method to run game
     */
    public void run(){
        try{
            mainLoop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //WINDOW NOT RESIZEABLE

}
