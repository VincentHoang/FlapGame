package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.LoadingScreen;
import com.mygdx.game.screens.MainMenuScreen;
import com.mygdx.game.screens.ScreenID;

public class FlapGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    private Screen loadingScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();


//        loadingScreen = new LoadingScreen();
        setScreen(new MainMenuScreen(this));
    }

    public void changeScreen(ScreenID screen) {
        switch(screen) {
            case GAME:
                this.setScreen(new GameScreen());
            case MAIN_MENU:
                this.setScreen(new MainMenuScreen(this));
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
