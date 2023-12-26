package com.totallynotacult.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.totallynotacult.jam.entities.Bullet;
import com.totallynotacult.jam.entities.Entity;
import com.totallynotacult.jam.entities.EntityManager;

public class PlayerCharacter {
    private float xCor;
    private float yCor;
    private int health;
    private int maxHealth;
    private double speed;
    private Texture playerTexture;
    private EntityManager entityManager;

    private Camera camera;

    public PlayerCharacter(EntityManager entityManager, Camera camera) {
        xCor = 200;
        yCor = 200;
        health = 6;
        maxHealth = 6;
        speed = 400;
        this.camera = camera;
        playerTexture = new Texture(Gdx.files.internal("mcpt_logo.png"));
        this.entityManager = entityManager;
    }

    public void move(float deltaTime) {

        int horDir = getDir(Gdx.input.isKeyPressed(Input.Keys.D), Gdx.input.isKeyPressed(Input.Keys.A));
        int vertDir = getDir(Gdx.input.isKeyPressed(Input.Keys.W), Gdx.input.isKeyPressed(Input.Keys.S));

        if (horDir != 0 && vertDir != 0) {
            xCor += speed / Math.sqrt(2) * horDir * deltaTime;
            yCor += speed / Math.sqrt(2) * vertDir * deltaTime;
        } else {
            xCor += speed * horDir * deltaTime;
            yCor += speed * vertDir * deltaTime;
        }
    }



    int getDir(boolean forward, boolean backward) {
        if (forward == backward) return 0;
        if (forward) return 1;
        return -1;
    }

    public void render(SpriteBatch batch, float deltaTime) {
        move(deltaTime);


        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            Vector3 click = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(click);

            float angle = (float) Math.atan2(click.y - yCor, click.x - xCor);


            entityManager.addEntity(new Bullet(xCor, yCor, angle));
        }
        batch.draw(playerTexture, xCor, yCor);
    }

    public float xCor() {return xCor;}
    public float yCor() {return yCor;}
}