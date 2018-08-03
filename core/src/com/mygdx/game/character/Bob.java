package com.mygdx.game.character;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

public class Bob {

    public Body body;

    private float velocityX = 8;
    private float velocityY;
    public float width = 5;
    public float height = 2.5f;

    private boolean controllable;

    public Bob(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(-0,0);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        body.setFixedRotation(true);
        body.setGravityScale(0);

        shape.dispose();

        controllable = true;
    }

    public void setVelocityY(float velocityYPercentage) {
        this.velocityY = velocityYPercentage*15;
        body.setTransform(body.getPosition(), MathUtils.atan2(velocityY, velocityX));
    }

    public void setControllable(boolean controllable) {
        this.controllable = controllable;
    }

    public void step(float delta) {
        if (controllable) {
            body.setLinearVelocity(velocityX, velocityY);
        }
    }
}
