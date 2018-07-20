package com.mygdx.game.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class FlapWorld {
  
  public World world;
  public Body body;
  private Body floor;
  private Body platform;
  
  public FlapWorld() {
    world = new World(new Vector2(0, -10), true);
    
    createBody();
    createFloor();
    createMovingObject();
  }

  private void createBody() {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.DynamicBody;
    bodyDef.position.set(-0,0);

    body = world.createBody(bodyDef);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(2, 1);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 1f;
    fixtureDef.friction = 1f;
    fixtureDef.restitution = 0.5f;
//    fixtureDef.friction
    
    body.createFixture(fixtureDef);
    body.setFixedRotation(true);
    
    shape.dispose();
    
    
  }
  
  private void createFloor() {
    // create a new body definition (type and location)
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.StaticBody;
    bodyDef.position.set(0, -10);
    // add it to the world
    floor = world.createBody(bodyDef);
    // set the shape (here we use a box 50 meters wide, 1 meter tall )
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(50, 1);
    // create the physical object in our body)
    // without this our body would just be data in the world
    floor.createFixture(shape, 0.0f);
    // we no longer use the shape object here so dispose of it.
    shape.dispose();
  }
  
  private void createMovingObject(){

    //create a new body definition (type and location)
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.KinematicBody;
    bodyDef.position.set(0,-12);


    // add it to the world
    platform = world.createBody(bodyDef);

    // set the shape (here we use a box 50 meters wide, 1 meter tall )
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(1,1);

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
  
  public void logicStep(float delta) {//call this at the end of the render loop
    body.setLinearVelocity(0.5f, body.getLinearVelocity().y);
    world.step(delta, 6, 2);
  }
}