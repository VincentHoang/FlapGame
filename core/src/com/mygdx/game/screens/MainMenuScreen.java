package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.FlapGame;


public class MainMenuScreen extends InputAdapter implements Screen {

    private FlapGame game;

    OrthographicCamera camera;

    Stage stage;

    Stage differentStage;


    public MainMenuScreen(FlapGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
//
//        viewPort = new StretchViewport(100, 200, camera);
//
//        Gdx.input.setInputProcessor(this); //stage

        stage = new Stage(new StretchViewport(32, 48));
        Gdx.input.setInputProcessor(stage);

        differentStage = new Stage(new ScreenViewport());

        Image image = new Image(new Texture(Gdx.files.internal("jet.png")));
        image.setBounds(0,0, 10, 5);
        stage.addActor(image);
//        Table table = new Table();
////        table.setFillParent(true);
//        table.setDebug(true);
//        table.setBounds(0,0, 100, 200);
//        stage.addActor(table);
//
//        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
//        TextButton newGame = new TextButton("New Game", skin);
//        newGame.setWidth(100);
//        TextButton preferences = new TextButton("Preferences", skin);
//        TextButton exit = new TextButton("Exit", skin);
//
//        table.add(newGame);
//        table.row();//.pad(10, 0, 10, 0);
//        table.add(preferences);
//        table.row();
//        table.add(exit);

        Image image2 = new Image(new Texture(Gdx.files.internal("flame.png")));
        image2.setBounds(100, 100, 100, 100);
        differentStage.addActor(image2);
        //https://stackoverflow.com/questions/25346611/libgdx-several-stages-in-one-screen
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        camera.update();
//        game.batch.setProjectionMatrix(camera.combined);
//
//        game.batch.begin();
//        game.font.draw(game.batch, "Welcome to Drop!!! ", 100, 150);
//        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
//        game.batch.end();


        differentStage.getViewport().apply();
        differentStage.act();
        differentStage.draw();

        stage.getViewport().apply();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

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

    }
}
