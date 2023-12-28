package com.totallynotacult.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.totallynotacult.jam.map.Tile;

import java.util.List;

public class WeaponSprite extends Entity {
    private final ShootingEntity owner;
    private final int type;

    Texture sprites;
    TextureRegion[][] sprite_sheet;

    public WeaponSprite(int type, ShootingEntity owner) {
        super(new Texture(Gdx.files.internal("weapon_sheet.png")));
        this.owner = owner;
        this.type = type;

        sprites = new Texture(Gdx.files.internal("weapon_sheet.png"));
        sprite_sheet = TextureRegion.split(sprites, sprites.getWidth() / 3, sprites.getHeight());

        set( new Sprite(sprite_sheet[0][type]));

        setOrigin(4,4);
    }

    public ShootingEntity getOwner() {
        return owner;
    }

    @Override
    public void update(List<Tile> room, float deltaTime, EntityManager manager) {

        setX(owner.getX()+owner.getOriginX()/2 + 2*owner.getFacing());
        setY(owner.getY()-3);
        float angle = owner.getAimAngle();
        setRotation((float)(angle * 180 / Math.PI));
        if (angle > Math.PI/2 && angle < Math.PI*3/2 || angle < -Math.PI/2) setScale(1,-1);
            else setScale(1,1);


    }
}
