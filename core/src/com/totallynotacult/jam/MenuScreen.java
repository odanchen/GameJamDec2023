package com.totallynotacult.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen implements Screen {

    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;
    MyGdxGame game;
    String mainText;
    String secondaryText;
    ClickListener[] clickListeners = new ClickListener[3];

    public MenuScreen(MyGdxGame game, String mainText, String secondaryText)
    {
        this.mainText = mainText;
        this.secondaryText = secondaryText;
        this.game = game;
        atlas = new TextureAtlas("skin/craftacular-ui.atlas");
        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"), atlas);
        this.playButton = new TextButton("Play", skin);
        this.exitButton = new TextButton("Exit", skin);
        this.storyLineButton = new TextButton("Story Line", skin);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(800,800, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
    }

    //Create buttons
    TextButton playButton;

    TextButton storyLineButton;

    TextButton exitButton;
    @Override
    public void show() {
        //Stage should control input:
        Gdx.input.setInputProcessor(stage);

        //Create Table
        Table mainTable = new Table(skin);
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.top();

        clickListeners[0] = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clearListeners();
                game.changeToDungeon();
                game.prepareNewDungeon();
            }

        };
        clickListeners[1] = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();

            }
        };
        clickListeners[2] = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new StoryScreen(game));
            }
        };

        //Add listeners to buttons
        playButton.addListener(clickListeners[0]);
        exitButton.addListener(clickListeners[1]);
        storyLineButton.addListener(clickListeners[2]);
        mainTable.add(new Label(mainText, skin)).expandX().expandY();
        mainTable.row();
        mainTable.add(new Label(secondaryText, skin)).expandX().padBottom(50).expandY();
        mainTable.row();
        mainTable.add(playButton).expandY();
        mainTable.row();
        mainTable.add(storyLineButton).expandY();
        mainTable.row();
        mainTable.add(exitButton).expandY();


        stage.addActor(mainTable);
    }

    private void clearListeners() {
        for (ClickListener listener : clickListeners) {
            playButton.removeListener(listener);
            exitButton.removeListener(listener);
            storyLineButton.removeListener(listener);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.1f, .12f, .16f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
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
        skin.dispose();
        atlas.dispose();
    }
}