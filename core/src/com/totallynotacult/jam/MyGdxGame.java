package com.totallynotacult.jam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.totallynotacult.jam.map.Room;
import com.totallynotacult.jam.map.RoomGen;

public class MyGdxGame extends Game {
    SpriteBatch batch;
    BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new GameOverScreen(this));

        //RoomGen r = new RoomGen(5);
    }

    public void changeScreen(Screen predecessor, Screen successor) {
        predecessor.dispose();
        setScreen(successor);
    }

    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
