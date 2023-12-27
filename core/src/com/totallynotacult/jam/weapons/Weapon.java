package com.totallynotacult.jam.weapons;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public abstract class Weapon {
    public int damage;
    public Texture bulletTexture;
    public float bulletSpeed;
    public float fireRate;

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

}
