package com.mygdx.game.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.character.Bob;

import java.util.List;

public class FlapWorld {

  public static final float WORLD_SCREEN_WIDTH = 18;
  public static final float WORLD_SCREEN_HEIGHT = 32;

  public static final float WORLD_HEIGHT = WORLD_SCREEN_HEIGHT * 1.2f;
  public static final float FLOOR_HEIGHT = 6;


  public World world;
  public RandomObstacles randomObstacles;
  //  public Body body;
  private Body floor;
  private Body ceiling;
  private Body platform;

  public Bob bob;

  public List<Body> staticObstacles;

  public FlapWorld() {
    world = new World(new Vector2(0, -10), true);
    randomObstacles = new RandomObstacles(world);
    randomObstacles.initialGenerate();

//    createFloor();
//    createCeiling();
//    createMovingObject();

    bob = new Bob(world);

//    staticObstacles = new ArrayList<>();
//    staticObstacles.add(createStaticObstacle(30f, -4f, 3f, 3f));
//    staticObstacles.add(createStaticObstacle(50f, -5f, 4f, 4f));
//    staticObstacles.add(createStaticObstacle(80f, 7f, 2f, 5f));

  /*  world.setContactListener(new ContactListener() {
      @Override
      public void beginContact(Contact contact) {
        if( collision(contact, body, ceiling)) {
          body.setGravityScale(1);
          controllable = false;
        }
      }

      private boolean collision(Contact contact, Body body1, Body body2) {
        return (contact.getFixtureA().getBody() == body1 &&
            contact.getFixtureB().getBody() == body2)
            ||
            (contact.getFixtureA().getBody() == body2 &&
                contact.getFixtureB().getBody() == body1);
      }

      @Override
      public void endContact(Contact contact) {

      }

      @Override
      public void preSolve(Contact contact, Manifold oldManifold) {

      }

      @Override
      public void postSolve(Contact contact, ContactImpulse impulse) {

      }
    });*/
  }

  private void createFloor() {
    // create a new body definition (type and location)
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.StaticBody;
    bodyDef.position.set(0, FLOOR_HEIGHT);
    // add it to the world
    floor = world.createBody(bodyDef);
    // set the shape (here we use a box 50 meters wide, 1 meter tall )
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(100, 1);
    // create the physical object in our body)
    // without this our body would just be data in the world
    floor.createFixture(shape, 0.0f);
    // we no longer use the shape object here so dispose of it.
    shape.dispose();
  }

//  private void createCeiling() {
//    BodyDef bodyDef = new BodyDef();
//    bodyDef.type = BodyDef.BodyType.StaticBody;
//    bodyDef.position.set(0, WORLD_HEIGHT);
//
//    ceiling = world.createBody(bodyDef);
//    PolygonShape shape = new PolygonShape();
//    shape.setAsBox(100, 1);
//
//    ceiling.createFixture(shape, 0.0f);
//    shape.dispose();
//  }
//
//  private Body createStaticObstacle(float x, float y, float width, float height) {
//    BodyDef bodyDef = new BodyDef();
//    bodyDef.type = BodyDef.BodyType.StaticBody;
//    bodyDef.position.set(x, y);
//
//    Body body = world.createBody(bodyDef);
//    PolygonShape shape = new PolygonShape();
//    shape.setAsBox(width, height);
//
//    body.createFixture(shape, 0.0f);
//    shape.dispose();
//    return body;
//  }

  private void createMovingObject() {

    //create a new body definition (type and location)
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.KinematicBody;
    bodyDef.position.set(0, -12);


    // add it to the world
    platform = world.createBody(bodyDef);

    // set the shape (here we use a box 50 meters wide, 1 meter tall )
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(1, 1);

    // set the properties of the object ( shape, weight, restitution(bouncyness)
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 1f;

    // create the physical object in our body)
    // without this our body would just be data in the world
    platform.createFixture(fixtureDef);

    // we no longer use the shape object here so dispose of it.
    shape.dispose();

    platform.setLinearVelocity(0, 0.75f);
  }

  public void setVelocityY(float velocityYPercentage) {
    bob.setVelocityY(velocityYPercentage);
  }

  public void logicStep(float delta) {//call this at the end of the render loop
    Vector2 bobPosition = bob.body.getPosition();

    randomObstacles.generate(bobPosition.x, WORLD_HEIGHT, WORLD_SCREEN_WIDTH);
    randomObstacles.cleanup(bobPosition.x, WORLD_SCREEN_WIDTH);

    bob.step(delta);
    world.step(delta, 6, 2);
  }
}