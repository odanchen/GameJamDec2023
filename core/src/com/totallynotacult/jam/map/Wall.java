package com.totallynotacult.jam.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Wall extends Tile {
    public Wall(Texture texture, int row, int col) {
        super(texture, row, col);
    }

    public Wall(TextureRegion textureRegion, int row, int col) {
        super(textureRegion, row, col);
    }
}
