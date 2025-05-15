package com.Game.Engine;

import com.Game.Window.MainWindow;
import com.Game.Callbacks.DrawFunc;
import com.Game.Callbacks.UpdateFunc;
import com.Game.Events.Keyboard;
import com.Game.Events.Mouse;
import com.Game.Scenes.Scene;

//Handle events with EventHandlers

/*
 * Class to be initialized by main
 */
public class GameEnv {
    private static final long FPS_TIME = 1_000_000_000/Global.FPS;
    private static boolean isInitialized = false;

    //Default update function: called in this.mainLoop()
    private static UpdateFunc gameUpdate = new UpdateFunc() {
        @Override
        public void update(double dt , Scene currentScene){

        }
    };
    
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
        return currTime;
    }

    /*
     * Method to be run as the main loop
     */
    public void mainLoop(){
        long currTime = System.nanoTime();
        long newTime = System.nanoTime();
        long dt;

        while(Global.MAIN_WINDOW.isVisible()){
            newTime = waitFrame(currTime);
            dt = newTime - currTime;
            double dtSec = (double)dt/1_000_000_000;
            gameUpdate.update(dtSec , Global.currentScene);
            Global.MAIN_WINDOW.repaint();
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

    public void resize(int width , int height){
        Global.CANVAS.fillBounds(width , height);
    }


}
