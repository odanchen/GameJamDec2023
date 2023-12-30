package com.totallynotacult.jam.holders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum TextureHolder {
    PISTOL_BULLET("bullets/pistol_bullet.png"),
    SHOTGUN_BULLET("bullets/shotgun_bullet.png"),
    MACHINEGUN_BULLET("bullets/machinegun_bullet.png"),
    ENEMY("weaponPickup.png"),
    GREY_TILE("tiledFloor.png"),
    MCPT_LOGO("mcpt_logo.png"),
    WALL("wall_tile.png"),
    SHADOW("shadow.png"),
    ENEMY_SHEET("enemy_sheet.png"),
    HITBOX("hitbox.png"),
    TIME_TILE("timeTravelTile.png"),
    TILESET("tileset.png"),
    EMPTY("tileEmpty.png"),
    BLUR("blurVig.png");

    private final Texture texture;

    public Texture getTexture() {
        return texture;
    }

    public void dispose() {
        for (TextureHolder value : values()) value.texture.dispose();
    }

    private TextureHolder(String s) {
        texture = new Texture(Gdx.files.internal(s));
    }
}