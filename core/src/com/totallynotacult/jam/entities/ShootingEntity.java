package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.totallynotacult.jam.map.Tile;
import com.totallynotacult.jam.weapons.QuickShooter;
import com.totallynotacult.jam.weapons.Weapon;

import java.util.List;

public abstract class ShootingEntity extends Entity {
    protected Weapon currentWeapon;
    public ShootingEntity(Texture texture) {
        super(texture);
        currentWeapon = Weapon.getRandomWeapon();
    }

    public ShootingEntity(Texture texture, float xPos, float yPos) {
        super(texture, xPos, yPos);
        currentWeapon = Weapon.getRandomWeapon();
    }

    protected void performShooting(float xTarget, float yTarget, EntityManager entityManager) {
        float angle = (float) Math.atan2(yTarget - getY(), xTarget - getX());
        currentWeapon.shoot(getX(), getY(), angle, entityManager);
    }

    protected void performQuickShooting(float xTarget, float yTarget, EntityManager entityManager) {
        float angle = (float) Math.atan2(yTarget - getY(), xTarget - getX());
        if (currentWeapon instanceof QuickShooter) {
            ((QuickShooter) currentWeapon).quickShoot(getX(), getY(), angle, entityManager);
        }
    }
    @Override
    public void update(List<Tile> room, float deltaTime) {
        currentWeapon.update();
    }
}
