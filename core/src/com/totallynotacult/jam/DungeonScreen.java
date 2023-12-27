package com.totallynotacult.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.totallynotacult.jam.entities.EntityManager;
import com.totallynotacult.jam.map.Room;
import com.totallynotacult.jam.map.Tile;
import com.totallynotacult.jam.map.Wall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DungeonScreen implements Screen {
    private final OrthographicCamera camera;
    private final PlayerCharacter character;
    private final SpriteBatch batch;
    private final EntityManager entityManager;
    private Room currentRoom;
    Room[][] rooms = new Room[3][3];
    int row = 0;
    int col = 0;


    public DungeonScreen() {
        batch = new SpriteBatch();
        entityManager = new EntityManager(batch);
        camera = new OrthographicCamera();
        character = new PlayerCharacter(entityManager, camera, this);

        rooms = new Room[3][3];
        for (int row = 0; row < rooms.length; row++) {
            for (int col = 0; col < rooms[row].length; col++) rooms[row][col] = new Room(1);
        }
        currentRoom = rooms[0][0];

        camera.setToOrtho(false, 512, 512);
    }

    boolean roomExists(int dRow, int dCol) {
        return row + dRow >= 0 && row + dRow < rooms.length && col + dCol >= 0 &&
                col + dCol < rooms[row].length && rooms[row + dRow][col + dCol] != null;
    }

    void changeRoom(int dRow, int dCol) {
        currentRoom = rooms[row += dRow][col += dCol];
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        ScreenUtils.clear(Color.GRAY);


        batch.begin();

        renderTiles(batch);

        character.update(currentRoom.getAllTiles(), delta);
        character.draw(batch);

        entityManager.updateEntities(currentRoom.getAllTiles(), delta);
        entityManager.drawEntities();
        batch.end();


    }

    void renderTiles(SpriteBatch batch) {
        currentRoom.getAllTiles().forEach(tile -> tile.draw(batch));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
