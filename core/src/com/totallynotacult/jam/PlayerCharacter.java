package com.totallynotacult.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.totallynotacult.jam.entities.Bullet;
import com.totallynotacult.jam.entities.Entity;
import com.totallynotacult.jam.entities.EntityManager;
import com.totallynotacult.jam.entities.ShootingEntity;
import com.totallynotacult.jam.map.Tile;
import com.totallynotacult.jam.map.Wall;
import com.totallynotacult.jam.weapons.MachineGun;
import com.totallynotacult.jam.weapons.Pistol;
import com.totallynotacult.jam.weapons.Shotgun;
import com.totallynotacult.jam.weapons.Weapon;

import javax.crypto.Mac;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PlayerCharacter extends ShootingEntity {
    private int health;
    private int maxHealth;
    private float speed;
    private EntityManager entityManager;
    private Camera camera;
    private final DungeonScreen screen;
    private Weapon currentWeapon;
    private long timeSinceLastShot = System.currentTimeMillis();


    public PlayerCharacter(EntityManager entityManager, Camera camera, DungeonScreen screen) {
        super(new Texture(Gdx.files.internal("police.png")));
        setBounds(100, 200, 16, 16);

        this.screen = screen;
        health = 6;
        maxHealth = 6;
        speed = 200;
        this.camera = camera;
        this.entityManager = entityManager;
        this.currentWeapon = new Pistol();
    }

    public void update(List<Tile> room, float deltaTime) {
        performMovement(deltaTime, room);
        performShooting();
    }

    public void performMovement(float deltaTime, List<Tile> room) {
        float horDir = getDir(Gdx.input.isKeyPressed(Input.Keys.D), Gdx.input.isKeyPressed(Input.Keys.A));
        float vertDir = getDir(Gdx.input.isKeyPressed(Input.Keys.W), Gdx.input.isKeyPressed(Input.Keys.S));

        var localSpeed = speed * deltaTime / ((horDir != 0 && vertDir != 0) ? (float) Math.sqrt(2) : 1);

        moveWithCollision(localSpeed, room, horDir, true);
        moveWithCollision(localSpeed, room, vertDir, false);

        if (getX() < 0 && screen.roomExists(0, -1)) {
            screen.changeRoom(0, -1);
            setX(256);
        } else if (getX() < 0) setX(0);

        if (getY() < 0 && screen.roomExists(1, 0)) {
            screen.changeRoom(1, 0);
            setY(256);
        } else if (getY() < 0) setY(0);

        if (getX() > 256 && screen.roomExists(0, 1)) {
            screen.changeRoom(0, 1);
            setX(0);
        } else if (getX() > 256) setX(256);

        if (getY() > 256 && screen.roomExists(-1, 0)) {
            screen.changeRoom(-1, 0);
            setY(0);
        } else if (getY() > 256) setY(256);
    }

    private void moveWithCollision(float localSpeed, List<Tile> room, float dir, boolean isHorizontal) {
        if (dir == 0) return;
        for (int i = 0; i < localSpeed; i++) {
            if (isHorizontal) translateX(dir);
            else translateY(dir);
            if (collidesWithWall(room)) {
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



    private int getDir(boolean forward, boolean backward) {
        if (forward == backward) return 0;
        if (forward) return 1;
        return -1;
    }

    private void performShooting() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            if ((System.currentTimeMillis() - timeSinceLastShot) / 1000f < 1/currentWeapon.fireRate) {
                return;
            }
            timeSinceLastShot = System.currentTimeMillis();

            Vector3 click = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(click);
            System.out.printf("x: %f, y: %f%n", click.x, click.y);
            performShooting(click.x, click.y, entityManager);
            float angle = (float) Math.atan2(click.y - getY(), click.x - getX());

            if (currentWeapon instanceof Shotgun) {
                for (int i = 0; i < ((Shotgun) currentWeapon).numBullets; i++) {
                    float spread = (float) Math.random() * ((Shotgun) currentWeapon).spread - ((Shotgun) currentWeapon).spread / 2;
                    entityManager.addEntity(new Bullet(getX(), getY(), angle + spread, currentWeapon));
                }
                return;
            }

            entityManager.addEntity(new Bullet(getX(), getY(), angle, currentWeapon));
        }
    }
}