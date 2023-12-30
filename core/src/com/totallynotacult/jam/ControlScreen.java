package com.totallynotacult.jam;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
// Import the necessary classes
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

// Define the ControlScreen class that implements Screen
public class ControlScreen implements Screen {

    // Declare the stage, skin and table fields
    private Stage stage;
    private Skin skin;
    private Table table;
    MyGdxGame game;

    // Define the constructor that takes a skin as a parameter
    public ControlScreen(MyGdxGame game) {
        this.game = game;
        // Assign the skin to the field
        this.skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"), new TextureAtlas("skin/craftacular-ui.atlas"));
        // Create a new stage
        stage = new Stage();
        // Set the stage as the input processor
        Gdx.input.setInputProcessor(stage);
        // Create a new table with the skin
        table = new Table(skin);
        // Set the table to fill the stage
        table.setFillParent(true);
        // Add some padding to the table
        table.pad(50);
    }

    // Override the show method
    @Override
    public void show() {
        // Clear the table
        table.clear();
        // Create a label for the title
        Label titleLabel = new Label("Controls", skin, "title");

        // Add the labels to the table
        table.add(titleLabel).colspan(2);
        table.row();
        table.row();
        // Add some buttons for the controls
        table.add("W - Move Up").left();
        table.row();
        table.add("A - Move Left").left();
        table.row();
        table.add("S - Move Down").left();
        table.row();
        table.add("D - Move Right").left();
        table.row();
        table.add("Left Click - Shoot").left();
        table.row();
        table.add("Space Bar - Stop Time").left();
        table.row();
        table.add("E - Interact with Time Travel Tiles").left();
        table.row();
        table.add("The red bar represents health").left();
        table.row();
        table.add("The yellow bar represents time stop").left();
        table.row();
        table.add("The yellow bar represents time stop").left();
        table.row();
        table.add("Press space to return to main menu").height(100);

        table.center();
        // Add the table to the stage
        stage.addActor(table);
    }

    // Override the render method
    @Override
    public void render(float delta) {
        // Clear the screen with a black color
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Update and draw the stage
        stage.act(delta);
        stage.draw();
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new MenuScreen(game, "Welcome to the The True Time Keeper", "Thank you for reading the controls."));
        }
    }

    // Override the resize method
    @Override
    public void resize(int width, int height) {
        // Update the stage viewport
        stage.getViewport().update(width, height, true);
    }

    // Override the dispose method
    @Override
    public void dispose() {
        // Dispose the stage
        stage.dispose();
    }

    // Override the other methods with empty bodies
    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
