package com.totallynotacult.jam.weapons.pistols;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.totallynotacult.jam.TextureHolder;
import com.totallynotacult.jam.entities.ShootingEntity;
import com.totallynotacult.jam.weapons.QuickShooter;

import java.util.Random;

public class Pistol extends QuickShooter {
    public Pistol(ShootingEntity owner) {
        super(owner);

        Random rand = new Random();
        this.damage = rand.nextInt(3) + 1;
        this.bulletSpeed = rand.nextInt(200) + 200;
        this.shootDelay = rand.nextInt(1) + 0.5f;
        this.bulletTexture = TextureHolder.PISTOL_BULLET.getTexture();
        type = 0;
        set(new Sprite(sprite_sheet[0][this.type]));
    }
}
