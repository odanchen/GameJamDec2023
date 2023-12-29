package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.totallynotacult.jam.map.Tile;
import com.totallynotacult.jam.weapons.QuickShooter;
import com.totallynotacult.jam.weapons.Weapon;

import java.util.List;

public abstract class ShootingEntity extends Entity {

    protected int health;
    protected int maxHealth;
    protected Weapon currentWeapon;
    protected float targetX;
    protected float targetY;
    protected float facing;


    public ShootingEntity(Texture texture) {
        super(texture);
        currentWeapon = Weapon.getRandomWeapon(this);

    }

    public ShootingEntity(Texture texture, float xPos, float yPos) {
        super(texture, xPos, yPos);
        currentWeapon = Weapon.getRandomWeapon(this);
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public float getAimAngle() {
        return (float) Math.atan2(targetY - getY(), targetX - getX());
    }

    public int getFacing() {
        return (int) (facing / Math.abs(facing));
    }

    protected void performShooting(EntityManager entityManager, boolean isFriendly) {
        float angle = getAimAngle();
        currentWeapon.shoot(angle, entityManager, isFriendly);
    }


    protected void performQuickShooting(EntityManager entityManager, boolean isFriendly) {
        float angle = getAimAngle();
        if (currentWeapon instanceof QuickShooter) {
            ((QuickShooter) currentWeapon).quickShoot(getX(), getY(), angle, entityManager, isFriendly);
        }
    }

    protected void checkBulletCollision(List<Bullet> enemyBullets) {
        enemyBullets.forEach(bullet -> {
            if (getBoundingRectangle().overlaps(bullet.getBoundingRectangle())) {
                receiveDamage(bullet.getDamage());
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

    public int getMaxHealth() {
        return maxHealth;
    }

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
            if (weaponTile != -1 && this.equals(manager.getCharacter())) {
                room.get(weaponTile).weaponTile = false;
                manager.removeWeapon(currentWeapon);
                currentWeapon = Weapon.getRandomWeapon(this);
                manager.addWeaponSprite(currentWeapon);
                //manager.getCharacter().currentWeaponSprite = new WeaponSprite(currentWeapon, this);
                //weaponSprites.remove(currentWeapon);
            }
        }
    }
}
