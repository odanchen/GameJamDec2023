package com.totallynotacult.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StoryScreen implements Screen {

    private SpriteBatch batch;

    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;
    MyGdxGame game;
    BitmapFont font;

    String story;

    public StoryScreen(MyGdxGame game)
    {

        this.game = game;
        story = " will start in the present and travel to the past and future to defeat Dr. Evil. Good luck!";
        atlas = new TextureAtlas("skin/craftacular-ui.atlas");
        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"), atlas);

        batch = new SpriteBatch();
        camera = new OrthographicCamera(800, 800);
        this.font = new BitmapFont(Gdx.files.internal("skin/starwars.fnt"), Gdx.files.internal("skin/starwars.png"), false, true);

    }


    @Override
    public void show() {

    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(.1f, .12f, .16f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, story, 200, 100, 600, 1, true);
        batch.end();
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
        skin.dispose();
        atlas.dispose();
    }
}