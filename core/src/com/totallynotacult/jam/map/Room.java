package com.totallynotacult.jam.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Room {

    public Room(int type) {

        Texture texture = new Texture(Gdx.files.internal("room1.png"));
        int[][] mat = generateRoomMatrix(texture);
        for (int i = 0; i < 16; i++) {
            for (int k = 0; k < 16; k++)
                System.out.print(mat[i][k]);
            System.out.println();
        }
    }

    public Color getPixelID(int x, int y, Texture texture) {
        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        Pixmap pixmap = texture.getTextureData().consumePixmap();
        return new Color(pixmap.getPixel(x, y));
    }
    public int[][] generateRoomMatrix(Texture texture) {
        int[][] mat = new int[16][16];

        for (int i = 0; i < 16; i++)
            for (int k = 0; k < 16; k++) {
                if (getPixelID(k, i, texture).equals(Color.BLACK))
                    mat[i][k] = 1;
                else mat[i][k] = 0;
            }
        return mat;
    }


}
