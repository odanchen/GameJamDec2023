package com.totallynotacult.jam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.totallynotacult.jam.map.RoomGen;

public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture img;
	BitmapFont font;
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new DungeonScreen());

		RoomGen r = new RoomGen(5);
	}

	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
