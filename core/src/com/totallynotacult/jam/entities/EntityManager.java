package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.totallynotacult.jam.PlayerCharacter;
import com.totallynotacult.jam.holders.TextureHolder;
import com.totallynotacult.jam.map.Tile;
import com.totallynotacult.jam.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private final List<Bullet> friendlyBullets = new ArrayList<>();
    private final List<Bullet> enemyBullets = new ArrayList<>();
    private final List<ShootingEntity> enemies = new ArrayList<>();
    private final List<Shadow> shadows = new ArrayList<>();
    private final List<Weapon> weapons = new ArrayList<>();
    private final SpriteBatch batch;
 //   private final List<TimeWarpers> timeWarper = new ArrayList<>();
    private PlayerCharacter character;

    public EntityManager(SpriteBatch batch) {
        this.batch = batch;
    }

    public void removeEnemies() {
        enemies.clear();
        enemyBullets.clear();
        weapons.removeIf(weaponSprite -> !(weaponSprite.getOwner() instanceof PlayerCharacter));
        shadows.removeIf(shadow -> !(shadow.getOwner() instanceof PlayerCharacter));
    }

    public void setCharacter(PlayerCharacter character) {
        addShadow(new Shadow(TextureHolder.SHADOW.getTexture(), character));
        this.character = character;
        addWeaponSprite(character.currentWeapon);
    }

    public boolean noMoreEnemies() {
        return enemies.isEmpty();
    }
    public List<ShootingEntity> getEnemies() {
        return enemies;
    }

    public void updateEntities(List<Tile> room, float deltaTime) {
        if (isMovementAllowed()) {
            enemyBullets.forEach(e -> e.update(room, deltaTime, this));
            friendlyBullets.forEach(e -> e.update(room, deltaTime, this));
        }


        character.update(room, deltaTime, this);
        weapons.forEach(weaponSprite -> weaponSprite.update(room, deltaTime, this));

        //character.currentWeaponSprite.update(room, deltaTime, this);
        enemies.forEach(enemy -> enemy.update(room, deltaTime, this));

        shadows.forEach(shadow -> shadow.update(room, deltaTime, this));


        friendlyBullets.removeIf(e -> outside(e) || e.collidesWithSomething(room, enemies));
        enemyBullets.removeIf(e -> outside(e) || e.collidesWithSomething(room, List.of(character)));

        shadows.removeIf(shadow -> shadow.getOwner().getHealth() <= 0);
        weapons.removeIf(weaponSprite -> weaponSprite.getOwner().getHealth() <= 0);
        enemies.removeIf(enemy -> enemy.getHealth() <= 0);

    }

    private boolean outside(Entity e) {
        return e.getX() > 1000 || e.getX() < -100 || e.getY() > 1000 || e.getY() < -100;
    }

    public void drawEntities() {
        shadows.forEach(shadow -> shadow.draw(batch));
        enemies.forEach(e -> e.draw(batch));

        character.draw(batch);

        weapons.forEach(weaponSprite -> weaponSprite.draw(batch));
        //character.currentWeaponSprite.draw(batch);
        enemyBullets.forEach(e -> e.draw(batch));
        friendlyBullets.forEach(e -> e.draw(batch));

    }

    public void addFriendlyBullet(Bullet bullet) {
        friendlyBullets.add(bullet);
    }

    public void addEnemyBullet(Bullet bullet) {
        enemyBullets.add(bullet);
    }

    public void addShadow(Shadow shadow) {shadows.add(shadow);}
    
    public void addWeaponSprite(Weapon weaponSprite) {weapons.add(weaponSprite);}

    public boolean roomClear() {
        return enemies.isEmpty();
    }

    public PlayerCharacter getCharacter() {
        return character;
    }

    public List<Bullet> getFriendlyBullets() {
        return friendlyBullets;
    }

    public List<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public void addEnemy(Enemy enemy) {
        addShadow(new Shadow(TextureHolder.SHADOW.getTexture(), enemy));
        enemies.add(enemy);
        addWeaponSprite(enemy.currentWeapon);
    }

    public boolean isMovementAllowed() {
        return character.isMovementAllowed();
    }

    public void removeAllBullets() {
        friendlyBullets.clear();
        enemyBullets.clear();
    }

    public void removeWeapon(Weapon weapon){
        weapons.remove(weapon);
    }
}
