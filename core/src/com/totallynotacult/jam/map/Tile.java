package com.totallynotacult.jam.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile extends Sprite {
    protected final int SIZE = 16;
    public boolean weaponTile = false;
    protected Sprite currentSprite;
    protected float stateTime = 0f;
    public Tile(Texture texture, int row, int col) {
        super(texture);
        setBounds(col * SIZE, row * SIZE, SIZE, SIZE);
    }

    public Tile(TextureRegion texture, int row, int col) {
        super(texture);
        setBounds(col * SIZE, row * SIZE, SIZE, SIZE);
    }
    public Tile(Texture texture, int row, int col, int size) {
        super(texture);
        setBounds(col * size, row * size, size, size);
    }

    public void animateTiles() {
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
}
