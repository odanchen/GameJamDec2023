package com.totallynotacult.jam.map;

import com.badlogic.gdx.graphics.Texture;

public class SuperChargeTile extends Tile {
    protected final int SIZE = 16;
    public boolean weaponTile = false;

    public SuperChargeTile(Texture texture, int row, int col) {
        super(texture, row, col);
    }
}
