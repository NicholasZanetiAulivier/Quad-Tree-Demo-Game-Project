package com.Game.Events;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.event.MouseInputListener;

import com.Game.Callbacks.MouseClickedFunc;
import com.Game.Callbacks.MousePressedFunc;
import com.Game.Callbacks.MouseReleasedFunc;
import com.Game.Engine.Global;

public class Mouse implements MouseInputListener{
    public int x = 0;
    public int y = 0;

    @SuppressWarnings("all")
    private MouseClickedFunc mouseClick = (e)->{};
    @SuppressWarnings("all")
    private MousePressedFunc mousePress = (e)->{};
    @SuppressWarnings("all")
    private MouseReleasedFunc mouseRelease = (e)->{};

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

    public void setMousePressFunction(MousePressedFunc m){
        this.mousePress = m;
    }

    public void setMouseClickedFunction(MouseClickedFunc m){
        this.mouseClick = m;
    }

    public void setMouseReleasedFunction(MouseReleasedFunc m){
        this.mouseRelease = m;
    }

    @Override
    public void mousePressed(MouseEvent m){
        mousePress.press(m);
    }

    @Override
    public void mouseReleased(MouseEvent m){
        mouseRelease.release(m);
    }

    @Override
    public void mouseClicked(MouseEvent m){
        mouseClick.click(m);
    }
}
