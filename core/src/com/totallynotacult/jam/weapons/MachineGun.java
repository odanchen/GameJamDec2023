package com.totallynotacult.jam.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.totallynotacult.jam.entities.Bullet;
import com.totallynotacult.jam.entities.EntityManager;

import java.util.Random;

public class MachineGun extends Weapon {
    public MachineGun() {
        Random rand = new Random();
        this.damage = rand.nextInt(1) + 1;
        this.bulletSpeed = rand.nextInt(300) + 300;
        this.shootDelay = 0.33f;
        this.bulletTexture = new Texture(Gdx.files.internal("bullets/machinegun_bullet.png"));
    }
}
