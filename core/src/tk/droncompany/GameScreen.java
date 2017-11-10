package tk.droncompany;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.org.apache.bcel.internal.generic.D2F;

import java.util.Iterator;

public class GameScreen implements Screen {
	final SouthPark game;
	SpriteBatch batch;
	Texture imgCow;
	Texture imgCartman;
	Texture imgCar;
	Texture imgPizza;
	Texture imgBackground;
	Sound hrum;
	Sound cartmanVoice;
	Vector3 touchPosition;
	Rectangle cow;
	Rectangle cartman;
	Rectangle car;
	Rectangle background;
	OrthographicCamera camera;
	Array<Rectangle> pizzas;
	long lastPizzaDrop;
	int catchedPizza;
	int record;
	long frequency = 999999999;
	int speed = 200;


	public GameScreen (final SouthPark gam) {
		this.game = gam;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		batch = new SpriteBatch();
		touchPosition = new Vector3();
		imgBackground = new Texture("background.jpg");
		imgCartman = new Texture("eric.png");
		imgCar = new Texture("car.png");
		imgPizza = new Texture("pizza.png");
		imgCow = new Texture("cow.png");
		hrum = Gdx.audio.newSound(Gdx.files.internal("hrum.mp3"));
		cartmanVoice = Gdx.audio.newSound(Gdx.files.internal("Erik_Cartman-Laughing.mp3"));
		cartman = new Rectangle();
		cartman.x = 1280/2 - 128/2;
		cartman.y = 10;
		cartman.width = 128;
		cartman.height = 128;
		background = new Rectangle();
		background.x = 0;
		background.y = 0;
		background.width = 720;
		background.height = 1280;

		car = new Rectangle();
		car.x = 1280*3;
		car.y = 100;
		car.width = 128;
		car.height = 128;

		cow = new Rectangle();
		cow.x = -1280;
		cow.y = 720-128;
		cow.width = 720;
		cow.height = 1280;
		pizzas = new Array<Rectangle>();
		newPizza();
	}
	private void newPizza(){
		Rectangle pizza = new Rectangle();
		pizza.y = 720;
		pizza.x = MathUtils.random(0,1280-128);
		pizza.height = 128;
		pizza.width = 128;
		pizzas.add(pizza);
		lastPizzaDrop = TimeUtils.nanoTime();

	}

	@Override
	public void show() {

	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		game.batch.draw(imgBackground, background.x, background.y);
		game.batch.draw(imgCow, cow.x, cow.y);
		game.batch.draw(imgCar, car.x, car.y);
		game.batch.draw(imgCartman, cartman.x, cartman.y);
		game.font.draw(game.batch, "Amount of pizzas: " + catchedPizza, 10, 20);
		game.font.draw(game.batch, "Record: " + record, 200, 20);

		for(Rectangle pizza: pizzas){
			game.batch.draw(imgPizza, pizza.x, pizza.y);
		}
		game.batch.end();

		if(Gdx.input.isTouched()){
			touchPosition.set(Gdx.input.getX(), Gdx.input.getY(),0);
			camera.unproject(touchPosition);
			cartman.x = (int) touchPosition.x- 128/2;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){ cartman.x -= 600 * Gdx.graphics.getDeltaTime();}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) cartman.x += 600 * Gdx.graphics.getDeltaTime();
		if(cartman.x < 0) cartman.x = 0;
		if(cartman.x > 1280-128) cartman.x = 1280-128;
		if(cow.x > 1280) cow.x = -128*2;
		if(car.x < -500) car.x = 1280*3;
		if(catchedPizza<10){
			frequency = 1000000000;
			speed = 300;
		}
		if(catchedPizza>10 && catchedPizza<30){
			frequency = 500000000;///для 20 і 30
			speed = 400;
		}

		if(catchedPizza >30 && catchedPizza<50){
			frequency = 500000000;// для 30 і 50
			speed = 450;
		}
		if(catchedPizza >50){
			frequency = 300000000;// для 50>
			speed = 450;
		}

		if(TimeUtils.nanoTime() - lastPizzaDrop > frequency) newPizza();

		Iterator<Rectangle> iterator = pizzas.iterator();
			while(iterator.hasNext()){
				Rectangle pizza = iterator.next();
				cow.x += 10*Gdx.graphics.getDeltaTime();
				car.x -= 50*Gdx.graphics.getDeltaTime();
				pizza.y -= speed*Gdx.graphics.getDeltaTime();
				if(pizza.y + 128 <0){
					iterator.remove();
					cartmanVoice.play();
					if(record<catchedPizza) {
						record = catchedPizza;
					}
					catchedPizza=0;
				}
				if(pizza.overlaps(cartman)){
					hrum.play();
					catchedPizza++;
					iterator.remove();
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
		imgPizza.dispose();
		imgCartman.dispose();
		cartmanVoice.dispose();
		batch.dispose();
	}
}
