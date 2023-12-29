package com.totallynotacult.jam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.totallynotacult.jam.PlayerCharacter;
import com.totallynotacult.jam.map.Tile;

import java.util.List;

public class Shadow extends Entity {

    private final ShootingEntity owner;
    public Shadow(Texture texture, ShootingEntity owner) {
        super(texture);
        this.owner = owner;
        setAlpha(0.4f);
    }

    public ShootingEntity getOwner() {
        return owner;
    }

    @Override
    public void update(List<Tile> room, float deltaTime, EntityManager manager) {
        setX(owner.getX()+owner.getOriginX() - getWidth()/2);

        if (owner instanceof PlayerCharacter)
            setY(owner.getY()-2);
        else setY(owner.getY()-3);



    }
}

