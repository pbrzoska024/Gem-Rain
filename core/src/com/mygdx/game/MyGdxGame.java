package com.mygdx.game;


import com.badlogic.drop.Drop;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxStarter;


public class MyGdxGame implements Screen {

	SpriteBatch batch;
	Texture sky;
	Texture img2;
	Texture mountains;
	Texture cloud_lonely;
	Texture bottom_clouds;
	private Music MMusic;
	int T;
	private Stage stage;
	private Table table;

	private BitmapFont font;
	private Skin skinButton;
	private TextureAtlas buttonAtlas;
	private TextButton.TextButtonStyle textButtonStyle;
	private TextButton button;
	private TextButton button2;
	private TextButton button3;



	private OrthographicCamera camera;

	private Game game;


	float left_screen_border = 20f;
	float z = 0;
	float right_screen_border = -150f;
	float second_left_screen_border = 20f;
	float second_right_screen_border = -150f;





	public MyGdxGame(Game aGame) {

	game = aGame;
	stage = new Stage(new ScreenViewport());





		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		table.setDebug(true);


		batch = new SpriteBatch();
		sky = new Texture("sky_lightened.png");
		img2 = new Texture("clouds_BG.png");
		mountains = new Texture("mountains.png");
		cloud_lonely = new Texture("cloud_lonely.png");
		bottom_clouds = new Texture("clouds_MG_1_lightened.png");


		//MMusic = Gdx.audio.newMusic(Gdx.files.internal("music.wav"));
		//MMusic.setLooping(true);
		//MMusic.play();


		font = new BitmapFont();
		skinButton = new Skin();
		buttonAtlas = new TextureAtlas("freezing/skin/freezing-ui.atlas");
		skinButton.addRegions(buttonAtlas);
		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = font;

		textButtonStyle.up = skinButton.getDrawable("button");
		textButtonStyle.down = skinButton.getDrawable("button-down");
		textButtonStyle.checked = skinButton.getDrawable("checkbox");


		button = new TextButton("Quit", textButtonStyle);
		button.setText("Quit");
		button.setHeight(25);
		button.setWidth(100);
		button.setPosition(142, 50);
		stage.addActor(button);

		button.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.exit();
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});


		button3 = new TextButton("Generate XML", textButtonStyle);
		button3.setText("Start");
		button3.setHeight(25);
		button3.setWidth(100);
		button3.setPosition(142, 100);


	button3.addListener(new InputListener(){
		@Override
		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
			game.setScreen(new Drop(game));
		}
		@Override
		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			return true;
		}
		});

		stage.addActor(button3);


	}




	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}


	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



		stage.act(Gdx.graphics.getDeltaTime());
		batch.begin();
		batch.enableBlending();
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch.draw(sky, 0, 0);
		batch.draw(mountains, 0, 0);
		batch.draw(img2, 0, -60);







		/* Cloud Movement*/

		if (left_screen_border > second_right_screen_border) {
			left_screen_border = left_screen_border - 10f * Gdx.graphics.getDeltaTime();
			batch.draw(cloud_lonely, left_screen_border, 25);


		} else if (left_screen_border < second_right_screen_border) {

			second_right_screen_border = 20f;
			left_screen_border = left_screen_border + 10f * Gdx.graphics.getDeltaTime();
			batch.draw(cloud_lonely, left_screen_border, 25);

			if (left_screen_border > 20f) {
				second_right_screen_border = -150f;
			}

		}

		/* Cloud Movement 2*/


			if (right_screen_border < second_left_screen_border) {
				right_screen_border = right_screen_border + 10f * Gdx.graphics.getDeltaTime();
				batch.draw(cloud_lonely, right_screen_border, 25);

			} else if (right_screen_border > second_left_screen_border) {

				second_left_screen_border = -150f;
				right_screen_border = right_screen_border - 10f * Gdx.graphics.getDeltaTime();
				batch.draw(cloud_lonely, right_screen_border, 25);
				if (right_screen_border < -140f) {
					second_left_screen_border = 20f;
				}

			}









		batch.draw(bottom_clouds, right_screen_border, -25);

		batch.draw(bottom_clouds, left_screen_border, -25);







		batch.end();

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
	public void dispose () {
		batch.dispose();
		sky.dispose();
		img2.dispose();
		mountains.dispose();
		cloud_lonely.dispose();
		bottom_clouds.dispose();
		buttonAtlas.dispose();
		font.dispose();
		skinButton.dispose();
		buttonAtlas.dispose();
		stage.dispose();
	}
}
