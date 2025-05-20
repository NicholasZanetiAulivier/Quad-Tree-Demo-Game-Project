package com.Game.Callbacks;

import java.awt.event.KeyEvent;

@FunctionalInterface
public interface KeyPressedFunc {
    public void pressed(KeyEvent e);
}
