package com.totallynotacult.jam.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Room {


    public Room(int type) {


    }
    public Color getPixelID(int x, int y, Texture texture) {
        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        Pixmap pixmap = texture.getTextureData().consumePixmap();
        return new Color(pixmap.getPixel(x, y));
    }
    public Tile[][] generateRoomMatrix(Texture texture) {
        Tile[][] mat = new Tile[16][16];
        for (int i = 0; i < 16; i++)
            for (int k = 0; k < 16; k++) {
                if (getPixelID(k, i, texture).equals(Color.BLACK)) {
                    mat[i][k] = new Wall(new Texture(Gdx.files.internal("wall.png")), i, k);
                }
                else mat[i][k] = new Tile(new Texture(Gdx.files.internal("greyTile.jpeg")), i, k);
            }
        return mat;
    }
}
