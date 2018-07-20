package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.FlapGame;
import com.mygdx.game.world.FlapWorld;

public class GameScreen implements Screen {

  private final FlapGame game;
  private static final int WORLD_WIDTH = 24;
  private static final int WORLD_HEIGHT = 32;

  private FlapWorld flapWorld;
  private Box2DDebugRenderer debugRenderer;
  
  private OrthographicCamera camera; //Probably use the games camera
  
  private Pixmap pixmap;
  private Sprite sprite;

  Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
  
  ////
  Stage stageHUD;
  Slider slider;
  
  public GameScreen(FlapGame game) {

    this.game = game;
    flapWorld = new FlapWorld();
    debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);
    camera = new OrthographicCamera(); //TODO use Viewports
    
    pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
    pixmap.setColor(Color.RED);
    pixmap.fill();

    sprite = new Sprite(new Texture(pixmap));

    stageHUD = new Stage(new ScreenViewport(camera));
    Image randomImage = new Image(new Texture(Gdx.files.internal("badlogic.jpg")));
    Image randomImage2 = new Image(new Texture(Gdx.files.internal("flame.png")));
    slider = new Slider(0, 1, 0.1f, false, new SliderStyle(randomImage.getDrawable(), randomImage2.getDrawable()));
    slider.setBounds(-Gdx.graphics.getWidth()/2 + 100, -Gdx.graphics.getHeight()/2 + 100, Gdx.graphics.getWidth() - 200, 100);
    slider.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        System.out.println("YAY"+ slider.getValue());
        flapWorld.body.setLinearVelocity( 1, slider.getValue()*10);
      }
    });
    
//    randomImage.setBounds(0, 0, 200, 200);
    stageHUD.addActor(slider);
//    stageHUD.addActor(randomImage);
    
    Gdx.input.setInputProcessor(stageHUD);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0,0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    renderWorld(delta);
    renderHUD(delta);
  }
  
  private void renderWorld(float delta) {
    Vector2 bodyPosition = flapWorld.body.getPosition();
    camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
    camera.position.set(bodyPosition.x + 6, bodyPosition.y,0);
    camera.update();

    game.batch.setProjectionMatrix(camera.combined); //IMPORTANT
    game.batch.begin();
    sprite.draw(game.batch);
    game.batch.end();

    debugRenderer.render(flapWorld.world, camera.combined);
    flapWorld.logicStep(delta);
  }

  private void renderHUD(float delta) {
    stageHUD.getViewport().apply();
//    stageHUD.act(Math.min(delta, 1 / 30f)); //TODO hmm
    stageHUD.act(delta);
    stageHUD.draw();
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
