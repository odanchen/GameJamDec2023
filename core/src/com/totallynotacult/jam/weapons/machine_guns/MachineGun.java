package com.totallynotacult.jam.weapons.machine_guns;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.totallynotacult.jam.holders.SoundHolder;
import com.totallynotacult.jam.holders.TextureHolder;
import com.totallynotacult.jam.entities.ShootingEntity;
import com.totallynotacult.jam.weapons.Weapon;

import java.util.Random;

public class MachineGun extends Weapon {
    public MachineGun(ShootingEntity owner) {
        super(owner);
        Random rand = new Random();
        this.damage = rand.nextInt(1) + 1;
        this.bulletSpeed = rand.nextInt(300) + 300;
        this.shootDelay = 0.1f;
        this.bulletTexture = TextureHolder.MACHINEGUN_BULLET.getTexture();
        type = 2;
        set(new Sprite(sprite_sheet[0][this.type]));

        shootingSound = SoundHolder.GUN.getSound();
    }
}
