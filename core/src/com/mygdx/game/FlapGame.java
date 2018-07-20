package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.ExampleScreen;
import com.mygdx.game.screens.MainMenuScreen;
import com.mygdx.game.screens.ScreenID;

public class FlapGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    public OrthographicCamera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        
        setScreen(new MainMenuScreen(this));
    }

    public void changeScreen(ScreenID screen) {
      System.out.println("screen = " + screen);
        switch(screen) {
            case GAME:
                this.setScreen(new GameScreen(this));
                break;
            case MAIN_MENU:
                this.setScreen(new ExampleScreen(this));
                break;
        }
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
    }
}
