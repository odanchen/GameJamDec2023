package com.totallynotacult.jam.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.totallynotacult.jam.entities.Bullet;
import com.totallynotacult.jam.entities.EntityManager;
import com.totallynotacult.jam.weapons.machine_guns.MachineGun;
import com.totallynotacult.jam.weapons.shotguns.Shotgun;

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

    public void shoot(float xCor, float yCor, float angle, EntityManager manager, boolean isFriendly) {
        if (readyToShoot()) {
            if (isFriendly)
                manager.addFriendlyBullet(new Bullet(xCor, yCor, angle, bulletTexture, bulletSpeed, damage));
            else manager.addEnemyBullet(new Bullet(xCor, yCor, angle, bulletTexture, bulletSpeed, damage));
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
