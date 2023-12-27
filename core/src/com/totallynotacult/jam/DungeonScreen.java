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
import com.totallynotacult.jam.map.RoomGen;
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

   // final ROOMWIDTH =
    private Room currentRoom;
    Room[][] rooms;
    int row;
    int col;

    Camera cam;


    public DungeonScreen() {
        batch = new SpriteBatch();
        entityManager = new EntityManager(batch);
        camera = new OrthographicCamera();
        character = new PlayerCharacter(entityManager, camera, this);
        cam = new Camera(character,320,320);

        RoomGen r = new RoomGen(7);
        rooms = r.getLevelMatrix();
        row = r.getStartRoom()[0];
        col = r.getStartRoom()[1];
        currentRoom = rooms[row][col];

        fixRoom();

        camera.setToOrtho(false, 256, 256);
    }

    void fixRoom() {
        if (!roomExists(1, 0)) currentRoom.sealExit(1, 0);
        if (!roomExists(-1, 0)) currentRoom.sealExit(-1, 0);
        if (!roomExists(0, 1)) currentRoom.sealExit(0, 1);
        if (!roomExists(0, -1)) currentRoom.sealExit(0, -1);
    }

    boolean roomExists(int dRow, int dCol) {
        return row + dRow >= 0 && row + dRow < rooms.length && col + dCol >= 0 &&
                col + dCol < rooms[row].length && rooms[row + dRow][col + dCol].getRoomType() != 0;
    }

    void changeRoom(int dRow, int dCol) {
        currentRoom = rooms[row += dRow][col += dCol];
        cam = new Camera(character,256,256);
        fixRoom();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
       // cam.camFollow();
        ScreenUtils.clear(Color.GRAY);


        batch.begin();

        renderTiles(batch);

        character.update(currentRoom.getAllTiles(), delta);
        character.draw(batch);

        entityManager.updateEntities(currentRoom.getAllTiles(), delta);
        entityManager.drawEntities();
        batch.end();

       // camera.position.set(cam.x, cam.y, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

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
