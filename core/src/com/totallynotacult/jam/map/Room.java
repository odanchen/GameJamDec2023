package com.totallynotacult.jam.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Room {
    private Tile[][] tiles;
    private int type;
    private int[] exitDirections;

    public Room(int type, int[] exitDirections) {
        /*
        Types
        0 = null
        1 = start (1 connector)
        2 = middle (2 connectors)
        3 = end (1 connector)
        */
        this.type = type;
        this.exitDirections = exitDirections;
        Texture roomTexture;
        TextureRegion roomVariation;
        switch (type) {

            case 1: {
                roomTexture = new Texture(Gdx.files.internal("room_start_sheet.png"));
                TextureRegion[][] ss = TextureRegion.split(roomTexture, roomTexture.getWidth()/4, roomTexture.getHeight());
                roomVariation = ss[0][exitDirections[0]];
            }break;
            default: {
                roomTexture = new Texture(Gdx.files.internal("room1.png"));
                TextureRegion[][] ss = TextureRegion.split(roomTexture, roomTexture.getWidth(), roomTexture.getHeight());
                roomVariation = ss[0][0];
            }

        }

        if (type != 0) {
           // var texture = new Texture(Gdx.files.internal("room1.png"));
            tiles = generateRoomMatrix(roomVariation);
        }
    }

    public int getRoomType() {return type;}
    public Color getPixelID(int x, int y, TextureRegion texture) {
        if (!texture.getTexture().getTextureData().isPrepared()) {
            texture.getTexture().getTextureData().prepare();
        }
        Pixmap pixmap = texture.getTexture().getTextureData().consumePixmap();
        return new Color(pixmap.getPixel(texture.getRegionX()+x, texture.getRegionY()-y+texture.getRegionHeight()-1));

    }

    public Tile[][] generateRoomMatrix(TextureRegion texture) {
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
