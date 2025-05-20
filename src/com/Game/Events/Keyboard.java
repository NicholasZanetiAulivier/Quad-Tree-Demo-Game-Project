package com.Game.Events;

import java.awt.event.KeyListener;

import com.Game.Callbacks.KeyTypedFunc;
import com.Game.Callbacks.KeyPressedFunc;
import com.Game.Callbacks.KeyReleasedFunc;

import java.awt.event.KeyEvent;

public class Keyboard implements KeyListener{
    @SuppressWarnings("all")
    private KeyTypedFunc typeFunc = (e) ->{};
    @SuppressWarnings("all")
    private KeyPressedFunc pressFunc = (e) ->{};
    @SuppressWarnings("all")
    private KeyReleasedFunc releaseFunc = (e) ->{};


    public void setKeyPressFunction(KeyPressedFunc k){
        pressFunc = k;
    }

    public void setKeyTypeFunction(KeyTypedFunc k){
        typeFunc = k;
    }

    public void setKeyReleaseFunction(KeyReleasedFunc k){
        releaseFunc = k;
    }

    @Override
    public void keyPressed(KeyEvent e){
        pressFunc.pressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e){
        releaseFunc.released(e);
    }

    @Override
    public void keyTyped(KeyEvent e ){
        typeFunc.typed(e);
    }
}