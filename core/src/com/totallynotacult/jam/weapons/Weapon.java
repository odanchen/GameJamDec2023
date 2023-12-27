package com.totallynotacult.jam.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.totallynotacult.jam.entities.Bullet;
import com.totallynotacult.jam.entities.EntityManager;

import java.util.Random;

public abstract class Weapon {
    protected int damage;
    protected Texture bulletTexture;
    protected float bulletSpeed;
    protected float shootDelay;
    protected float timeSinceShot = 0;

    public static Weapon getRandomWeapon() {
        Random rand = new Random();
        int weapon = rand.nextInt(3);
        switch (weapon) {
            case 0:
                return new Pistol();
            case 1:
                return new Shotgun();
            case 2:
                return new MachineGun();
        }
        return null;
    }

    public void shoot(float xCor, float yCor, float angle, EntityManager manager) {
        if (readyToShoot()) {
            manager.addEntity(new Bullet(xCor, yCor, angle, bulletTexture, bulletSpeed));
            reset();
        }
    }

    public void update() {
        timeSinceShot += Gdx.graphics.getDeltaTime();
    }

    protected boolean readyToShoot() {
        return timeSinceShot >= shootDelay;
    }

    protected void reset() {
        timeSinceShot = 0;
    }
}
