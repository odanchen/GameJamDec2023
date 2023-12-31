package com.totallynotacult.jam.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totallynotacult.jam.DungeonScreen;
import com.totallynotacult.jam.holders.TextureHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Room {
    private Tile[][] tiles;
    private int type;
    private int[] exitDirections;
    private boolean visited;
    private int indexVariation;
    public boolean hasAFuture = false;
    private int timeline;

    public Room(int type, int[] exitDirections, int timeLine, int indexVariation) {
        /*
        Types
        0 = null
        1 = start (1 connector)
        2 = middle (2 connectors)
        3 = end (1 connector)
        */
        this.type = type;
        this.exitDirections = exitDirections;
        this.indexVariation = indexVariation;
        Texture roomTexture;
        this.timeline = timeLine;
        TextureRegion roomVariation;
        switch (type) {

            case 1: {
                roomTexture = new Texture(Gdx.files.internal("room_start_sheet.png"));
                TextureRegion[][] ss = TextureRegion.split(roomTexture, roomTexture.getWidth() / 4, roomTexture.getHeight());
                roomVariation = ss[0][exitDirections[0]];
                break;
            }
            case 3: {
                roomTexture = new Texture(Gdx.files.internal("room_end.png"));
                TextureRegion[][] ss = TextureRegion.split(roomTexture, roomTexture.getWidth() / 4, roomTexture.getHeight());
                roomVariation = ss[0][exitDirections[0]];
                break;
            }
            case 2: {
                roomTexture = new Texture(Gdx.files.internal("room_edge_noFrame.png"));
                TextureRegion[][] ss = TextureRegion.split(roomTexture, roomTexture.getWidth() / 60, roomTexture.getHeight()/3);
                int index = 0;
                if (exitDirections[0] == 0 || exitDirections[1] == 0) {
                    if (exitDirections[0] == 1 || exitDirections[1] == 1) index = 60;
                    if (exitDirections[0] == 3 || exitDirections[1] == 3) index = 30;
                    if (exitDirections[0] == 2 || exitDirections[1] == 2) index = 10;
                }
                if (exitDirections[0] == 1 || exitDirections[1] == 1) {
                    if (exitDirections[0] == 2 || exitDirections[1] == 2) index = 50;
                    if (exitDirections[0] == 3 || exitDirections[1] == 3) index = 20;
                }
                if (exitDirections[0] == 2 || exitDirections[1] == 2) {
                    if (exitDirections[0] == 3 || exitDirections[1] == 3) index = 40;
                }

                roomVariation = ss[DungeonScreen.currentTimeLine][index - indexVariation]; break;
                //roomVariation = ss[DungeonScreen.currentTimeLine][index - 6]; break;

            }
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

    public int[] getExitDirections() {
        return exitDirections;
    }
    public int getIndexVariation() {
        return indexVariation;
    }
    public boolean isVisited() {
        return visited;
    }

    public int getRoomType() {
        return type;
    }

    public Color getPixelID(int x, int y, TextureRegion texture) {
        if (!texture.getTexture().getTextureData().isPrepared()) {
            texture.getTexture().getTextureData().prepare();
        }
        Pixmap pixmap = texture.getTexture().getTextureData().consumePixmap();
        return new Color(pixmap.getPixel(texture.getRegionX() + x, texture.getRegionY() - y + texture.getRegionHeight() - 1));
    }

    public Tile[][] generateRoomMatrix(TextureRegion texture) {
        Tile[][] mat = new Tile[16][16];
        Random random = new Random();
        Texture tileImg = TextureHolder.GREY_TILE.getTexture();
        Texture wallImg = TextureHolder.WALL.getTexture();
        Texture timeTile = TextureHolder.TIME_TILE.getTexture();
        Texture empty = TextureHolder.EMPTY.getTexture();

        for (int row = 0; row < 16; row++)
            for (int col = 0; col < 16; col++) {
                if (getPixelID(col, row, texture).equals(Color.BLACK)) {
                    TextureRegion[][] region;
                    if (timeline == 1)
                       region = TextureRegion.split(TextureHolder.TILESET.getTexture(), 16, 16);
                    else if (timeline == 2) region = TextureRegion.split(new Texture(Gdx.files.internal("tilesetTemplate3.png")), 16, 16);
                        else region = TextureRegion.split(new Texture(Gdx.files.internal("tilesetTemplate2.png")), 16, 16);
                    TextureRegion currentTexture = BitMasker.getTexture(region, texture, row, col, Color.BLACK);
                    mat[row][col] = new Wall(currentTexture, row, col);
                } else if (getPixelID(col, row, texture).equals(Color.RED) && !visited) {
                    mat[row][col] = new EnemyTile(empty, row, col);
                } else if ((getPixelID(col, row, texture).equals(Color.YELLOW))) {
                    mat[row][col] = new ForwardTravelTile(new Texture(Gdx.files.internal("timeTravelTile2.png")), row, col);
                    hasAFuture = true;
                } else if ((getPixelID(col, row, texture).equals(Color.BLUE))) {
                    mat[row][col] = new BackwardTravelTile(timeTile, row, col);
                } else if ((getPixelID(col, row, texture).equals(Color.GREEN))) {
                    mat[row][col] = new SuperChargerTile(timeTile, row, col);
                } else if (getPixelID(col, row, texture).equals(Color.BROWN)){
                    mat[row][col] = new Tile(empty,row,col);
                    mat[row][col].weaponTile = true;
                } else mat[row][col] = new Tile(empty,row,col);;
            }
        return mat;
    }

    public void makeVisited() {
        visited = true;
    }

    public List<Tile> getAllTiles() {
        return Arrays.stream(tiles).flatMap(Arrays::stream).collect(Collectors.toList());
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void sealExit(int dRow, int dCol) {
//        var wallImg = new Texture(Gdx.files.internal("wall.png"));
//        if (dRow == 1) for (int col = 0; col < tiles[0].length; col++) tiles[0][col] = new Wall(wallImg, 0, col);
//        if (dRow == -1) for (int col = 0; col < tiles[0].length; col++)
//            tiles[tiles.length - 1][col] = new Wall(wallImg, tiles.length - 1, col);
//
//        if (dCol == 1) for (int row = 0; row < tiles.length; row++)
//            tiles[row][tiles[0].length - 1] = new Wall(wallImg, row, tiles[0].length - 1);
//        if (dCol == -1) for (int row = 0; row < tiles.length; row++)
//            tiles[row][0] = new Wall(wallImg, row, 0);
    }
}
