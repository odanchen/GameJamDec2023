package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totallynotacult.jam.map.Tile;
import com.totallynotacult.jam.weapons.QuickShooter;
import com.totallynotacult.jam.weapons.Weapon;

import java.util.List;

public abstract class ShootingEntity extends Entity {

    protected int health;
    protected int maxHealth;
    protected Weapon currentWeapon;

    public ShootingEntity(Texture texture) {
        super(texture);
        currentWeapon = Weapon.getRandomWeapon();
    }

    public ShootingEntity(Texture texture, float xPos, float yPos) {
        super(texture, xPos, yPos);
        currentWeapon = Weapon.getRandomWeapon();
    }

    public ShootingEntity(TextureRegion texture, float xPos, float yPos) {
        super(texture, xPos, yPos);
        currentWeapon = Weapon.getRandomWeapon();
    }

    protected void performShooting(float xTarget, float yTarget, EntityManager entityManager, boolean isFriendly) {
        float angle = (float) Math.atan2(yTarget - getY(), xTarget - getX());
        currentWeapon.shoot(getX(), getY(), angle, entityManager, isFriendly);
    }

    protected void performQuickShooting(float xTarget, float yTarget, EntityManager entityManager, boolean isFriendly) {
        float angle = (float) Math.atan2(yTarget - getY(), xTarget - getX());
        if (currentWeapon instanceof QuickShooter) {
            ((QuickShooter) currentWeapon).quickShoot(getX(), getY(), angle, entityManager, isFriendly);
        }
    }

    protected void checkBulletCollision(List<Bullet> enemyBullets) {
        enemyBullets.forEach(bullet -> {
            if (getBoundingRectangle().overlaps(bullet.getBoundingRectangle())) {
                receiveDamage(bullet.getDamage());
                System.out.println("HIT");
            }
        });
    }

    @Override
    public void update(List<Tile> room, float deltaTime, EntityManager manager) {
        currentWeapon.update();
    }

    protected abstract boolean collidesWithObstacle(List<Tile> room, EntityManager manager);

    public int getHealth() {
        return health;
    }

    public void receiveDamage(int damage) {
        health -= damage;
    }

    public int getMaxHealth( ){return maxHealth;}

    protected void moveWithCollision(float localSpeed, List<Tile> room, float dir, boolean isHorizontal, EntityManager manager) {
        if (dir == 0) {
            return;
        }
        for (int i = 0; i < Math.abs(localSpeed); i++) {
            if (isHorizontal) translateX(dir);
            else translateY(dir);
            if (collidesWithObstacle(room, manager)) {
                if (isHorizontal) translateX(-dir);
                else translateY(-dir);
                return;
            }

            int weaponTile = collisionWithWeaponTile(room);
            if (weaponTile != -1) {
                room.get(weaponTile).weaponTile = false;
                currentWeapon = Weapon.getRandomWeapon();
            }
        }
    }
}
