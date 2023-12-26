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

public class DungeonScreen implements Screen {

    OrthographicCamera camera;
    PlayerCharacter character;
    SpriteBatch batch;
    EntityManager entityManager;

    Tile[][] tiles = new Tile[10][10];


    public DungeonScreen() {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) tiles[row][col] = new Tile();
        }

        tiles[5][5] = new Wall();

        batch = new SpriteBatch();
        entityManager = new EntityManager(batch);
        camera = new OrthographicCamera();
        character = new PlayerCharacter(entityManager, camera);
        
        camera.setToOrtho(false, 464, 261);
    }

    @Override
    public void show() {

    }


    Texture wall = new Texture(Gdx.files.internal("wall.png"));
    Room room = new Room(1);
    int[][] mat = room.generateRoomMatrix(new Texture(Gdx.files.internal("room1.png")));
    @Override
    public void render(float delta) {
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        ScreenUtils.clear(Color.GRAY);



        batch.begin();
        //camera.position.set(character.position.x, character.position.y, 0);
        character.render(batch, delta);
        entityManager.render(delta);
        //Walls
        for (int i = 0; i < 16; i++)
            for (int k = 0; k < 16; k++)
                if (mat[i][k] == 1) batch.draw(wall, k*16, i*16);

        batch.end();


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

    }
}
