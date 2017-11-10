package tk.droncompany;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

/**
 * Created by dyach on 11.09.2016.
 */
public class MainMenuScreen implements Screen {

    final SouthPark game;
    Texture imgBackground2;
    OrthographicCamera camera;
    Rectangle background2;

    public MainMenuScreen(final SouthPark gam) {
        this.game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        imgBackground2 = new Texture("background2.jpg");
        background2 = new Rectangle();
        background2.x = 0;
        background2.y = 0;
        background2.height = 720;
        background2.width = 1280;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(imgBackground2, background2.x, background2.y);
        game.font.draw(game.batch, "Catch pizza!", 100, 710);
        game.font.draw(game.batch, "Touch screen for play!", 100, 690);
        game.batch.end();
        if(Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
            dispose();
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

    }
}
