package com.totallynotacult.jam.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totallynotacult.jam.entities.Bullet;
import com.totallynotacult.jam.entities.Entity;
import com.totallynotacult.jam.entities.EntityManager;
import com.totallynotacult.jam.entities.ShootingEntity;
import com.totallynotacult.jam.map.Tile;
import com.totallynotacult.jam.weapons.machine_guns.MachineGun;
import com.totallynotacult.jam.weapons.pistols.Pistol;
import com.totallynotacult.jam.weapons.shotguns.Shotgun;


import java.util.List;
import java.util.Random;

public abstract class Weapon extends Entity {
    protected int damage;
    protected Texture bulletTexture;
    protected float bulletSpeed;
    protected float shootDelay;
    protected float timeSinceShot = 0;
    protected int type;
    protected ShootingEntity owner;
    public float kickbackX;
    public float kickbackY;
    public float kickbackMag;
    Texture sprite;
    protected TextureRegion[][] sprite_sheet;
    Texture sprites;
    protected Sound shootingSound;

    public Weapon(ShootingEntity owner) {
        super(new Texture(Gdx.files.internal("weapon_sheet.png")));
        this.owner = owner;

        sprites = new Texture(Gdx.files.internal("weapon_sheet.png"));
        sprite_sheet = TextureRegion.split(sprites, sprites.getWidth() / 4, sprites.getHeight());

        set(new Sprite(sprite_sheet[0][this.type]));
        kickbackX = 0;
        kickbackY = 0;
        kickbackMag = 0;
        setOrigin(4, 4);
    }

    @Override
    public void update(List<Tile> room, float deltaTime, EntityManager manager) {
        kickbackX = (float) (kickbackMag * Math.cos(owner.getAimAngle()));
        kickbackY = (float) (kickbackMag * Math.sin(owner.getAimAngle()));
        if (kickbackMag > 0) kickbackMag -= 1;
        setX(owner.getX() + owner.getOriginX() / 2 + 2 * owner.getFacing() - kickbackX);
        setY(owner.getY() - 3 - kickbackY);
        float angle = owner.getAimAngle();
        setRotation((float) (angle * 180 / Math.PI));
        if (angle >= Math.PI / 2 && angle <= Math.PI * 3 / 2 || angle <= -Math.PI / 2) setScale(1, -1);
        else setScale(1, 1);
    }

    public static Weapon getRandomWeapon(ShootingEntity owner) {
        Random rand = new Random();
        int weapon = rand.nextInt(3);
        switch (weapon) {
            case 0:
                return new Pistol(owner);
            case 1:
                return new Shotgun(owner);
            case 2:
                return new MachineGun(owner);
        }
        return null;
    }

    public Sprite bulletType() {

        int amountOfBulletTypes = 3;
        sprite = new Texture(Gdx.files.internal("weapon_bullet_sheet.png"));
        sprite_sheet = TextureRegion.split(sprite, sprite.getWidth() / amountOfBulletTypes, sprite.getHeight());

        if (type == -1)
            return new Sprite(sprite_sheet[0][amountOfBulletTypes]);
        else
            return new Sprite(sprite_sheet[0][type]);

    }

    public void shoot(float angle, EntityManager manager, boolean isFriendly) {
        if (type == -1) return;
        float xx = getX() + getOriginX() + (float) (Math.cos(angle) * 6) - 5;
        float yy = getY() + getOriginY() + (float) (Math.sin(angle) * 8) - 4;

        if (readyToShoot()) {
            kickbackMag = 5;
            var bullet = new Bullet(xx, yy, angle, bulletType(), bulletSpeed, damage);
            if (isFriendly)
                manager.addFriendlyBullet(bullet);
            else manager.addEnemyBullet(bullet);
            reset();
            shootingSound.play();
        }
    }

    public int getType() {
        return type;
    }

    public void update() {
        timeSinceShot += Gdx.graphics.getDeltaTime();
    }

    protected boolean readyToShoot() {
        return timeSinceShot >= shootDelay;
    }

    protected void reset() {
        timeSinceShot = 0;
    }

    public ShootingEntity getOwner() {
        return owner;
    }

    protected void produceBullet(float angle, EntityManager manager, boolean isFriendly) {
        kickbackMag = 5;
        float xx = getX() + getOriginX() + (float) (Math.cos(angle) * 6) + 1;
        float yy = getY() + getOriginY() + (float) (Math.sin(angle) * 8) - 4;

        var bullet = new Bullet(xx, yy, angle, bulletType(), bulletSpeed, damage);
        if (isFriendly)
            manager.addFriendlyBullet(bullet);
        else manager.addEnemyBullet(bullet);
    }
}
