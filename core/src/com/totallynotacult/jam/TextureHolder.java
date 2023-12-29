package com.totallynotacult.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum TextureHolder {
    PISTOL_BULLET("bullets/pistol_bullet.png"),
    SHOTGUN_BULLET("bullets/shotgun_bullet.png"),
    MACHINEGUN_BULLET("bullets/machinegun_bullet.png"),
    ENEMY("enemy.png"),
    GREY_TILE("floor_tile.png"),
    MCPT_LOGO("mcpt_logo.png"),
    WALL("wall_tile.png"),
    SHADOW("shadow.png"),
    ENEMY_SHEET("enemy_sheet.png");

    private final Texture texture;

    public Texture getTexture() {
        return texture;
    }

    public void dispose() {
        for (var value : values()) value.texture.dispose();
    }

    private TextureHolder(String s) {
        texture = new Texture(Gdx.files.internal(s));
    }
}