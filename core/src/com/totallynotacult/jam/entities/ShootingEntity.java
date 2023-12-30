package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.totallynotacult.jam.DungeonScreen;
import com.totallynotacult.jam.PlayerCharacter;
import com.totallynotacult.jam.holders.SoundHolder;
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
    protected float hitboxWidth;
    protected float hitboxHeight;
    protected boolean isSuperCharged;



    public boolean getIsSuperCharged() {return isSuperCharged;}
    public void setIsSuperCharged(boolean b) {isSuperCharged = b;}

    public ShootingEntity(Texture texture) {
        super(texture);


    }

    public ShootingEntity(Texture texture, float xPos, float yPos) {
        super(texture, xPos, yPos);
    }

    public ShootingEntity(Texture texture, float xPos, float yPos, float hbw, float hbh) {
        super(texture, xPos, yPos, hbw, hbh);
        hitboxWidth = hbw;
        hitboxHeight = hbh;
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
            ((QuickShooter) currentWeapon).quickShoot(angle, entityManager, isFriendly);
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
        if (currentWeapon != null) currentWeapon.update();
        //hitbox.setBounds(getX()+getOriginX(),getY(),hitboxWidth,hitboxHeight);

        if (facing > 0) setScale(1, 1);
        else setScale(-1, 1);
        hitbox.set(this);
    }

    protected abstract boolean collidesWithObstacle(List<Tile> room, EntityManager manager);

    public int getHealth() {
        return health;
    }

    public void receiveDamage(int damage) {

        if (this instanceof PlayerCharacter) {
            health -= damage;
            SoundHolder.DAMAGE.getSound().play();
            hitFlash = 3;
        } else {
            if (!this.getIsSuperCharged() || DungeonScreen.currentCharacter.getIsSuperCharged() && this.getIsSuperCharged()) {
                health -= damage;
                SoundHolder.DAMAGE.getSound().play();
                hitFlash = 3;
            }
        }

    }

    public int getMaxHealth() {
        return maxHealth;
    }

    protected void moveWithCollision(float localSpeed, List<Tile> room, float dir, boolean isHorizontal, EntityManager manager) {
        if (dir == 0) {
            return;
        }


        for (int i = 0; i < Math.abs(localSpeed); i++) {
            if (isHorizontal) translateX(dir); else translateY(dir);
            if (collidesWithObstacle(room, manager)) {
                if (isHorizontal) {
                    translateX(-dir);
                }
                else {
                    translateY(-dir);
                }
                return;
            }

            int weaponTile = collisionWithWeaponTile(room);
            if (weaponTile != -1 && this.equals(manager.getCharacter())) {
                room.get(weaponTile).weaponTile = false;
                manager.removeWeapon(currentWeapon);
                currentWeapon = Weapon.getRandomWeapon(this);
                manager.addWeaponSprite(currentWeapon);
                SoundHolder.AMMO_PICKUP.getSound().play();
                //manager.getCharacter().currentWeaponSprite = new WeaponSprite(currentWeapon, this);
                //weaponSprites.remove(currentWeapon);
            }
        }
    }
}
