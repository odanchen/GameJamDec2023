package com.totallynotacult.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totallynotacult.jam.map.Tile;
import com.totallynotacult.jam.map.Wall;

import java.util.List;
import java.util.stream.IntStream;

public abstract class Entity extends Sprite {

    protected Sprite currentSprite;
    protected float stateTime = 0f;

    public Entity(Texture texture) {
        super(texture);
    }

    public Entity(Texture texture, float xPos, float yPos) {
        super(texture);
        setPosition(xPos, yPos);
    }

    public Entity(TextureRegion texture, float xPos, float yPos) {
        super(texture);
        setPosition(xPos, yPos);
    }


    public abstract void update(List<Tile> room, float deltaTime, EntityManager manager);

    protected boolean collidesWithWall(List<Tile> room) {
        var rect = getBoundingRectangle();
        return room.stream().anyMatch(tile -> tile instanceof Wall && tile.getBoundingRectangle().overlaps(rect));
    }

    protected void entityAnimations() {

        //Walk/RunCycle
        TextureRegion currentWalkFrame;
        stateTime += Gdx.graphics.getDeltaTime();
        currentSprite.setX(getX());
        currentSprite.setY(getY());
        currentSprite.setSize(getWidth(), getHeight());
        currentSprite.setOrigin(getOriginX(), getOriginY());
        currentSprite.setRotation(getRotation());
        currentSprite.setScale(getScaleX(), getScaleY());
        currentSprite.setColor(getColor());
        set(currentSprite);
    }

    protected int collisionWithWeaponTile(List<Tile> room) {
        var rect = getBoundingRectangle();
        return IntStream.range(0, room.size())
                .filter(i -> room.get(i).weaponTile && room.get(i).getBoundingRectangle().overlaps(rect))
                .findFirst()
                .orElse(-1);
    }
}
