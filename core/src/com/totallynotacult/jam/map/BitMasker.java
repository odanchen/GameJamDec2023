package com.totallynotacult.jam.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class BitMasker {
    private BitMasker() {
    }

    private static int[][] idxs = {{-1, 1}, {0, 1}, {1, 1}, {-1, 0}, {1, 0}, {-1, -1}, {0, -1}, {1, -1}};

    public static void fillMaps() {
        neightbors.put(0, new int[]{1, 3});
        neightbors.put(2, new int[]{1, 4});
        neightbors.put(5, new int[]{3, 6});
        neightbors.put(7, new int[]{4, 6});
        indexMap.put(2, 0);
        indexMap.put(8, 1);
        indexMap.put(10, 2);
        indexMap.put(11, 3);
        indexMap.put(16, 4);
        indexMap.put(18, 5);
        indexMap.put(22, 6);
        indexMap.put(24, 7);
        indexMap.put(26, 8);
        indexMap.put(27, 9);
        indexMap.put(30, 10);
        indexMap.put(31, 11);
        indexMap.put(64, 12);
        indexMap.put(66, 13);
        indexMap.put(72, 14);
        indexMap.put(74, 15);
        indexMap.put(75, 16);
        indexMap.put(80, 17);
        indexMap.put(82, 18);
        indexMap.put(86, 19);
        indexMap.put(88, 20);
        indexMap.put(90, 21);
        indexMap.put(91, 22);
        indexMap.put(94, 23);
        indexMap.put(95, 24);
        indexMap.put(104, 25);
        indexMap.put(106, 26);
        indexMap.put(107, 27);
        indexMap.put(120, 28);
        indexMap.put(122, 29);
        indexMap.put(123, 30);
        indexMap.put(126, 31);
        indexMap.put(127, 32);
        indexMap.put(208, 33);
        indexMap.put(210, 34);
        indexMap.put(214, 35);
        indexMap.put(216, 36);
        indexMap.put(218, 37);
        indexMap.put(219, 38);
        indexMap.put(222, 39);
        indexMap.put(223, 40);
        indexMap.put(248, 41);
        indexMap.put(250, 42);
        indexMap.put(251, 43);
        indexMap.put(254, 44);
        indexMap.put(255, 45);
        indexMap.put(0, 46);
    }

    private static Map<Integer, int[]> neightbors = new HashMap<>();
    private static Map<Integer, Integer> indexMap = new HashMap<>();

    private static Color getPixelID(int x, int y, TextureRegion texture) {
        if (x < 0 || x > 15 || y < 0 || y > 15) return Color.BLACK;
        if (!texture.getTexture().getTextureData().isPrepared()) {
            texture.getTexture().getTextureData().prepare();
        }
        Pixmap pixmap = texture.getTexture().getTextureData().consumePixmap();
        return new Color(pixmap.getPixel(texture.getRegionX() + x, texture.getRegionY() - y + texture.getRegionHeight() - 1));
    }

    private static Color colorAt(int row, int col, int[] delta, TextureRegion room) {
        return getPixelID(col + delta[0], row + delta[1], room);
    }

    public static TextureRegion getTexture(TextureRegion[][] tileset, TextureRegion room, int row, int col, Color color) {
        int ans = 0;
        for (int i = 0; i < idxs.length; i++) {
            if (i == 1 || i == 3 || i == 4 || i == 6) {
                if (colorAt(row, col, idxs[i], room).equals(color)) ans |= 1 << i;
            } else {
                if (colorAt(row, col, idxs[i], room).equals(color) &&
                        (colorAt(row, col, idxs[neightbors.get(i)[0]], room).equals(color) &&
                                colorAt(row, col, idxs[neightbors.get(i)[1]], room).equals(color)))
                    ans |= 1 << i;
            }
        }

        ans = indexMap.get(ans);
        return tileset[ans / 7][ans % 7];
    }
}
