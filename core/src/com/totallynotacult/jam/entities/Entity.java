package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.totallynotacult.jam.map.Tile;
import com.totallynotacult.jam.map.Wall;

import java.util.List;
import java.util.stream.IntStream;

public abstract class Entity extends Sprite {
    public Entity(Texture texture) {
        super(texture);
    }

    public Entity(Texture texture, float xPos, float yPos) {
        super(texture);
        setPosition(xPos, yPos);
    }

    public abstract void update(List<Tile> room, float deltaTime, EntityManager manager);

    protected boolean collidesWithWall(List<Tile> room) {
        var rect = getBoundingRectangle();
        return room.stream().anyMatch(tile -> tile instanceof Wall && tile.getBoundingRectangle().overlaps(rect));
    }

    protected int collisionWithWeaponTile(List<Tile> room) {
        var rect = getBoundingRectangle();
        return IntStream.range(0, room.size())
                .filter(i -> room.get(i).weaponTile && room.get(i).getBoundingRectangle().overlaps(rect))
                .findFirst()
                .orElse(-1);
    }
}
