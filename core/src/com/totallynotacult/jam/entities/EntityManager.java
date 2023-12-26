package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.totallynotacult.jam.map.Tile;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private final SpriteBatch batch;

    public EntityManager(SpriteBatch batch) {
        this.batch = batch;
    }

    public void updateEntities(List<Tile> room, float deltaTime) {
        entities.forEach(e -> e.update(room, deltaTime));
        entities.removeIf(e -> e.getX() > 1000 || e.getX() < -100 || e.getY() > 1000 || e.getY() < -100 || e.collidesWithWall(room));
    }

    public void drawEntities() {
        entities.forEach(e -> e.draw(batch));
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
}
