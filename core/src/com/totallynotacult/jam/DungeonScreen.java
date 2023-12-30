package com.totallynotacult.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.totallynotacult.jam.entities.Enemy;
import com.totallynotacult.jam.entities.EntityManager;
import com.totallynotacult.jam.holders.MusicHolder;
import com.totallynotacult.jam.holders.TextureHolder;
import com.totallynotacult.jam.map.*;

public class DungeonScreen implements Screen {
    private final OrthographicCamera camera;
    private final PlayerCharacter character;
    private final SpriteBatch batch;
    private final EntityManager entityManager;
    private final ShapeRenderer renderer;
    public static int currentTimeLine = 1; // - 0 = past, 1 = present, 2 = future
    public static PlayerCharacter currentCharacter;
    private Room currentRoom;
    private Room[][] rooms;
    private int row;
    private int col;
    public Camera cam;
    private MyGdxGame game;
    private float fadeCounterMax = 0.5f;
    private float fadeCounter = fadeCounterMax * 2;
    public static int level = 1;
    Sprite floor;
    Sprite black;
    Sprite blur;
    Texture tileImg = TextureHolder.GREY_TILE.getTexture();

    public DungeonScreen(MyGdxGame game) {
        level = 1;
        currentTimeLine = 1;
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 320, 320);
        this.black = new Sprite(new Texture(Gdx.files.internal("hitbox.png")));
        this.black.setBounds(0,0,256,256);
        entityManager = new EntityManager(batch);
        character = new PlayerCharacter(entityManager, camera, this);
        currentCharacter = character;

        batch.enableBlending();
        fadeCounter = fadeCounterMax;
        entityManager.setCharacter(character);
        renderer = new ShapeRenderer();

        RoomGen r = new RoomGen(7);
        rooms = r.getLevelMatrix();
        row = r.getStartRoom()[0];
        col = r.getStartRoom()[1];
        currentRoom = rooms[row][col];
        Gdx.gl.glLineWidth(4);
        fixRoom();
        currentRoom.makeVisited();


        blur = new Sprite(new Texture(Gdx.files.internal("blurVig.png")));
        blur.setPosition(-131,-103);
        cam = new Camera(character, 256, 256, this);

