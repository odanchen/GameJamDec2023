package com.totallynotacult.jam.map;

import com.badlogic.gdx.graphics.Texture;

public class BackwardTravelTile extends Tile {
    protected final int SIZE = 16;
    public boolean weaponTile = false;

    public BackwardTravelTile(Texture texture, int row, int col) {
        super(texture, row, col);

        setBounds(col * SIZE, row * SIZE, 16, 32);
    }
}
