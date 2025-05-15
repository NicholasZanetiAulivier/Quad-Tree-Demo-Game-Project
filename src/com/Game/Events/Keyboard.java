package com.Game.Events;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Keyboard implements KeyListener{
    public boolean KEY_W = false;
    public boolean KEY_A = false;
    public boolean KEY_S = false;
    public boolean KEY_D = false; 

    @Override
    public void keyPressed(KeyEvent e){
        int c = e.getKeyCode();
        switch(c){
            case KeyEvent.VK_A : {
                this.KEY_A = true;
                break;
            }
            case KeyEvent.VK_W : {
                this.KEY_W = true;
                break;
            }
            case KeyEvent.VK_S : {
                this.KEY_S = true;
                break;
            }
            case KeyEvent.VK_D : {
                this.KEY_D = true;
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        int c = e.getKeyCode();
        switch(c){
            case KeyEvent.VK_A : {
                this.KEY_A = false;
                break;
            }
            case KeyEvent.VK_W : {
                this.KEY_W = false;
                break;
            }
            case KeyEvent.VK_S : {
                this.KEY_S = false;
                break;
            }
            case KeyEvent.VK_D : {
                this.KEY_D = false;
                break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e ){

    }
}