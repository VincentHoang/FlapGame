package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.FlapGame;

public class MainMenuScreen implements Screen {

  private final FlapGame game;
  
  private Stage stage;
  private Image startButton;

  public MainMenuScreen(FlapGame game) {
    this.game = game;
    stage = new Stage(new ScreenViewport(game.camera)); //TODO ScreenViewPort maybe
    setupStartButton();

    Gdx.input.setInputProcessor(stage);
  }

  private void setupStartButton() {
    startButton = new Image(new Texture(Gdx.files.internal("start_button.png")));
    startButton.setTouchable(Touchable.enabled);
    startButton.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        game.changeScreen(ScreenID.GAME);
        return true;
      }
    });
    
    
    startButton.setBounds(stage.getWidth()/2 - 150, stage.getHeight()/2 -150, 300, 300);
    
    stage.addActor(startButton);
  }

  @Override
  public void show() {
    
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    stage.act(Gdx.graphics.getDeltaTime());
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