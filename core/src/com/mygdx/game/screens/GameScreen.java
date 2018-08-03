package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.FlapGame;
import com.mygdx.game.character.Bob;
import com.mygdx.game.world.FlapWorld;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

  private final FlapGame game;
  private static final int WORLD_WIDTH = 18;
  private static final int WORLD_HEIGHT = 32;

  private FlapWorld flapWorld;
  private Box2DDebugRenderer debugRenderer;
  
  private OrthographicCamera camera; //Probably use the games camera
  private Viewport worldViewport;
  
  private Pixmap pixmap;
  private List<Sprite> randomSprites = new ArrayList<>();

  Stage stageHUD;
  private TextureAtlas bobAtlas;
  private Animation<TextureRegion> bobAnimation;
  float elapsedTime = 0;
  private Sprite bobSprite;
  private Sprite capeSprite;
  private Sprite backgroundOne;
  private Sprite backgroundTwo;

  ////
  Slider slider;
  
  public GameScreen(FlapGame game) {

    this.game = game;
    flapWorld = new FlapWorld();
    debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);
    camera = new OrthographicCamera(); //TODO use Viewports
    worldViewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
    
    pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
    pixmap.setColor(Color.RED);
    pixmap.fill();

//    addRandomSprite(0, 0, 10, 10);
//    addRandomSprite(30, -5, 10, 10);
//    addRandomSprite(50, 7, 10, 10);

    stageHUD = new Stage(new ScreenViewport(camera));

    /***BOB**/
    bobAtlas = new TextureAtlas("bob/penguin_nontrim.txt");
    bobAnimation = new Animation<>(1/15f, bobAtlas.getRegions());
//    bobSprite = bobAtlas.createSprite("Penguin-2");//new Sprite(new TextureRegion(new Texture("bob/Penguin-1.png")));
//    bobSprite.setBounds(0, 0, flapWorld.bob.width, flapWorld.bob.height);

    capeSprite = new Sprite(new TextureRegion(new Texture("bob/Red-Cape-1.png")));
    capeSprite.setBounds(0, 0, flapWorld.bob.width/3, flapWorld.bob.height/3);


    TextureRegion backgroundTexture = new TextureRegion(new Texture("scene-1.png"));
    backgroundOne = new Sprite(backgroundTexture);
    backgroundOne.setSize(100, WORLD_HEIGHT*1.5f);
    backgroundOne.setCenterY(0);
    backgroundOne.setX(0);

    backgroundTwo = new Sprite(backgroundTexture);
    backgroundTwo.setSize(100, WORLD_HEIGHT*1.5f);
    backgroundTwo.setCenterY(0);
    backgroundTwo.setX(98);

    initSlider();

    Gdx.input.setInputProcessor(stageHUD);
  }

  private void addRandomSprite(float x, float y, float width, float height) {
    Sprite sprite = new Sprite(new Texture(pixmap));
    sprite.setBounds(x, y, width, height);
    randomSprites.add(sprite);
  }

  private void initSlider() {
    Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
    pixmap.setColor(Color.GRAY);
    pixmap.fill();

    Image sliderBackground = new Image(new Texture(pixmap));
    Image sliderForeground = new Image(new Texture(Gdx.files.internal("flame.png")));

    slider = new Slider(-1, 1, 0.05f, false, new SliderStyle(sliderBackground.getDrawable(), sliderForeground.getDrawable()));
    slider.setValue(0);
    flapWorld.setVelocityY(0);
    slider.setBounds(100, 0, Gdx.graphics.getWidth() - 200, 100);
    slider.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        flapWorld.setVelocityY(-((Slider)actor).getValue());
      }
    });
    
    stageHUD.addActor(slider);

    pixmap.dispose();
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
    Body bobBody = flapWorld.bob.body;
    Vector2 bodyPosition = bobBody.getPosition();

//    camera.zoom = 3f;
    camera.position.set(bodyPosition.x + 4, bodyPosition.y,0);
    worldViewport.apply();

    game.batch.setProjectionMatrix(camera.combined); //IMPORTANT
    game.batch.begin();

    renderBackground();
    renderBob(delta);

    game.batch.end();

    debugRenderer.render(flapWorld.world, camera.combined);
    flapWorld.logicStep(delta);
  }

  private void renderBob(float delta) {
    Bob bob = flapWorld.bob;
    Body bobBody = bob.body;
    Vector2 bodyPosition = bobBody.getPosition();

//    bobSprite.setCenter(bodyPosition.x, bodyPosition.y);
//    bobSprite.setOriginCenter();
//    bobSprite.setRotation(bobBody.getAngle()*MathUtils.radiansToDegrees);
//    bobSprite.draw(game.batch);

    capeSprite.setCenter(bodyPosition.x-0.75f, bodyPosition.y+0.5f);
    capeSprite.setOrigin(capeSprite.getWidth()/2+0.75f, capeSprite.getHeight()/2-0.4f);
//    capeSprite.setOriginCenter();
//    capeSprite.setOrigin(bobSprite.getOriginX(), bobSprite.getOriginY());
    capeSprite.setRotation(bobBody.getAngle()*MathUtils.radiansToDegrees);
    capeSprite.draw(game.batch);

    elapsedTime += delta;
    game.batch.draw(
        bobAnimation.getKeyFrame(elapsedTime, true),
        bodyPosition.x - bob.width/2,
        bodyPosition.y - bob.height/2,
        bob.width/2,
        bob.height/2,
        bob.width,
        bob.height,
        1, 1,
        bobBody.getAngle()*MathUtils.radiansToDegrees
        );


  }

  private void renderBackground() {
    if (backgroundOne.getX() < backgroundTwo.getX()) {

    }
    Vector2 position = flapWorld.bob.body.getPosition();
//    if (position.x > backgroundOne.)

    Sprite greater, lesser;
    if (backgroundOne.getX() > backgroundTwo.getX()) {
      greater = backgroundOne;
      lesser = backgroundTwo;
    } else {
      greater = backgroundTwo;
      lesser = backgroundOne;
    }

    if (position.x > greater.getX() + greater.getWidth()/2) {
      lesser.setX(greater.getX() + greater.getWidth() - 2);
    }

    backgroundOne.draw(game.batch);
    backgroundTwo.draw(game.batch);
  }

  private void renderHUD(float delta) {
    stageHUD.getViewport().apply(true); //need to centre the camera (because world rendering moved it)
//    stageHUD.act(Math.min(delta, 1 / 30f)); //TODO hmm
    stageHUD.act(delta);
    stageHUD.draw();
  }

  @Override
  public void resize(int width, int height) {
    worldViewport.update(width, height);
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
    bobAtlas.dispose();
  }
}
