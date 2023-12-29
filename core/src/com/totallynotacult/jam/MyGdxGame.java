package com.totallynotacult.jam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {
    SpriteBatch batch;
    BitmapFont font;
    Screen dungeon;

    @Override
    public void create() {
        prepareNewDungeon();
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MenuScreen(this, "Welcome to the Time Massacre", "Please turn up your volume."));

        //RoomGen r = new RoomGen(5);
    }


    public void changeToDungeon() {
        this.setScreen(this.dungeon);
    }

    public void prepareNewDungeon() {
        this.dungeon = new DungeonScreen(this);
    }

    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
