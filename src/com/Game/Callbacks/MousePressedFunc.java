package com.Game.Callbacks;

import java.awt.event.MouseEvent;

@FunctionalInterface
public interface MousePressedFunc {
    public void press(MouseEvent m);
}
