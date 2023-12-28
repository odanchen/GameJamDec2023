package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.totallynotacult.jam.map.Tile;

import java.util.List;

public class WeaponSprite extends Entity {
    private final ShootingEntity owner;
    public WeaponSprite(Texture texture, ShootingEntity owner) {
        super(texture);
        this.owner = owner;
        setAlpha(0.4f);
    }

    public ShootingEntity getOwner() {
        return owner;
    }

    @Override
    public void update(List<Tile> room, float deltaTime, EntityManager manager) {

        //
        setX(owner.getX()+owner.getOriginX() - getWidth()/2);
        setY(owner.getY()-3);


    }
}
