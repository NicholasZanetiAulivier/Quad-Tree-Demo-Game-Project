package com.Game.Callbacks;

import java.awt.event.MouseEvent;

@FunctionalInterface
public interface MouseReleasedFunc {
    public void release(MouseEvent m);
}
