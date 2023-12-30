package com.totallynotacult.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.totallynotacult.jam.holders.MusicHolder;

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
    Texture menuTexture;

    public MenuScreen(MyGdxGame game, String mainText, String secondaryText)
    {
        this.mainText = mainText;
        this.secondaryText = secondaryText;
        this.game = game;
        atlas = new TextureAtlas("skin/craftacular-ui.atlas");
        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"), atlas);
        this.playButton = new TextButton("Play", skin);
        this.controlsButton = new TextButton("Controls", skin);
        this.storyLineButton = new TextButton("Story Line", skin);

        menuTexture = new Texture("main_menu.png");

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1024,1024, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
    }

    //Create buttons
    TextButton playButton;

    TextButton storyLineButton;

    TextButton controlsButton;
    @Override
    public void show() {
        //Stage should control input:
        Gdx.input.setInputProcessor(stage);
//dd
        //Create Table
        Table mainTable = new Table(skin);
        Table textTable = new Table(skin);

        //Set alignment of contents in the table.
        mainTable.top();

        clickListeners[0] = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicHolder.THEME.getMusic().setVolume(0.1f);
                MusicHolder.THEME.getMusic().setLooping(true);
                MusicHolder.THEME.getMusic().play();
                clearListeners();
                game.prepareNewDungeon();
                game.changeToDungeon();
            }

        };
        clickListeners[1] = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clearListeners();
                game.setScreen(new ControlScreen(game));

            }
        };
        clickListeners[2] = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clearListeners();
                game.setScreen(new StoryScreen(game));
            }
        };
        //Add listeners to buttons
        playButton.addListener(clickListeners[0]);
        controlsButton.addListener(clickListeners[1]);
        storyLineButton.addListener(clickListeners[2]);

        mainTable.moveBy(725,325);
        mainTable.add(playButton).height(50).padBottom(15);
        mainTable.row();
        mainTable.add(storyLineButton).height(50);
        mainTable.row();
        mainTable.add(controlsButton).height(50).padTop(15);

        textTable.moveBy(512, 900);
        Label mainLabel = new Label(mainText, skin);
        mainLabel.setFontScale(1.5f);
        textTable.add(mainLabel);
        textTable.row();

        Label secondaryLabel = new Label(secondaryText, skin);
        secondaryLabel.setFontScale(1.2f);

        secondaryLabel.setColor(Color.RED);
        textTable.add(secondaryLabel);
        textTable.row();
        stage.addActor(mainTable);
        stage.addActor(textTable);
    }

    private void clearListeners() {
        for (ClickListener listener : clickListeners) {
            playButton.removeListener(listener);
            controlsButton.removeListener(listener);
            storyLineButton.removeListener(listener);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.1f, .12f, .16f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(menuTexture, 0, 0);
        batch.end();
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