package com.totallynotacult.jam.weapons.machine_guns;

import com.totallynotacult.jam.TextureHolder;
import com.totallynotacult.jam.weapons.Weapon;

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