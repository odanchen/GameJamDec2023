package com.totallynotacult.jam.weapons;

import com.totallynotacult.jam.TextureHolder;

import java.util.Random;

public class Pistol extends QuickShooter {
    public Pistol() {
        Random rand = new Random();
        this.damage = rand.nextInt(3) + 1;
        this.bulletSpeed = rand.nextInt(200) + 200;
        this.shootDelay = rand.nextInt(1) + 0.5f;
        this.bulletTexture = TextureHolder.PISTOL_BULLET.getTexture();
    }
}
