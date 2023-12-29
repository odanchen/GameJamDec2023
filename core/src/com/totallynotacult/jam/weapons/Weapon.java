package com.totallynotacult.jam.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totallynotacult.jam.entities.Bullet;
import com.totallynotacult.jam.entities.EntityManager;
import com.totallynotacult.jam.entities.ShootingEntity;
import com.totallynotacult.jam.weapons.machine_guns.MachineGun;
import com.totallynotacult.jam.weapons.pistols.Pistol;
import com.totallynotacult.jam.weapons.shotguns.Shotgun;


import java.util.Random;

public abstract class Weapon {
    protected int damage;
    protected Texture bulletTexture;
    protected float bulletSpeed;
    protected float shootDelay;
    protected float timeSinceShot = 0;
    protected int type;
    protected ShootingEntity owner;

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

    Texture sprite;
    TextureRegion[][] sprite_sheet;

    public Sprite bulletType() {
        int amountOfBulletTypes = 3;
        sprite = new Texture(Gdx.files.internal("weapon_bullet_sheet.png"));
        sprite_sheet = TextureRegion.split(sprite, sprite.getWidth() / amountOfBulletTypes, sprite.getHeight());

        return new Sprite(sprite_sheet[0][type]);

    }
    public void shoot(float xCor, float yCor, float angle, EntityManager manager, boolean isFriendly) {
        if (readyToShoot()) {
            if (isFriendly)
                manager.addFriendlyBullet(new Bullet(xCor, yCor, angle, bulletType(), bulletSpeed, damage));
            else manager.addEnemyBullet(new Bullet(xCor, yCor, angle, bulletType(), bulletSpeed, damage));
            reset();
        }
    }

    public int getType() {return type;}
    public void update() {
        timeSinceShot += Gdx.graphics.getDeltaTime();
    }

    protected boolean readyToShoot() {
        return timeSinceShot >= shootDelay;
    }

    protected void reset() {
        timeSinceShot = 0;
    }
}
