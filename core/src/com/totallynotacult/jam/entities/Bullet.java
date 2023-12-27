package com.totallynotacult.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.totallynotacult.jam.map.Tile;
import com.totallynotacult.jam.weapons.Weapon;

import java.util.List;

public class Bullet extends Entity {
    private float speed;
    float angle;

    public Bullet(float xCor, float yCor, float angle, Weapon weapon) {
        super(weapon.bulletTexture, xCor, yCor);
        this.setRotation((float) Math.toDegrees(angle));
        this.speed = weapon.bulletSpeed;
        this.angle = angle;
    }

    @Override
    public void update(List<Tile> room, float deltaTime) {
        translateX((float) (speed * Math.cos(angle) * deltaTime));
        translateY((float) (speed * Math.sin(angle) * deltaTime));
    }
}
