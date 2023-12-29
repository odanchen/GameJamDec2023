package com.totallynotacult.jam.weapons.pistols;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.totallynotacult.jam.holders.SoundHolder;
import com.totallynotacult.jam.holders.TextureHolder;
import com.totallynotacult.jam.entities.ShootingEntity;
import com.totallynotacult.jam.weapons.QuickShooter;

import java.util.Random;

public class EnemyPistol extends QuickShooter {
    public EnemyPistol(ShootingEntity owner) {
        super(owner);

        Random rand = new Random();
        this.damage = 1;
        this.bulletSpeed = 150;
        this.shootDelay = 2.0f;
        this.bulletTexture = TextureHolder.PISTOL_BULLET.getTexture();
        type = 0;
        set(new Sprite(sprite_sheet[0][this.type]));

        shootingSound = SoundHolder.GUN.getSound();
    }
}
