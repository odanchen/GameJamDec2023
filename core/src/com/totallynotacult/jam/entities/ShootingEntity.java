package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.Texture;

public abstract class ShootingEntity extends Entity {
    public ShootingEntity(Texture texture) {
        super(texture);
    }

    public ShootingEntity(Texture texture, float xPos, float yPos) {
        super(texture, xPos, yPos);
    }

    protected void performShooting(float xTarget, float yTarget, EntityManager entityManager) {
        float angle = (float) Math.atan2(yTarget - getY(), xTarget - getX());
        entityManager.addEntity(new Bullet(getX(), getY(), angle));
    }
}
