package com.Game.Callbacks;

import java.awt.event.KeyEvent;

@FunctionalInterface
public interface KeyReleasedFunc {
    public void released(KeyEvent e);
}
