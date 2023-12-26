package com.totallynotacult.jam;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class DungeonScreen implements Screen {

    OrthographicCamera camera;
    PlayerCharacter character;
    SpriteBatch batch;

    public DungeonScreen() {
        camera = new OrthographicCamera();
        character = new PlayerCharacter();
        batch = new SpriteBatch();
        camera.setToOrtho(false, 800, 800);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(Color.BLUE);
        batch.begin();


        batch.end();
        camera.position.lerp(new Vector3((float) character.xCor(), (float) character.yCor()), 0.1f);

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
