package com.totallynotacult.jam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.sun.tools.javac.Main;

public class StoryScreen implements Screen {

    private SpriteBatch batch;

    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;
    MyGdxGame game;
    BitmapFont font;

    int currentSection = 0;
    float textY = 0;
    String[] textSections;

    public StoryScreen(MyGdxGame game)
    {

        this.game = game;
        String firstSection = "You have received an emergency call to the court of the TVA (Time Variant Authority) Variants have gone rampant and are trying to split the TVA timeline. You must use the past present and future to destroy them and expunge them from reality.";
        String secondSection = "Press any button to skip... :( (don't you dare)";
        String thirdSection = "Are you still there?";
        String fourthSection = "Knock Knock... Who's there?... Doctor... Doctor Who?... Exactly!";
        String fifthSection = "Credits: Oleksandr, Tobi, Parsa";

        textSections = new String[] {firstSection, secondSection, thirdSection, fourthSection, fifthSection};

        atlas = new TextureAtlas("skin/craftacular-ui.atlas");
        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"), atlas);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 480);
        this.font = new BitmapFont(Gdx.files.internal("skin/starwars.fnt"), Gdx.files.internal("skin/starwars.png"), false, true);
        this.font.getData().setScale(1);
    }


    @Override
    public void show() {

    }



    @Override
    public void render(float delta) {
        if (textY > 1000 + textSections[currentSection].length() * 2) {
            currentSection++;
            textY = 0;

        }
        if (currentSection >= textSections.length || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new MenuScreen(game, "Welcome to the Time Massacre", "Please turn up your volume."));
        }
        textY+=delta * 50;
        ScreenUtils.clear(0,0,0, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        int offset = Constants.window_size / 3;
        font.draw(batch, textSections[currentSection], offset / 2, textY, Constants.window_size - offset, 3, true);
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