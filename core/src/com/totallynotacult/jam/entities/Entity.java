package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.totallynotacult.jam.map.Tile;

import java.util.List;

public abstract class Entity extends Sprite {
    public Entity(Texture texture) {
        super(texture);
    }

    public Entity(Texture texture, float xPos, float yPos) {
        super(texture);
        setPosition(xPos, yPos);
    }

    public abstract void update(List<Tile> room, float deltaTime);
}
