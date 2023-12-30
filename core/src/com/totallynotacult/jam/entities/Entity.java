package com.totallynotacult.jam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.totallynotacult.jam.holders.TextureHolder;
import com.totallynotacult.jam.map.Tile;
import com.totallynotacult.jam.map.Wall;

import java.util.List;
import java.util.stream.IntStream;

public abstract class Entity extends Sprite {

    protected Sprite currentSprite;
    protected float stateTime = 0f;
    protected Sprite hitbox = new Sprite(TextureHolder.HITBOX.getTexture());
    protected float hbw;
    protected float hbh;
    protected int hitFlash;
    public Entity(Texture texture) {
        super(texture);
        //hitbox.set(this);
    }

    public Entity(Texture texture, float xPos, float yPos) {
        super(texture);
        setPosition(xPos, yPos);

    }

    public Entity(TextureRegion texture, float xPos, float yPos) {
        super(texture);
        setOrigin(getWidth() / 2, getHeight() / 2);
        setPosition(xPos, yPos);

    }

    public Entity(Texture texture, float xPos, float yPos, float hbw, float hbh) {
        super(texture);
        setPosition(xPos, yPos);

        this.hbw = hbw;
        this.hbh = hbh;
    }


    public abstract void update(List<Tile> room, float deltaTime, EntityManager manager);

    protected boolean collidesWithWall(List<Tile> room) {

        float w = getWidth();
        float h = getHeight();
        float sX = getScaleX();
        float sY = getScaleY();
        float oX = getOriginX();
        setSize(11,1);
        setOrigin(5,getOriginY());
        setScale(1,1);
        Rectangle rect = getBoundingRectangle(); // ----< hitbox not working :(
        boolean b = room.stream().anyMatch(tile -> tile instanceof Wall && tile.getBoundingRectangle().overlaps(rect));
        setSize(w,h);
        setScale(sX,sY);
        setOrigin(oX,0);
        return b;
    }

    protected void entityAnimations() {

        if (hitFlash > 0) {
            currentSprite.setColor(Color.RED);
            hitFlash--;
        }
        else currentSprite.setColor(Color.WHITE);
        //Walk/RunCycle
        TextureRegion currentWalkFrame;
        stateTime += Gdx.graphics.getDeltaTime();
        currentSprite.setX(getX());
        currentSprite.setY(getY());
        currentSprite.setSize(getWidth(), getHeight());
        currentSprite.setOrigin(getOriginX(), getOriginY());
        currentSprite.setRotation(getRotation());
        currentSprite.setScale(getScaleX(), getScaleY());
       // currentSprite.setColor(getColor());
        set(currentSprite);

    }

    protected int collisionWithWeaponTile(List<Tile> room) {
        Rectangle rect = getBoundingRectangle();
        return IntStream.range(0, room.size())
                .filter(i -> room.get(i).weaponTile && room.get(i).getBoundingRectangle().overlaps(rect))
                .findFirst()
                .orElse(-1);
    }
}
