package com.Game.Callbacks;

import java.awt.event.KeyEvent;

@FunctionalInterface
public interface KeyTypedFunc {
    public void typed(KeyEvent e);
}
