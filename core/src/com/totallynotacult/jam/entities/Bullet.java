package com.totallynotacult.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends Entity {
    private float speed = 400;
    float angle;
    Texture texture;

    public Bullet(float xCor, float yCor, float angle) {
        super(xCor, yCor);
        this.texture = new Texture(Gdx.files.internal("bullet.png"));
        this.angle = angle;
    }

    @Override
    public void render(SpriteBatch batch, float deltaTime) {
        super.render(batch, deltaTime);
        batch.draw(texture, xPos, yPos);
    }

    @Override
    public void act(float deltaTime) {
        xPos += speed * Math.cos(angle) * deltaTime;
        yPos += speed * Math.sin(angle) * deltaTime;
    }
}
