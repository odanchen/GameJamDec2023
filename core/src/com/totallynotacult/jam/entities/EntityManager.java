package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.totallynotacult.jam.PlayerCharacter;
import com.totallynotacult.jam.map.Tile;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private final ArrayList<Bullet> friendlyBullets = new ArrayList<>();
    private final ArrayList<Bullet> enemyBullets = new ArrayList<>();
    private final ArrayList<ShootingEntity> enemies = new ArrayList<>();
    private final SpriteBatch batch;
    private PlayerCharacter character;

    public EntityManager(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setCharacter(PlayerCharacter character) {
        this.character = character;
    }

    public void updateEntities(List<Tile> room, float deltaTime) {
        if (isMovementAllowed()) {
            enemyBullets.forEach(e -> e.update(room, deltaTime, this));
        }
        friendlyBullets.forEach(e -> e.update(room, deltaTime, this));

        enemies.forEach(enemy -> enemy.update(room, deltaTime, this));
        friendlyBullets.removeIf(e -> outside(e) || e.collidesWithSomething(room, enemies));
        enemyBullets.removeIf(e -> outside(e) || e.collidesWithSomething(room, List.of(character)));

        enemies.removeIf(enemy -> enemy.getHealth() <= 0);
    }

    private boolean outside(Entity e) {
        return e.getX() > 1000 || e.getX() < -100 || e.getY() > 1000 || e.getY() < -100;
    }

    public void drawEntities() {
        friendlyBullets.forEach(e -> e.draw(batch));
        enemyBullets.forEach(e -> e.draw(batch));
        enemies.forEach(e -> e.draw(batch));
    }

    public void addFriendlyBullet(Bullet bullet) {
        friendlyBullets.add(bullet);
    }

    public void addEnemyBullet(Bullet bullet) {
        enemyBullets.add(bullet);
    }


    public boolean roomClear() {
        return enemies.isEmpty();
    }

    public PlayerCharacter getCharacter() {
        return character;
    }

    public ArrayList<Bullet> getFriendlyBullets() {
        return friendlyBullets;
    }

    public ArrayList<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public boolean isMovementAllowed() {
        return character.isMovementAllowed();
    }

    public void removeAllBullets() {
        friendlyBullets.clear();
        enemyBullets.clear();
    }
}
