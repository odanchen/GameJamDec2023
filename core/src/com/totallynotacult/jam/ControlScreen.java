package com.totallynotacult.jam;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ControlScreen implements Screen {
    private SpriteBatch batch;
    private Texture img;
    private MyGdxGame game;

    public ControlScreen(MyGdxGame game) {
        this.game = game;
        batch = new SpriteBatch();
        img = new Texture("controls.png"); // Replace with your image file name
    }

    @Override
    public void show() {
        // This method is called when the screen is set as the current screen.
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(img, 0, 0); // Adjust the position as needed
        batch.end();


        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new MenuScreen(game, "Welcome to the The True Time Keeper", "Thank you for reading the controls."));
        }
    }

    @Override
    public void resize(int width, int height) {
        // This method is called when the screen size is changed.
    }

    @Override
    public void pause() {
        // This method is called when the game is paused (e.g., when it loses focus).
    }

    @Override
    public void resume() {
        // This method is called when the game resumes from a paused state.
    }

    @Override
    public void hide() {
        // This method is called when the screen is no longer the current screen.
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}