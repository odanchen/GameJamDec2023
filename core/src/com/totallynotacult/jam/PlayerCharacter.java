package com.totallynotacult.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.totallynotacult.jam.entities.EntityManager;
import com.totallynotacult.jam.entities.ShootingEntity;
import com.totallynotacult.jam.holders.SoundHolder;
import com.totallynotacult.jam.map.BackwardTravelTile;
import com.totallynotacult.jam.map.ForwardTravelTile;
import com.totallynotacult.jam.map.SuperChargeTile;
import com.totallynotacult.jam.map.Tile;
import com.totallynotacult.jam.weapons.QuickShooter;
import com.totallynotacult.jam.weapons.pistols.NoWeapon;

import java.util.List;
import java.util.Random;

public class PlayerCharacter extends ShootingEntity {
    private float speed;
    private EntityManager entityManager;
    private Camera camera;
    public final DungeonScreen screen;
    private float timeStopCoolDown = 10;
    private float timeStopDuration = 3;
    private float timeSinceLastStop = 10;
    private float timeStopLeft = 0;
    private float stateTime = 0f;
    private boolean isMoving = false;



    //Animations
    Texture sprites;
    TextureRegion[][] sprite_sheet;
    TextureRegion[] runCycleFrames;
    TextureRegion idle;
    Animation<TextureRegion> runCycleAni;


    public PlayerCharacter(EntityManager entityManager, Camera camera, DungeonScreen screen) {
        super(new Texture(Gdx.files.internal("sprite_player_sheet.png")),0,0,10,5);
        sprites = new Texture(Gdx.files.internal("sprite_player_sheet.png"));
        sprite_sheet = TextureRegion.split(sprites, sprites.getWidth() / 4, sprites.getHeight());
        idle = sprite_sheet[0][0];
        //Run Cycle
        runCycleFrames = new TextureRegion[3];
        runCycleFrames[0] = sprite_sheet[0][1];
        runCycleFrames[1] = sprite_sheet[0][2];
        runCycleFrames[2] = sprite_sheet[0][3];
        runCycleAni = new Animation<>(0.08f, runCycleFrames);

        setBounds(100, 200, 16, 16);
        setOrigin(getWidth() / 2, 0);
        this.screen = screen;
        health = 5;
        maxHealth = health;
        speed = 120;
        this.camera = camera;
        this.entityManager = entityManager;
        this.currentWeapon = new NoWeapon(this);
        isSuperCharged = false;
        //currentWeaponSprite = new WeaponSprite(this.currentWeapon,this);
    }

    public void update(List<Tile> room, float deltaTime, EntityManager manager) {
        super.update(room, deltaTime, manager);
        performMovement(deltaTime, room);

        timeStopLeft -= Gdx.graphics.getDeltaTime();
        timeSinceLastStop += Gdx.graphics.getDeltaTime();

        Vector3 mPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        if (!isMoving) setPosition((float) Math.floor(getX()), (float) Math.floor(getY()));
        camera.unproject(mPos);
        targetX = mPos.x;
        targetY = mPos.y;
        performShooting();
        checkBulletCollision(manager.getEnemyBullets());
        timeStopAction();
        playerAnimations();


        //Interactable Tile Collisions
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            if (room.stream().anyMatch(tile -> tile instanceof BackwardTravelTile && tile.getBoundingRectangle().overlaps(getBoundingRectangle()))) {
                DungeonScreen.currentTimeLine--;
                screen.fadeToBlack();
                //screen.regenerateRoom();

                //setPosition(tile.getPos);
            } else if (room.stream().anyMatch(tile -> tile instanceof ForwardTravelTile && tile.getBoundingRectangle().overlaps(getBoundingRectangle())))  {
                DungeonScreen.currentTimeLine++;
                screen.fadeToBlack();
                //screen.regenerateRoom();
            } else if (room.stream().anyMatch(tile -> tile instanceof SuperChargeTile && tile.getBoundingRectangle().overlaps(getBoundingRectangle())))  {
                isSuperCharged = true;
            }

        }
    }



    public void playerAnimations() {

        //Walk/RunCycle
        TextureRegion currentWalkFrame;
        stateTime += Gdx.graphics.getDeltaTime();
        currentWalkFrame = runCycleAni.getKeyFrame(stateTime, true);
        currentSprite = isMoving ? new Sprite(currentWalkFrame) : new Sprite(idle);

        entityAnimations();
    }

    public boolean isMovementAllowed() {
        return timeStopLeft <= 0;
    }

    private void timeStopAction() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && timeSinceLastStop >= timeStopCoolDown) {
            timeStopLeft = timeStopDuration;
            timeSinceLastStop = -timeStopDuration;
            (new Random().nextInt(2) == 1 ? SoundHolder.TIMESTOP_SOUND : SoundHolder.ZAWARUDO).getSound().play();
        }
    }

    @Override
    protected boolean collidesWithObstacle(List<Tile> room, EntityManager manager) {

        //return collidesWithWall(room) || manager.getEnemies().stream().anyMatch(enemy -> getBoundingRectangle().overlaps(enemy.getBoundingRectangle()));
        return collidesWithWall(room);
    }
    public float pointDistance (float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt( (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
    }
    public void performMovement(float deltaTime, List<Tile> room) {

        if (pointDistance(getX(),getY(),screen.cam.x,screen.cam.y) > 60) return;
        float horDir = getDir(Gdx.input.isKeyPressed(Input.Keys.D), Gdx.input.isKeyPressed(Input.Keys.A));
        float vertDir = getDir(Gdx.input.isKeyPressed(Input.Keys.W), Gdx.input.isKeyPressed(Input.Keys.S));

        isMoving = horDir != 0 || vertDir != 0;
        Vector3 mx = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mx);
        facing = mx.x - (getX() + getOriginX());





        float localSpeed = speed * deltaTime / ((horDir != 0 && vertDir != 0) ? (float) Math.sqrt(2) : 1);

        moveWithCollision(localSpeed, room, horDir, true, entityManager);
        moveWithCollision(localSpeed, room, vertDir, false, entityManager);

        if (getX() < 0 && screen.canSwitchRoom()) {
            setX(247);
            screen.cam.switchRoom(true,false);
        } else if (getX() < 0) setX(0);

        if (getY() < 0 && screen.canSwitchRoom()) {

            setY(247);
            screen.cam.switchRoom(false,false);
        } else if (getY() < 0) setY(0);

        if (getX() > 247 && screen.canSwitchRoom()) {
            setX(0);
            screen.cam.switchRoom(true,true);
        } else if (getX() > 247) setX(247);

        if (getY() > 247 && screen.canSwitchRoom()) {

            setY(0);
            screen.cam.switchRoom(false,true);
        } else if (getY() > 247) setY(247);
    }

    private int getDir(boolean forward, boolean backward) {
        if (forward == backward) return 0;
        if (forward) return 1;
        return -1;
    }

    private void performShooting() {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
           // setX(getX()-1);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && currentWeapon instanceof QuickShooter) {
                performQuickShooting(entityManager, true);
            } else performShooting(entityManager, true);
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public float getTimeStopCoolDown() {
        return timeStopCoolDown;
    }

    public float getTimeStopDuration() {
        return timeStopDuration;
    }

    public float getTimeSinceLastStop() {
        return timeSinceLastStop;
    }

    public float getTimeStopLeft() {
        return timeStopLeft;
    }
}