package com.totallynotacult.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class ScreenSaver implements Screen {
    OrthographicCamera camera;
    MyGdxGame game;
    int xVelocity = 150;
    int yVelocity = 150;
    Rectangle logo;
    Texture mcpt_img;

    public ScreenSaver(MyGdxGame game) {
        camera = new OrthographicCamera();
        this.game = game;
        camera.setToOrtho(false, 800, 480);

        mcpt_img = new Texture(Gdx.files.internal("mcpt_logo.png"));
        logo = new Rectangle();
        logo.x = 400;
        logo.y = 240;
        logo.width = mcpt_img.getWidth();
        logo.height = mcpt_img.getHeight();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.4f,0.4f,0.4f,1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(mcpt_img, logo.x, logo.y);
        game.batch.end();
        updateLogoPosition(delta);

    }

    public void updateLogoPosition(float delta) {
        logo.x += xVelocity * delta;
        logo.y += yVelocity * delta;
        if (logo.x > 800 - mcpt_img.getWidth() || logo.x < 0) {
            xVelocity *= -1;
        }
        if (logo.y > 480 - mcpt_img.getHeight() || logo.y < 0) {
            yVelocity *= -1;
        }
    }

    @Override
    public void show() {

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
