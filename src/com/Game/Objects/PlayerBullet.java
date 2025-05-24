package com.Game.Objects;

import com.DataType.Vector2;

public abstract class PlayerBullet extends PlayerObject implements Bullets{
    protected Vector2 direction;
    protected Hitbox hitbox;
}
