package com.totallynotacult.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.totallynotacult.jam.map.Tile;

import java.util.List;

public class Enemy extends ShootingEntity {
    private float angle;
    private float speed;
    private float lastShot;

    private float playerX;
    private float playerY;
    private EntityManager entityManager;

    public Enemy(int xCor, int yCor) {
        super(new Texture(Gdx.files.internal("enemy.png")));
        setBounds(xCor, yCor, 32, 32);
        speed = 100;
        angle = (float) (Math.random() * 2 * Math.PI);

        lastShot = 0;
    }

    int getDir(float localSpeed) {
        if (localSpeed == 0) return 0;
        if (localSpeed > 0) return 1;
        return -1;
    }

    public void temp(float playerX, float playerY, EntityManager entityManager) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.entityManager = entityManager;
    }

    @Override
    public void update(List<Tile> room, float deltaTime) {
        super.update(room, deltaTime);

        int xDir = getDir((float) (speed * deltaTime * Math.cos(angle)));
        int yDir = getDir((float) (speed * deltaTime * Math.sin(angle)));

        for (int i = 0; i < Math.abs(speed * deltaTime * Math.cos(angle)); i++) {
            if (getX() > 0 && getX() < 505 && !collidesWithWall(room)) translateX(xDir);
            else {
                angle = (float) (Math.PI - angle);
                System.out.println("x flip");
                translateX(-xDir);
                break;
            }
        }


        for (int i = 0; i < Math.abs(speed * deltaTime * Math.sin(angle)); i++) {
            if (getY() > 0 && getY() < 512 && !collidesWithWall(room)) translateY(yDir);
            else {
                angle *= -1;
                System.out.println("y flip");
                translateY(-yDir);
                break;
            }
        }

        performShooting(playerX, playerY, entityManager);
    }
}
