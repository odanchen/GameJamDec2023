package com.totallynotacult.jam.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tile extends Sprite {
    protected final int SIZE = 16;

    public Tile(Texture texture, int row, int col) {
        super(texture);
        setBounds(col * SIZE, row * SIZE, SIZE, SIZE);
    }
}
