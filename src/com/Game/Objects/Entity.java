package com.Game.Objects;

/*
 * Entity should be implemented for entities that update every frame
 */
public interface Entity {
    
    public void update(float dt);
    public boolean hasCollisions();
}
