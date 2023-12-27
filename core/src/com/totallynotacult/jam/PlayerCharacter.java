package com.totallynotacult.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.totallynotacult.jam.entities.EntityManager;
import com.totallynotacult.jam.entities.ShootingEntity;
import com.totallynotacult.jam.map.Tile;
import com.totallynotacult.jam.weapons.Pistol;
import com.totallynotacult.jam.weapons.QuickShooter;
import com.totallynotacult.jam.weapons.Weapon;

import java.util.List;

public class PlayerCharacter extends ShootingEntity {
    private float speed;
    private EntityManager entityManager;
    private Camera camera;
    private final DungeonScreen screen;
    private float facing;
    Texture currentFrame;

    public PlayerCharacter(EntityManager entityManager, Camera camera, DungeonScreen screen) {
        super(new Texture(Gdx.files.internal("police.png")));

        setBounds(100, 200, 16, 16);
        setOrigin(getWidth()/2,0);
        this.screen = screen;
        health = 15;
        maxHealth = 15;
        speed = 200;
        this.camera = camera;
        this.entityManager = entityManager;
        this.currentWeapon = new Pistol();
    }

    public void update(List<Tile> room, float deltaTime, EntityManager manager) {
        super.update(room, deltaTime, manager);
        performMovement(deltaTime, room);

        performShooting();
        checkBulletCollision(manager.getEnemyBullets());
    }

    public void performMovement(float deltaTime, List<Tile> room) {
        float horDir = getDir(Gdx.input.isKeyPressed(Input.Keys.D), Gdx.input.isKeyPressed(Input.Keys.A));
        float vertDir = getDir(Gdx.input.isKeyPressed(Input.Keys.W), Gdx.input.isKeyPressed(Input.Keys.S));

        Vector3 mx = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mx);
        facing = mx.x-(getX()+getOriginX());


            if (facing > 0) setScale(1,1);
                else setScale(-1,1);


        var localSpeed = speed * deltaTime / ((horDir != 0 && vertDir != 0) ? (float) Math.sqrt(2) : 1);

        moveWithCollision(localSpeed, room, horDir, true);
        moveWithCollision(localSpeed, room, vertDir, false);

        if (getX() < 0 && screen.canSwitchRoom()) {
            screen.changeRoom(0, -1);
            setX(256);
        } else if (getX() < 0) setX(0);

        if (getY() < 0 && screen.canSwitchRoom()) {
            screen.changeRoom(1, 0);
            setY(256);
        } else if (getY() < 0) setY(0);

        if (getX() > 256 && screen.canSwitchRoom()) {
            screen.changeRoom(0, 1);
            setX(0);
        } else if (getX() > 256) setX(256);

        if (getY() > 256 && screen.canSwitchRoom()) {
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
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 click = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(click);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && currentWeapon instanceof QuickShooter) {
                performQuickShooting(click.x, click.y, entityManager, true);
            } else performShooting(click.x, click.y, entityManager, true);
        }
    }
}