package com.totallynotacult.jam.weapons;

import com.totallynotacult.jam.TextureHolder;
import com.totallynotacult.jam.entities.EntityManager;

import java.util.Random;

public class Shotgun extends Weapon {
    public int numBullets;
    public float spread;

    public Shotgun() {
        // Create random values for the shotgun
        Random rand = new Random();
        this.damage = rand.nextInt(2) + 1;
        this.bulletSpeed = rand.nextInt(100) + 200;
        this.shootDelay = rand.nextInt(2) + 2f;
        this.numBullets = rand.nextInt(3) + 3;
        this.spread = (float) Math.PI / 4;
        this.bulletTexture = TextureHolder.SHOTGUN_BULLET.getTexture();
    }

    @Override
    public void shoot(float xCor, float yCor, float angle, EntityManager manager, boolean isFriendly) {
        if (readyToShoot()) {
            for (int i = 0; i < numBullets; i++) {
                timeSinceShot = shootDelay;
                super.shoot(xCor, yCor, (float) (angle + (Math.random() - 0.5) * spread), manager, isFriendly);
            }
            reset();
        }
    }
}
