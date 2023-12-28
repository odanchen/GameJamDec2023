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
import com.totallynotacult.jam.map.EnemyTile;
import com.totallynotacult.jam.map.Room;
import com.totallynotacult.jam.map.RoomGen;

public class DungeonScreen implements Screen {
    private final OrthographicCamera camera;
    private final PlayerCharacter character;
    private final SpriteBatch batch;
    private final EntityManager entityManager;
    private final ShapeRenderer renderer;


    // final ROOMWIDTH =
    private Room currentRoom;
    private Room[][] rooms;
    private int row;
    private int col;
    private Camera cam;
    private MyGdxGame game;


    public DungeonScreen(MyGdxGame game) {
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        entityManager = new EntityManager(batch);
        character = new PlayerCharacter(entityManager, camera, this);
        cam = new Camera(character, 320, 320);
        entityManager.setCharacter(character);
        renderer = new ShapeRenderer();

        RoomGen r = new RoomGen(7);
        rooms = r.getLevelMatrix();
        row = r.getStartRoom()[0];
        col = r.getStartRoom()[1];
        currentRoom = rooms[row][col];
        Gdx.gl.glLineWidth(3);
        fixRoom();
        currentRoom.makeVisited();

        camera.setToOrtho(false, 256, 256);
    }

    public boolean canSwitchRoom() {
        return entityManager.roomClear();
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
        entityManager.removeAllBullets();
        currentRoom = rooms[row += dRow][col += dCol];
        cam = new Camera(character, 256, 256);
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
        // cam.camFollow();
        ScreenUtils.clear(Color.BLACK);


        batch.begin();

        renderTiles(batch);


        character.draw(batch);

        entityManager.updateEntities(currentRoom.getAllTiles(), delta);
        entityManager.drawEntities();

        batch.end();

        drawHealthBar();
        drawTimeBar();


        // camera.position.set(cam.x, cam.y, 0);
        camera.update();

        renderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        if (character.isDead()) {
            game.setScreen(new GameOverScreen(game));
        }
    }

    private void drawHealthBar() {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.CORAL);
        renderer.rect(7, 242, 75, 12);
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.CORAL);
        renderer.rect(7, 242, (character.getHealth() / (float)character.getMaxHealth() * 75f), 12);
        renderer.end();
    }

    private void drawTimeBar() {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.GOLD);
        renderer.rect(7, 7, 75, 12);
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.GOLD);
        if (entityManager.isMovementAllowed()) {

        }
        renderer.rect(7, 3, (character.getHealth() / (float)character.getMaxHealth() * 75f), 12);
        renderer.end();
    }

    void renderTiles(SpriteBatch batch) {
        Texture weaponTileTexture = TextureHolder.TEMP_PLAYER.getTexture();
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
