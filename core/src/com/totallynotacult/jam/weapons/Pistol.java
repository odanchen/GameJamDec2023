package com.totallynotacult.jam.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class Pistol extends QuickShooter {
    public Pistol() {
        Random rand = new Random();
        this.damage = rand.nextInt(3) + 1;
        this.bulletSpeed = rand.nextInt(200) + 200;
        this.shootDelay = rand.nextInt(1) + 0.5f;
        this.bulletTexture = new Texture(Gdx.files.internal("bullets/pistol_bullet.png"));
    }
}
