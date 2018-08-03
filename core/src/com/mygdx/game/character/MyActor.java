package com.mygdx.game.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyActor extends Actor { //Maybe use Image ?
  private TextureRegion textureRegion;

  public MyActor (TextureRegion textureRegion, float width, float height) {
    this.textureRegion = textureRegion;
    setBounds(textureRegion.getRegionX(), textureRegion.getRegionY(),
        width, height);
  }

  @Override
  public void draw (Batch batch, float parentAlpha) {
    Color color = getColor();
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
    batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(),
        getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
  }
}