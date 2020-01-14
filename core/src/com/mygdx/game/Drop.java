package com.badlogic.drop;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Screen;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyGdxGameOver;
import com.mygdx.game.MyGdxStarter;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Drop implements Screen {
	private Texture dropImage;
	private Texture background;
	private Texture baddroplet;
	private Texture bucketImage;
	private Texture backgroundclouds;
	private Texture deathraindropImage;
	private Sound dropSound;
	private Music rainMusic;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Rectangle bucket;
	private Array<Rectangle> raindrops;
	private Array<Rectangle> badraindrops;
	private Array<Rectangle> deathraindrops;
	private long lastDropTime;
	private long lastDropTime2;
	private TextButton button;
	private TextButton.TextButtonStyle textButtonStyle;
	private Skin skinButton;
	private TextureAtlas buttonAtlas;
	private BitmapFont font;
	private long lastDropTime3;

    Texture sky;
	Texture img2;
	Texture mountains;
	Texture cloud_lonely;
	float left_screen_border = 20f;
	float z = 0;
	float right_screen_border = 500f;
	float second_left_screen_border = 20f;
	float second_right_screen_border = -150f;

	private int score;
	private String ScoreName;
	BitmapFont ScoreBitmapFontName;

	private Stage stage;
	private Game game;

	public Drop (Game aGame) {
		game = aGame;
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		score = 0;
		ScoreName = "score: 0";
		ScoreBitmapFontName = new BitmapFont();
		ScoreBitmapFontName.getData().setScale(2,2);

		font = new BitmapFont();
		skinButton = new Skin();
		buttonAtlas = new TextureAtlas("freezing/skin/freezing-ui.atlas");
		skinButton.addRegions(buttonAtlas);
		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = font;

		textButtonStyle.up = skinButton.getDrawable("button");
		textButtonStyle.down = skinButton.getDrawable("button-down");
		textButtonStyle.checked = skinButton.getDrawable("checkbox");



		button = new TextButton("Menu", textButtonStyle);
		button.setText("Menu");
		button.setHeight(50);
		button.setWidth(50);
		button.setPosition(330, 155);

		button.addListener(new InputListener(){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new MyGdxGame(game));
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		stage.addActor(button);
		batch = new SpriteBatch();
		sky = new Texture("sky_lightened.png");
		img2 = new Texture("clouds_BG.png");
		mountains = new Texture("mountains.png");
		cloud_lonely = new Texture("clouds_MG_1.png");
		background = new Texture("backgroundCastles.png");
		backgroundclouds = new Texture("clouds_MG_2.png");


		// load the images for the droplet and the bucket, 64x64 pixels each
        deathraindropImage = new Texture(Gdx.files.internal("deathdroplet.png"));
		baddroplet = new Texture(Gdx.files.internal("baddroplet.png"));
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));


		//dropSound = Gdx.audio.newSound(Gdx.files.internal("audi.mp3"));
		//rainMusic = Gdx.audio.newMusic(Gdx.files.internal("Blues.mp3"));


		//rainMusic.setLooping(true);
		//rainMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);


		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;


		raindrops = new Array<Rectangle>();
		badraindrops = new Array<Rectangle>();
		deathraindrops = new Array<Rectangle>();

		spawnRaindrop();
		spawnbadraindrops();
		spawndeathraindrops();
	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}


    private void spawndeathraindrops() {
        Rectangle deathraindrop = new Rectangle();
        deathraindrop.x = MathUtils.random(0, 800-64);
        deathraindrop.y = 480;
        deathraindrop.width = 64;
        deathraindrop.height = 64;
        deathraindrops.add(deathraindrop);
        lastDropTime3 = TimeUtils.nanoTime();
    }


	private void spawnbadraindrops() {
		Rectangle badraindrop = new Rectangle();
		badraindrop.x = MathUtils.random(0, 800-64);
		badraindrop.y = 200;
		badraindrop.width = 64;
		badraindrop.height = 64;
		badraindrops.add(badraindrop);
		lastDropTime2 = TimeUtils.nanoTime();
	}
	public void show() {
		Gdx.input.setInputProcessor(stage);

	}



	@Override
	public void render(float delta) {
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



		camera.update();


		batch.setProjectionMatrix(camera.combined);


		batch.begin();

		batch.enableBlending();
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		/* Cloud Movement*/

		batch.draw(background, 0, -220);

		ScoreBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		ScoreBitmapFontName.draw(batch, ScoreName, 0, 25);

		if (left_screen_border > second_right_screen_border) {
			left_screen_border = left_screen_border - 10f * Gdx.graphics.getDeltaTime();
			batch.draw(cloud_lonely, left_screen_border, 450, cloud_lonely.getWidth(), cloud_lonely.getHeight());


		} else if (left_screen_border < second_right_screen_border) {

			second_right_screen_border = 20f;
			left_screen_border = left_screen_border + 10f * Gdx.graphics.getDeltaTime();
			batch.draw(cloud_lonely, left_screen_border, 450, cloud_lonely.getWidth(), cloud_lonely.getHeight());

			if (left_screen_border > 20f) {
				second_right_screen_border = -150f;
			}

		}

		/* Cloud Movement 2*/


		if (right_screen_border < second_left_screen_border) {
			right_screen_border = right_screen_border + 10f * Gdx.graphics.getDeltaTime();
			batch.draw(cloud_lonely, right_screen_border, 450);

		} else if (right_screen_border > second_left_screen_border) {

			second_left_screen_border = -150f;
			right_screen_border = right_screen_border - 10f * Gdx.graphics.getDeltaTime();
			batch.draw(cloud_lonely, right_screen_border, 450);
			if (right_screen_border < -140f) {
				second_left_screen_border = 20f;
			}

		}







		batch.draw(bucketImage, bucket.x, bucket.y);
		for(Rectangle raindrop: raindrops) {
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}

		batch.draw(bucketImage, bucket.x, bucket.y);
		for(Rectangle badraindrop: badraindrops) {
			batch.draw(baddroplet, badraindrop.x, badraindrop.y);
		}

		batch.draw(bucketImage, bucket.x, bucket.y);
		for(Rectangle deathraindrop: deathraindrops) {
			batch.draw(deathraindropImage, deathraindrop.x, deathraindrop.y);
		}

		batch.end();
		stage.draw();


		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();


		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 - 64) bucket.x = 800 - 64;


		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
		if(TimeUtils.nanoTime() - lastDropTime2 > 1000000000) spawnbadraindrops();
		if(TimeUtils.nanoTime() - lastDropTime3 > 1000000000) spawndeathraindrops();



		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0) iter.remove();
			if(raindrop.overlaps(bucket)) {
				score++;
				ScoreName = "score: " + score;
				//dropSound.play();
				iter.remove();
			}
		}

		for (Iterator<Rectangle> iter = badraindrops.iterator(); iter.hasNext(); ) {
			Rectangle badraindrop = iter.next();
			badraindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(badraindrop.y + 64 < 0) iter.remove();
			if(badraindrop.overlaps(bucket)) {
				score--;
				ScoreName = "score: " + score;
				//dropSound.play();
				iter.remove();
			}
		}

        for (Iterator<Rectangle> iter = deathraindrops.iterator(); iter.hasNext(); ) {
            Rectangle deathraindrop = iter.next();
            deathraindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if(deathraindrop.y + 64 < 0) iter.remove();
            if(deathraindrop.overlaps(bucket)) {
				game.setScreen(new MyGdxGameOver(game));
                score--;
                ScoreName = "score: " + score;
                //dropSound.play();
                iter.remove();
            }
        }



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
		// dispose of all the native resources
		dropImage.dispose();
		baddroplet.dispose();
		deathraindropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		skinButton.dispose();
		font.dispose();
		buttonAtlas.dispose();
		rainMusic.dispose();
		batch.dispose();
		stage.dispose();
	}
}