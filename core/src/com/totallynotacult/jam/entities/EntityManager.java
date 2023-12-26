package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.totallynotacult.jam.entities.Entity;

import java.util.ArrayList;

public class EntityManager {

    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private SpriteBatch batch;

    public EntityManager(SpriteBatch batch) {
        this.batch = batch;
    }

    public void render(float deltaTime) {
        entities.forEach(e -> e.render(batch, deltaTime));
        entities.removeIf(e -> e.xPos > 1000 || e.xPos < -100 || e.yPos > 1000 || e.yPos < -100);
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

}
