package com.totallynotacult.jam.weapons.machine_guns;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.totallynotacult.jam.holders.SoundHolder;
import com.totallynotacult.jam.holders.TextureHolder;
import com.totallynotacult.jam.entities.ShootingEntity;
import com.totallynotacult.jam.weapons.Weapon;

import java.util.Random;

public class RocketLauncher extends Weapon {
    public RocketLauncher(ShootingEntity owner) {
        super(owner);
        Random rand = new Random();
        this.damage = 20;
        this.bulletSpeed = 800;
        this.shootDelay = 1.2f;
        this.bulletTexture = TextureHolder.MACHINEGUN_BULLET.getTexture();
        type = 3;
        set(new Sprite(sprite_sheet[0][this.type]));

        shootingSound = SoundHolder.GUN.getSound();
    }
}
