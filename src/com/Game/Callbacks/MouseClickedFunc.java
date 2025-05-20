package com.Game.Callbacks;

import java.awt.event.MouseEvent;

@FunctionalInterface
public interface MouseClickedFunc {
    public void click(MouseEvent m);
}
