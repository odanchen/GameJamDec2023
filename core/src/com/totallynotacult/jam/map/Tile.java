package com.totallynotacult.jam.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile extends Sprite {
    protected final int SIZE = 16;
    public boolean weaponTile = false;

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
}
