package com.totallynotacult.jam.weapons;

import com.totallynotacult.jam.TextureHolder;

import java.util.Random;

public class MachineGun extends Weapon {
    public MachineGun() {
        Random rand = new Random();
        this.damage = rand.nextInt(1) + 1;
        this.bulletSpeed = rand.nextInt(300) + 300;
        this.shootDelay = 0.33f;
        this.bulletTexture = TextureHolder.MACHINEGUN_BULLET.getTexture();
    }
}
