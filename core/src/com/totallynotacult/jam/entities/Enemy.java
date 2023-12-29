package com.totallynotacult.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totallynotacult.jam.map.Tile;
import com.totallynotacult.jam.weapons.Weapon;
import com.totallynotacult.jam.weapons.pistols.EnemyPistol;

import java.util.List;


public class Enemy extends ShootingEntity {
    private float angle;
    private float speed;
    private boolean rangedEnemy;
    private float agroRange;


    //Animations
    Texture sprites;
    TextureRegion[][] sprite_sheet;
    TextureRegion[] runCycleFrames;
    TextureRegion idle;
    Animation<TextureRegion> runCycleAni;

    public Enemy(int xCor, int yCor) {
        super(new Texture(Gdx.files.internal("enemy_sheet.png")));
        currentWeapon = new EnemyPistol(this);
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
        setOrigin(getWidth() / 2, 0);
        speed = 60;
        angle = (float) (Math.random() * 2 * Math.PI);
        maxHealth = 20;
        health = maxHealth;
        rangedEnemy = true;
        agroRange = 130;
    }

    int getDir(float localSpeed) {
        if (localSpeed == 0) return 0;
        if (localSpeed > 0) return 1;
        return -1;
    }

    @Override
    protected boolean collidesWithObstacle(List<Tile> room, EntityManager manager) {
        return collidesWithWall(room) || getBoundingRectangle().overlaps(manager.getCharacter().getBoundingRectangle()) ||
                manager.getEnemies().stream().anyMatch(enemy -> enemy != this && enemy.getBoundingRectangle().overlaps(getBoundingRectangle()));
    }

    @Override
    public void update(List<Tile> room, float deltaTime, EntityManager manager) {
        if (manager.isMovementAllowed()) {
            super.update(room, deltaTime, manager);

            float tY = manager.getCharacter().getY();
            float tX = manager.getCharacter().getX();

            float angle = (float) Math.atan2(tY - getY(), tX - getX());

            int xDir = getDir((float) (speed * deltaTime * Math.cos(angle)));
            int yDir = getDir((float) (speed * deltaTime * Math.sin(angle)));

            float dis = pointDistance(getX(),getY(),tX,tY);
            if (dis < agroRange) {

                if (rangedEnemy && dis >= 50) {
                    moveWithCollision((float) (speed * deltaTime * Math.cos(angle)), room, xDir, true, manager);
                    moveWithCollision((float) (speed * deltaTime * Math.sin(angle)), room, yDir, false, manager);
                }
                targetX = manager.getCharacter().getX();
                targetY = manager.getCharacter().getY();
                performShooting(manager, false);
            }
        }

        checkBulletCollision(manager.getFriendlyBullets());
        enemyAnimations();
    }

    public float pointDistance (float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt( (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
    }
    public void enemyAnimations() {
        TextureRegion currentWalkFrame;
        currentWalkFrame = runCycleAni.getKeyFrame(stateTime, true);
        currentSprite = new Sprite(idle);

        entityAnimations();
    }
}
