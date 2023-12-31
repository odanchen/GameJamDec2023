package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.totallynotacult.jam.map.Tile;

import java.util.List;

public class Bullet extends Entity {
    private final float speed;
    private final float angle;
    private final int damage;

    public Bullet(float xCor, float yCor, float angle, Sprite texture, float speed, int damage) {
        super(texture, xCor, yCor);
        //translateX(getOriginX());
        //translateY(getOriginY());

        set(texture);
        setSize(16, 16);
        setOrigin(getWidth() / 2, getHeight() / 2);

        setPosition(xCor, yCor);

        //this.setRotation((float) Math.toDegrees(angle));
        this.speed = speed;
        this.angle = angle;
        this.damage = damage;
    }

    @Override
    public void update(List<Tile> room, float deltaTime, EntityManager manager) {
        translateX((float) (speed * Math.cos(angle) * deltaTime));
        translateY((float) (speed * Math.sin(angle) * deltaTime));
    }

    public int getDamage() {
        return damage;
    }

    public boolean collidesWithSomething(List<Tile> room, List<ShootingEntity> entities) {
        return collidesWithWall(room) || entities.stream().anyMatch(e -> e.getBoundingRectangle().overlaps(getBoundingRectangle()));
    }
}
