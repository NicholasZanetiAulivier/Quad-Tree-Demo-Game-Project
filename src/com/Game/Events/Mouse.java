package com.Game.Events;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.event.MouseInputListener;

import com.Game.Engine.Global;

public class Mouse implements MouseInputListener{
    public int x = 0;
    public int y = 0;
    public boolean left_down = false;
    public boolean right_down = false;

    private Timer timer = new Timer();

    public Mouse(){
    }

    @Override
    public void mouseDragged(MouseEvent m){
        mouseMoved(m);
    }

    @Override
    public void mouseMoved(MouseEvent m){
        x = (int)(m.getX() / Global.DRAW_SCALE);
        y = (int)(m.getY() / Global.DRAW_SCALE);
    }

    @Override
    public void mouseExited(MouseEvent m){
        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                @SuppressWarnings("all")
                Point xy = MouseInfo.getPointerInfo().getLocation();
                //TODO: Fix offscreen mouse tracking(if wanted)
            }
        }, 0, 10);
    }

    @Override
    public void mouseEntered(MouseEvent m){
        timer.cancel();
        timer.purge();
    }

    @Override
    public void mousePressed(MouseEvent m){
        switch(m.getButton()){
            case MouseEvent.BUTTON1 :{
                this.left_down = true;
                break;
            }
            case MouseEvent.BUTTON2 : {
                this.right_down = true;
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent m){
        switch(m.getButton()){
            case MouseEvent.BUTTON1 :{
                this.left_down = false;
                break;
            }
            case MouseEvent.BUTTON2 : {
                this.right_down = false;
                break;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent m){

    }
}