        MusicHolder.THEME.getMusic().setVolume(0.1f);
        MusicHolder.THEME.getMusic().setLooping(true);
        MusicHolder.THEME.getMusic().play();
    }



    public boolean canSwitchRoom() {
        return entityManager.roomClear();
    }

    public void regenerateRoom() {

        currentRoom = new Room(currentRoom.getRoomType(), currentRoom.getExitDirections(), currentTimeLine, currentRoom.getIndexVariation());
        entityManager.removeEnemies();
        changeRoom(0, 0, true);
    }

    void fixRoom() {
        if (!roomExists(1, 0)) currentRoom.sealExit(1, 0);
        if (!roomExists(-1, 0)) currentRoom.sealExit(-1, 0);
        if (!roomExists(0, 1)) currentRoom.sealExit(0, 1);
        if (!roomExists(0, -1)) currentRoom.sealExit(0, -1);
    }

    boolean roomExists(int dRow, int dCol) {
        return row + dRow >= 0 && row + dRow < rooms.length && col + dCol >= 0 && col + dCol < rooms[row].length && rooms[row + dRow][col + dCol].getRoomType() != 0;
    }

    void changeRoom(int dRow, int dCol, boolean timeLineSwap) {

        //camera.position.set(character.getX(), character.getY(), 0);
        entityManager.removeAllBullets();
        if (!timeLineSwap) {
            currentRoom = rooms[row += dRow][col += dCol];
            character.setIsSuperCharged(false);
            currentCharacter = character;
        }

        fixRoom();
        for (int row = 0; row < currentRoom.getTiles().length; row++) {
            for (int col = 0; col < currentRoom.getTiles()[row].length; col++) {
                if (currentRoom.getTiles()[row][col] instanceof EnemyTile && !currentRoom.isVisited()) {
                    if (currentTimeLine == 1 && currentRoom.hasAFuture && !currentRoom.isVisited())
                        entityManager.addEnemy(new Enemy(col * 16, row * 16, true));
                    else entityManager.addEnemy(new Enemy(col * 16, row * 16, false));
                }
            }
        }

    }

    @Override
    public void show() {

    }

    boolean check = false;
    BitmapFont font = new BitmapFont(); //or use alex answer to use custom font

    @Override
    public void render(float delta) {
        if (currentTimeLine == 1 && entityManager.noMoreEnemies()) System.out.println("clear");//currentRoom.makeVisited();
        if (currentRoom.getRoomType() == 1) currentTimeLine = 1;
        ScreenUtils.clear(Color.BLACK);
        ///////////
        if (fadeCounter + delta > fadeCounterMax && fadeCounter < fadeCounterMax) {
            if (currentRoom.getRoomType() == 3) {
                currentTimeLine = 1;
                level++;
                RoomGen r = new RoomGen(7);
                rooms = r.getLevelMatrix();
                row = r.getStartRoom()[0];
                col = r.getStartRoom()[1];
                currentRoom = rooms[row][col];
                currentTimeLine = 1;

            } else {
                regenerateRoom();
                if (currentTimeLine == 1)
                    tileImg = TextureHolder.GREY_TILE.getTexture();
                else if (currentTimeLine == 2)  tileImg = new Texture(Gdx.files.internal("tiledFloor3.png"));
                    else tileImg = new Texture(Gdx.files.internal("tiledFloor2.png"));
            }
            fadeCounter = fadeCounterMax + 0.01f;
            check = true;
        }else {
            if (!check) fadeCounter += delta;
            else check = false;
        }



        batch.setProjectionMatrix(camera.combined);
        cam.camFollow();
        camera.position.set(cam.x, cam.y, 0);
        camera.update();

        floor = new Sprite(tileImg);
        batch.begin();
        floor.draw(batch);
        renderTiles(batch);



        entityManager.updateEntities(currentRoom.getAllTiles(), delta);
        entityManager.drawEntities();
        blur.draw(batch);
        float x = camera.position.x - camera.viewportWidth / 2 + 10;
        float y = camera.position.y - camera.viewportHeight / 2 + 10;
        font.draw(batch, "Level: "+level, x, y+40);
        String timeStream = "Present";
        if (currentTimeLine == 2) timeStream = "Future";
        else if (currentTimeLine == 0) timeStream = "Past";
        font.draw(batch, "The "+timeStream, x+100, y+40);
        batch.end();



        renderer.setProjectionMatrix(camera.combined);

        if (isFade()) {
            float opacity;
            if (fadeCounter > fadeCounterMax) {
                opacity =  (fadeCounterMax * 2 - fadeCounter) / fadeCounterMax;
            } else {
                opacity = fadeCounter / fadeCounterMax;
            }
            batch.begin();
            black.setAlpha(opacity);
            black.draw(batch);
            batch.end();
//            renderer.begin(ShapeRenderer.ShapeType.Filled);
//            renderer.setColor(new Color(0,0,0, opacity));
//            renderer.rect(-20, -20, 1000, 1000);
//            renderer.end();
        }
        drawHealthBar();
        drawTimeBar();

        if (entityManager.noMoreEnemies() && currentTimeLine == 1) currentRoom.makeVisited();
        if (character.isDead()) {
            game.setScreen(new MenuScreen(game, "Try Again?", "You have died; however, you may \n still choose to travel back to \n the past if your will holds."));
            MusicHolder.THEME.getMusic().stop();
        }
    }

    public void fadeToBlack(){
        fadeCounter = 0;
    }

    public boolean isFade() {
        return fadeCounter < fadeCounterMax * 2;
    }

    private void drawHealthBar() {
        float x = cam.x - camera.viewportWidth / 2 + 10;
        float y = cam.y - camera.viewportHeight / 2 + 10;

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.rect(x, y, 75f, 12);


        renderer.setColor(Color.CORAL);
        renderer.rect(x, y, (character.getHealth() / (float) character.getMaxHealth() * 75f), 12);
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);
        renderer.rect(x, y, 75, 12);
        renderer.end();
    }

    private void drawTimeBar() {
        float x = camera.position.x + camera.viewportWidth / 2 - 85;
        float y = camera.position.y - camera.viewportHeight / 2 + 10;

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.rect(x, y, 75, 12);
        renderer.setColor(Color.GOLD);
        if (entityManager.isMovementAllowed()) {
            renderer.rect(x, y, Math.min(character.getTimeSinceLastStop() / character.getTimeStopCoolDown(), 1) * 75, 12);
        } else {
            renderer.rect(x, y, Math.min(character.getTimeStopLeft() / character.getTimeStopDuration(), 1) * 75, 12);
        }
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);
        renderer.rect(x, y, 75, 12);
        renderer.end();
    }

    void renderTiles(SpriteBatch batch) {
        Texture weaponTileTexture = TextureHolder.ENEMY.getTexture();
        currentRoom.getAllTiles().forEach(tile -> {
            tile.draw(batch);
            if (tile.weaponTile) {
                batch.draw(weaponTileTexture, tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
            }
            if (tile instanceof BackwardTravelTile) ((BackwardTravelTile) tile).timeTileAnimations();
            if (tile instanceof ForwardTravelTile) ((ForwardTravelTile) tile).timeTileAnimations();
            if (tile instanceof SuperChargerTile) ((SuperChargerTile) tile).superChargerAnimation();
        });
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
