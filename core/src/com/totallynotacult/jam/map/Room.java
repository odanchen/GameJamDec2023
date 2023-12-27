package com.totallynotacult.jam.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Room {
    private Tile[][] tiles;
    private int type;

    public Room(int type) {
        this.type = type;
        if (type != 0) {
            var texture = new Texture(Gdx.files.internal("room1.png"));
            tiles = generateRoomMatrix(texture);

        }
    }

    public int getRoomType() {return type;}
    public Color getPixelID(int x, int y, Texture texture) {
        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        Pixmap pixmap = texture.getTextureData().consumePixmap();
        return new Color(pixmap.getPixel(x, y));
    }

    public Tile[][] generateRoomMatrix(Texture texture) {
        Tile[][] mat = new Tile[16][16];
        var tileImg = new Texture(Gdx.files.internal("greyTile.jpeg"));
        var wallImg = new Texture(Gdx.files.internal("wall.png"));

        for (int row = 0; row < 16; row++)
            for (int col = 0; col < 16; col++) {
                if (getPixelID(col, row, texture).equals(Color.BLACK)) {
                    mat[row][col] = new Wall(wallImg, row, col);
                } else mat[row][col] = new Tile(tileImg, row, col);
            }
        return mat;
    }

    public List<Tile> getAllTiles() {
        return Arrays.stream(tiles).flatMap(Arrays::stream).collect(Collectors.toList());
    }
}
