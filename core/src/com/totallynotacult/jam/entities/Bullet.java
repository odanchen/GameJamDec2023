package com.totallynotacult.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.totallynotacult.jam.map.Tile;

import java.util.List;

public class Bullet extends Entity {
    private float speed = 400;
    float angle;

    public Bullet(float xCor, float yCor, float angle) {
        super(new Texture(Gdx.files.internal("bullet.png")), xCor, yCor);
        this.angle = angle;
    }

    @Override
    public void update(List<Tile> room, float deltaTime) {
        translateX((float) (speed * Math.cos(angle) * deltaTime));
        translateY((float) (speed * Math.sin(angle) * deltaTime));
    }
}
