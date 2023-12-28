package com.totallynotacult.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totallynotacult.jam.map.Tile;

import java.util.List;

public class Enemy extends ShootingEntity {
    private float angle;
    private float speed;

    //Animations
    Texture sprites;
    TextureRegion[][] sprite_sheet;
    TextureRegion[] runCycleFrames;
    TextureRegion idle;
    Animation<TextureRegion> runCycleAni;

    public Enemy(int xCor, int yCor) {
        super(new Texture(Gdx.files.internal("enemy_sheet.png")));

        sprites = new Texture(Gdx.files.internal("enemy_sheet.png"));
        sprite_sheet = TextureRegion.split(sprites, sprites.getWidth() / 4, sprites.getHeight());
        idle = sprite_sheet[0][0];
        //Run Cycle
        runCycleFrames = new TextureRegion[3];
        runCycleFrames[0] = sprite_sheet[0][1];
        runCycleFrames[1] = sprite_sheet[0][2];
        runCycleFrames[2] = sprite_sheet[0][3];
        runCycleAni = new Animation<>(0.06f, runCycleFrames);

        setBounds(xCor, yCor, 16, 16);
        speed = 60;
        angle = (float) (Math.random() * 2 * Math.PI);
        maxHealth = 3;
        health = maxHealth;
    }

    int getDir(float localSpeed) {
        if (localSpeed == 0) return 0;
        if (localSpeed > 0) return 1;
        return -1;
    }

    @Override
    public void update(List<Tile> room, float deltaTime, EntityManager manager) {
        if(manager.isMovementAllowed()) {
            super.update(room, deltaTime, manager);
            int xDir = getDir((float) (speed * deltaTime * Math.cos(angle)));
            int yDir = getDir((float) (speed * deltaTime * Math.sin(angle)));

            for (int i = 0; i < Math.abs(speed * deltaTime * Math.cos(angle)); i++) {
                if (getX() > 0 && getX() < 216 && !collidesWithWall(room)) translateX(xDir);
                else {
                    angle = (float) (Math.PI - angle);
                    translateX(-xDir);
                    break;
                }
            }


            for (int i = 0; i < Math.abs(speed * deltaTime * Math.sin(angle)); i++) {
                if (getY() > 0 && getY() < 216 && !collidesWithWall(room)) translateY(yDir);
                else {
                    angle *= -1;
                    translateY(-yDir);
                    break;
                }
            }
            performShooting(manager.getCharacter().getX(), manager.getCharacter().getY(), manager, false);
        }
        checkBulletCollision(manager.getFriendlyBullets());
        enemyAnimations();
    }

    public void enemyAnimations() {
        TextureRegion currentWalkFrame;
        currentWalkFrame = runCycleAni.getKeyFrame(stateTime, true);
        currentSprite = new Sprite(idle);

        entityAnimations();
    }
}
