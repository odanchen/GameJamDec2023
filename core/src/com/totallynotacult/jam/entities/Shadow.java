package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.totallynotacult.jam.map.Tile;

import java.util.List;

public class Shadow extends Entity{

    Entity owner;
    public Shadow(Texture texture, Entity owner) {
        super(texture);
        this.owner = owner;
    }


    @Override
    public void update(List<Tile> room, float deltaTime, EntityManager manager) {
        setX(owner.getX());
        setY(owner.getY());
    }
}

