package com.totallynotacult.jam.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class BitMasker {
    private BitMasker() {
    }

    private static int[][] idxs = {{-1, 1}, {0, 1}, {1, 1}, {-1, 0}, {1, 0}, {-1, -1}, {0, -1}, {1, -1}};

    private static Map<Integer, int[]> neightbors = Map.ofEntries(entry(0, new int[]{1, 3}),
            entry(2, new int[]{1, 4}),
            entry(5, new int[]{3, 6}),
            entry(7, new int[]{4, 6}));
    private static Map<Integer, Integer> indexMap = Map.ofEntries(entry(2, 0),
            entry(8, 1),
            entry(10, 2),
            entry(11, 3),
            entry(16, 4),
            entry(18, 5),
            entry(22, 6),
            entry(24, 7),
            entry(26, 8),
            entry(27, 9),
            entry(30, 10),
            entry(31, 11),
            entry(64, 12),
            entry(66, 13),
            entry(72, 14),
            entry(74, 15),
            entry(75, 16),
            entry(80, 17),
            entry(82, 18),
            entry(86, 19),
            entry(88, 20),
            entry(90, 21),
            entry(91, 22),
            entry(94, 23),
            entry(95, 24),
            entry(104, 25),
            entry(106, 26),
            entry(107, 27),
            entry(120, 28),
            entry(122, 29),
            entry(123, 30),
            entry(126, 31),
            entry(127, 32),
            entry(208, 33),
            entry(210, 34),
            entry(214, 35),
            entry(216, 36),
            entry(218, 37),
            entry(219, 38),
            entry(222, 39),
            entry(223, 40),
            entry(248, 41),
            entry(250, 42),
            entry(251, 43),
            entry(254, 44),
            entry(255, 45),
            entry(0, 46));

    private static Color getPixelID(int x, int y, TextureRegion texture) {
        if (!texture.getTexture().getTextureData().isPrepared()) {
            texture.getTexture().getTextureData().prepare();
        }
        Pixmap pixmap = texture.getTexture().getTextureData().consumePixmap();
        return new Color(pixmap.getPixel(texture.getRegionX() + x, texture.getRegionY() - y + texture.getRegionHeight() - 1));
    }

    private static Color colorAt(int row, int col, int[] delta, TextureRegion room) {
        return getPixelID(col + delta[0], row + delta[1], room);
    }

    public static Texture getTexture(List<Texture> tileset, TextureRegion room, int row, int col, Color color) {
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

        return tileset.get(indexMap.get(ans));
    }
}
