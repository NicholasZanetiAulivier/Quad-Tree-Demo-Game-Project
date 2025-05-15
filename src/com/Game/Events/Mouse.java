package com.Game.Events;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import com.Game.Engine.Global;

public class Mouse implements MouseInputListener{
    public int x = 0;
    public int y = 0;

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

    }

    @Override
    public void mouseEntered(MouseEvent m){

    }

    @Override
    public void mousePressed(MouseEvent m){

    }

    @Override
    public void mouseReleased(MouseEvent m){

    }

    @Override
    public void mouseClicked(MouseEvent m){

    }
}
