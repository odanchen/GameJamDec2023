package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {
    protected float xPos;
    protected float yPos;
    protected float deltaTime;

    public Entity(float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public abstract void act(float deltaTime);

    public void render(SpriteBatch batch, float deltaTime) {
        this.deltaTime = deltaTime;
        act(deltaTime);
    }
}
