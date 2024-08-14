package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rowg.jackthegiant.GameMain;

import HUDs.MainMenuButtons;
import helpers.GameInfo;

/**
 * Created by claud on 14/09/2017.
 */

public class MainMenu implements Screen
{

    private GameMain game;
    private OrthographicCamera mainCamera;
    private Viewport gameViewPort;

    private Texture bg;

    private MainMenuButtons btns;

    public MainMenu(GameMain game)
    {
        this.game = game;

        // mainCamera = new OrthographicCamera(GameInfo.width,GameInfo.height);
        mainCamera = new OrthographicCamera();
        mainCamera.setToOrtho(false,GameInfo.width,GameInfo.height);
        mainCamera.position.set(GameInfo.width/2f,GameInfo.height/2f,0);

        gameViewPort = new StretchViewport(GameInfo.width,GameInfo.height,mainCamera);

        bg= new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\7 - Backgrounds\\Menu BG.png");

        btns = new MainMenuButtons(game);


    }






    @Override
    public void show() {

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        game.getBatch().draw(bg,0,0);

        game.getBatch().end();

        game.getBatch().setProjectionMatrix(btns.getStage().getCamera().combined);

        btns.getStage().draw();
        btns.getStage().act();
    }

    @Override
    public void resize(int width, int height)
    {
        gameViewPort.update(width,height);

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
        bg.dispose();
        btns.getStage().dispose();
    }
} //main menu
