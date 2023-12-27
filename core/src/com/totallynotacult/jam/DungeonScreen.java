package com.totallynotacult.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    // final ROOMWIDTH =
    private Room currentRoom;
    private Room[][] rooms;
    private int row;
    private int col;
    private Camera cam;


    public DungeonScreen() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        entityManager = new EntityManager(batch);
        character = new PlayerCharacter(entityManager, camera, this);
        cam = new Camera(character, 320, 320);
        entityManager.setCharacter(character);

        RoomGen r = new RoomGen(7);
        rooms = r.getLevelMatrix();
        row = r.getStartRoom()[0];
        col = r.getStartRoom()[1];
        currentRoom = rooms[row][col];

        fixRoom();
        entityManager.addEnemy(new Enemy(100, 100));

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
        return row + dRow >= 0 && row + dRow < rooms.length && col + dCol >= 0 &&
                col + dCol < rooms[row].length && rooms[row + dRow][col + dCol].getRoomType() != 0;
    }

    void changeRoom(int dRow, int dCol) {
        currentRoom = rooms[row += dRow][col += dCol];
        cam = new Camera(character, 256, 256);
        fixRoom();
        for (int row = 0; row < currentRoom.getTiles().length; row++) {
            for (int col = 0; col < currentRoom.getTiles()[row].length; col++) {
                if (currentRoom.getTiles()[row][col] instanceof EnemyTile) {
                    entityManager.addEnemy(new Enemy(col * 32, row * 32));
                }
            }
        }
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

        character.update(currentRoom.getAllTiles(), delta, entityManager);
        character.draw(batch);

        entityManager.updateEntities(currentRoom.getAllTiles(), delta);
        entityManager.drawEntities();
        batch.end();

        // camera.position.set(cam.x, cam.y, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

    }

    void renderTiles(SpriteBatch batch) {
        Texture weaponTileTexture = new Texture(Gdx.files.internal("tempPlayer.png"));
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
