package com.totallynotacult.jam.weapons.shotguns;

import com.totallynotacult.jam.TextureHolder;
import com.totallynotacult.jam.entities.EntityManager;
import com.totallynotacult.jam.weapons.Weapon;

import java.util.Random;

public class Shotgun extends Weapon {
    public int numBullets;
    public float spread;

    public Shotgun() {
        // Create random values for the shotgun
        Random rand = new Random();
        this.damage = 1;
        this.bulletSpeed = 500;
        this.shootDelay = 0.5f;
        this.numBullets = 10;
        this.spread = (float) Math.PI / 4;
        this.bulletTexture = TextureHolder.SHOTGUN_BULLET.getTexture();
        type = 1;
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
