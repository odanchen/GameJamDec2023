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
    EntityManager entityManager;

    Texture wall = new Texture(Gdx.files.internal("wall.png"));
    Room room = new Room(1);
    Tile[][] tiles = room.generateRoomMatrix(new Texture(Gdx.files.internal("room1.png")));


    public DungeonScreen() {
        batch = new SpriteBatch();
        entityManager = new EntityManager(batch);
        camera = new OrthographicCamera();
        character = new PlayerCharacter(entityManager, camera);

        camera.setToOrtho(false, 512, 512);
    }

    @Override
    public void show() {

    }

    public List<Tile> getAllTiles() {
        return Arrays.stream(tiles).flatMap(Arrays::stream).collect(Collectors.toList());
    }



    @Override
    public void render(float delta) {
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        ScreenUtils.clear(Color.GRAY);


        batch.begin();
        //camera.position.set(character.position.x, character.position.y, 0);
        //Walls
       // for (int i = 0; i < 16; i++)
         //   for (int k = 0; k < 16; k++)
           //     if (mat[i][k] == 1) batch.draw(wall, k*16, i*16);


        renderTiles(batch);

        character.update(getAllTiles(), delta);
        character.draw(batch);

        entityManager.updateEntities(getAllTiles(), delta);
        entityManager.drawEntities();
        batch.end();


    }

    void renderTiles(SpriteBatch batch) {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                if (tiles[row][col] instanceof Wall) {
                    tiles[row][col].draw(batch);
                }
            }
        }
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
