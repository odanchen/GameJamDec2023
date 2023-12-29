package com.totallynotacult.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.totallynotacult.jam.entities.Enemy;
import com.totallynotacult.jam.entities.EntityManager;
import com.totallynotacult.jam.holders.MusicHolder;
import com.totallynotacult.jam.holders.TextureHolder;
import com.totallynotacult.jam.map.EnemyTile;
import com.totallynotacult.jam.map.Room;
import com.totallynotacult.jam.map.RoomGen;

public class DungeonScreen implements Screen {
    private final OrthographicCamera camera;
    private final PlayerCharacter character;
    private final SpriteBatch batch;
    private final EntityManager entityManager;
    private final ShapeRenderer renderer;
    public static int currentTimeLine = 1; // - 0 = past, 1 = present, 2 = future

    private Room currentRoom;
    private Room[][] rooms;
    private int row;
    private int col;
    public Camera cam;
    private MyGdxGame game;


    public DungeonScreen(MyGdxGame game) {
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 320, 320);

        entityManager = new EntityManager(batch);
        character = new PlayerCharacter(entityManager, camera, this);


        entityManager.setCharacter(character);
        renderer = new ShapeRenderer();

        RoomGen r = new RoomGen(9);
        rooms = r.getLevelMatrix();
        row = r.getStartRoom()[0];
        col = r.getStartRoom()[1];
        currentRoom = rooms[row][col];
        Gdx.gl.glLineWidth(4);
        fixRoom();
        currentRoom.makeVisited();

        cam = new Camera(character, 256, 256, this);
    }

    public boolean canSwitchRoom() {
        return entityManager.roomClear();
    }

    public void regenerateRoom() {
        currentRoom = new Room(currentRoom.getRoomType(),currentRoom.getExitDirections(),currentTimeLine,currentRoom.getIndexVariation());
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

    void changeRoom(int dRow, int dCol) {
        //camera.position.set(character.getX(), character.getY(), 0);
        entityManager.removeAllBullets();
        currentRoom = rooms[row += dRow][col += dCol];

        fixRoom();
        for (int row = 0; row < currentRoom.getTiles().length; row++) {
            for (int col = 0; col < currentRoom.getTiles()[row].length; col++) {
                if (currentRoom.getTiles()[row][col] instanceof EnemyTile && !currentRoom.isVisited()) {
                    entityManager.addEnemy(new Enemy(col * 16, row * 16));
                }
            }
        }
        currentRoom.makeVisited();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);


        batch.setProjectionMatrix(camera.combined);
        cam.camFollow();
        camera.position.set(cam.x, cam.y, 0);
        camera.update();


        batch.begin();

        renderTiles(batch);
        entityManager.updateEntities(currentRoom.getAllTiles(), delta);
        entityManager.drawEntities();

        batch.end();


        renderer.setProjectionMatrix(camera.combined);
        drawHealthBar();
        drawTimeBar();

        if (character.isDead()) game.setScreen(new MenuScreen(game, "You died, yet, you may still choose to travel back to the past if so your will holds.", "Try again?"));
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
