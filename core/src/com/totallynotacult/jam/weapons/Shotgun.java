package com.totallynotacult.jam.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class Shotgun extends Weapon {
    public int numBullets;
    public float spread;
    public Shotgun() {
        // Create random values for the shotgun
        Random rand = new Random();
        this.damage = rand.nextInt(2);
        this.bulletSpeed = rand.nextInt(100) + 200;
        this.fireRate = rand.nextFloat() + 0.5f;
        this.numBullets = rand.nextInt(3) + 3;
        this.spread = (float) Math.PI / 4;
        this.bulletTexture = new Texture(Gdx.files.internal("bullets/shotgun_bullet.png"));
    }
}
