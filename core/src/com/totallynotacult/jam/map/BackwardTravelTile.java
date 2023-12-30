package com.totallynotacult.jam.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BackwardTravelTile extends Tile {
    protected final int SIZE = 16;
    public boolean weaponTile = false;
    Texture sprites;
    TextureRegion[][] sprite_sheet;
    TextureRegion[] flameCycleFrames;
    Animation<TextureRegion> flameCycleAni;
    public BackwardTravelTile(Texture texture, int row, int col) {
        super(texture, row, col);


        sprites = new Texture(Gdx.files.internal("timeTravelTile.png"));
        sprite_sheet = TextureRegion.split(sprites, sprites.getWidth() / 4, sprites.getHeight());
        //Run Cycle
        flameCycleFrames = new TextureRegion[4];
        flameCycleFrames[0] = sprite_sheet[0][0];
        flameCycleFrames[1] = sprite_sheet[0][1];
        flameCycleFrames[2] = sprite_sheet[0][2];
        flameCycleFrames[3] = sprite_sheet[0][3];
        flameCycleAni = new Animation<>(0.08f, flameCycleFrames);
        //setBounds(col * SIZE, row * SIZE, 16, 16);
    }

    public void timeTileAnimations() {

        //Walk/RunCycle
        TextureRegion currentFrame;

        currentFrame = flameCycleAni.getKeyFrame(stateTime, true);
        currentSprite =  new Sprite(currentFrame);

        animateTiles();
    }
}
