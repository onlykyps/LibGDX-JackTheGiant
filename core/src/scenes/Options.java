package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rowg.jackthegiant.GameMain;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.swing.text.View;

import HUDs.OptionsButtons;
import helpers.GameInfo;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

/**
 * Created by claud on 21/09/2017.
 */

public class Options implements Screen
{

    private GameMain game;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;
    private Texture bg;

    private OptionsButtons btns;


    public Options(GameMain game)
    {
        this.game = game;

        mainCamera = new OrthographicCamera();
        mainCamera.setToOrtho(false, GameInfo.width,GameInfo.height);
        mainCamera.position.set(GameInfo.width/2f, GameInfo.height/2f,0);

        gameViewport = new StretchViewport(GameInfo.width,GameInfo.height,mainCamera);

        bg = new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\7 - Backgrounds\\Options BG.png");

        btns = new OptionsButtons(game);


    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(bg,0,0);
        game.getBatch().end();

        game.getBatch().setProjectionMatrix(btns.getStage().getCamera().combined);
        btns.getStage().draw();

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
    public void dispose()
    {
        bg.dispose();
        btns.getStage().dispose();
    }
} // options
