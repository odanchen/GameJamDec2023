package com.totallynotacult.jam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.totallynotacult.jam.map.BitMasker;

public class MyGdxGame extends Game {
    SpriteBatch batch;
    BitmapFont font;
    Screen dungeon;

    @Override
    public void create() {

        BitMasker.fillMaps();
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MenuScreen(this, "Welcome to the The True Time Keeper", "Please turn up your volume."));

        //RoomGen r = new RoomGen(5);
    }



    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
