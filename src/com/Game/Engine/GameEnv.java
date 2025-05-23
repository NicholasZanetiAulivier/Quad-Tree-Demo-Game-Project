package com.Game.Engine;

import com.Game.Window.MainWindow;
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
    private static UpdateFunc gameUpdate = (d) -> {};
    
    public GameEnv(int width , int height , String name)throws Exception{
        if (isInitialized) {
            Global.MAIN_WINDOW.dispose();
            throw new ExceptionInInitializerError("Game Environment has already been Initialized");
        }else {
            Global.GAME_ENVIRONMENT = this;
            
            Global.MAIN_WINDOW = new MainWindow(width,height,name);
            System.out.println(Global.MAIN_WINDOW);
            
            Global.initScenes();
            System.out.println("Game Env Successfully Initialized");
            
            Global.MainMenu.switchScene();

            Global.KEYBOARD = new Keyboard();
            Global.MAIN_WINDOW.addKeyListener(Global.KEYBOARD);

            Global.MOUSE = new Mouse();
            Global.CANVAS.addMouseListener(Global.MOUSE);
            Global.CANVAS.addMouseMotionListener(Global.MOUSE);

            GameEnv.isInitialized = true;
        }
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

            //Get memory info
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
            System.err.println(e);
        }
    }

    public void resize(int width , int height){
        Global.CANVAS.fillBounds(width , height);
    }


}
