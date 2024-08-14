package HUDs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rowg.jackthegiant.GameMain;

import org.omg.CORBA.PRIVATE_MEMBER;

import helpers.GamaManager;
import helpers.GameInfo;
import scenes.GamePlay;
import scenes.HighScore;
import scenes.MainMenu;
import scenes.Options;

/**
 * Created by claud on 14/09/2017.
 */

public class MainMenuButtons
{
    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;

    private ImageButton playBtn;
    private ImageButton highscoreBTN;
    private ImageButton optionsBtn;
    private ImageButton quitBtn;
    private ImageButton musicBtn;

    public MainMenuButtons(GameMain game)
    {
        this.game = game;

        gameViewport = new FitViewport(GameInfo.width,GameInfo.height,new OrthographicCamera());
        stage = new Stage(gameViewport,game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createAndPositionButtons();

        addAllListeners();

        stage.addActor(playBtn);
        stage.addActor(highscoreBTN);
        stage.addActor(musicBtn);
        stage.addActor(optionsBtn);
        stage.addActor(quitBtn);

    }

    void createAndPositionButtons()
    {
        playBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\8 - Buttons\\3 - Main Menu Buttons\\Start Game.png"))));
        highscoreBTN = new ImageButton(new SpriteDrawable(new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\8 - Buttons\\3 - Main Menu Buttons\\Highscore.png"))));
        musicBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\8 - Buttons\\3 - Main Menu Buttons\\Music On.png"))));
        optionsBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\8 - Buttons\\3 - Main Menu Buttons\\Options.png"))));
        quitBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\8 - Buttons\\3 - Main Menu Buttons\\Quit.png"))));

        playBtn.setPosition(GameInfo.width/2f,GameInfo.height/2f+160, Align.center);
        highscoreBTN.setPosition(GameInfo.width/2f,GameInfo.height/2f+100, Align.center);
        musicBtn.setPosition(GameInfo.width/2f,GameInfo.height/2f-100, Align.center);
        optionsBtn.setPosition(GameInfo.width/2f,GameInfo.height/2f+40, Align.center);
        quitBtn.setPosition(GameInfo.width/2f,GameInfo.height/2f-20, Align.center);


    }

    void addAllListeners()
    {
        playBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //any code that we type will be executed when we press the play button

                GamaManager.getInstance().gameStartedFromMainMenu = true;

                RunnableAction run = new RunnableAction();
                run.setRunnable(new Runnable() {
                    @Override
                    public void run()
                    {
                        game.setScreen(new GamePlay(game));
                    }
                });

                SequenceAction sa = new SequenceAction();
                //sa.addAction(Actions.delay(3f));
                sa.addAction(Actions.fadeOut(1f));
                sa.addAction(run);

                stage.addAction(sa);

            }
        });

        highscoreBTN.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //any code that we type will be executed when we press the play button

                game.setScreen(new HighScore(game));




            }
        });

        musicBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //any code that we type will be executed when we press the play button


            }
        });

        optionsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //any code that we type will be executed when we press the play button

                game.setScreen(new Options(game));


            }
        });

        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //any code that we type will be executed when we press the play button


            }
        });
    }

    public Stage getStage()
    {
        return this.stage;
    }







}//main mennu btns
