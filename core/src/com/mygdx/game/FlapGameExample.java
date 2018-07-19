package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.prism.image.ViewPort;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class FlapGameExample extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	BitmapFont font;

	private ViewPort viewPort;

	Group group;
	MyActor jet;
	MyActor flame;
	private Stage stage;

	class MyActor extends Actor {
		private TextureRegion textureRegion;

		public MyActor(TextureRegion textureRegion) {
			this.textureRegion = textureRegion;
//			setBounds(getX(), getY(), this.textureRegion.getRegionWidth(), this.textureRegion.getRegionHeight());

//			addListener(new InputListener(){
//				@Override
//				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//					started = !started;
//					System.out.println("started: "+ started);
//					return true;
//				}
//			});
		}

		@Override
		public void draw(Batch batch, float parentAlpha) {
			batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
					getScaleX(), getScaleY(), getRotation());
		}

		@Override
		public Actor hit(float x, float y, boolean touchable) {
			return super.hit(x, y, touchable);
		}
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		font.getData().setScale(2, 2);

		Viewport viewport = new ExtendViewport(480, 300);


		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);

		TextureRegion textureRegion = new TextureRegion(new Texture("jet1.png"));
		jet = new MyActor(textureRegion);
		jet.setBounds(0, 0, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());

		TextureRegion flameTexture = new TextureRegion(new Texture("flame.png"));
		flame = new MyActor(flameTexture);
		flame.setBounds(jet.getWidth()-25, 25, flameTexture.getRegionWidth(), flameTexture.getRegionHeight());


/*		Pool<MoveToAction> actionPool = new Pool<MoveToAction>(){
			protected MoveToAction newObject(){
				return new MoveToAction();
			}
		};
		MoveToAction moveAction = actionPool.obtain();
		moveAction.setDuration(5f);
		moveAction.setPosition(300f, 0);*/


		group = new Group();
		group.addActor(jet);
		group.addActor(flame);

		group.addAction(parallel(moveTo(200,0,5),rotateBy(90,5)));

//		myActor.addAction(parallel(scaleTo(0.5F, 0.5F, 5F), rotateTo(9.0f, 5f)));

		stage.addActor(group);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//		batch.draw(img, 0, 0);
//		batch.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		batch.begin();
		font.draw(batch, (group.getX() + "," + group.getY()), 200,200);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		stage.dispose();
		font.dispose();
	}
}
