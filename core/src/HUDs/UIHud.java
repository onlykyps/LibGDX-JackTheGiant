package HUDs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rowg.jackthegiant.GameMain;
import com.sun.org.apache.xpath.internal.operations.String;

import helpers.GamaManager;
import helpers.GameInfo;
import scenes.MainMenu;

import static com.sun.org.apache.xpath.internal.operations.String.*;
import static java.lang.String.valueOf;

/**
 * Created by claud on 22/09/2017.
 */

public class UIHud
{

    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;


    private Image coinImage, scoreImage, lifeImage, pausePanel;

    private Label coinLabel, lifeLabel, scoreLabel;

    private ImageButton pauseBtn, pause, resumeBtn,quitBtn;


    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;



    public UIHud(GameMain game)
    {
        this.game = game;

        gameViewport = new FitViewport(GameInfo.width,GameInfo.height,new OrthographicCamera());


        stage = new Stage(gameViewport,game.getBatch());

        Gdx.input.setInputProcessor(stage);

        if (GamaManager.getInstance().gameStartedFromMainMenu)
        { //this is the first time the game is started, setting initial values
            GamaManager.getInstance().gameStartedFromMainMenu = false;
            GamaManager.getInstance().lifeScore=2;
            GamaManager.getInstance().coinScore=0;
            GamaManager.getInstance().regularScore=0;
        }


        createImages();
        createLabels();

        //createBtnAndAddListener();
        create();
        pauseButtonListener();

    }

    public void create()
    {
        myTexture =  new Texture(Gdx.files.internal("D:\\\\Dropbox\\\\My Games\\\\Jack The Giant\\\\android\\\\assets\\\\8 - Buttons\\\\1 - Gameplay Buttons\\\\Pause.png"));
        myTextureRegion =  new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable);

        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);
    }

    void createLabels()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\5 - Fonts\\blow.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;

        BitmapFont font = generator.generateFont(parameter);

        coinLabel = new Label("x" + GamaManager.getInstance().coinScore,new Label.LabelStyle(font, Color.WHITE));
        lifeLabel = new Label("x" + GamaManager.getInstance().lifeScore,new Label.LabelStyle(font, Color.WHITE));
        scoreLabel = new Label(valueOf(GamaManager.getInstance().regularScore),new Label.LabelStyle(font, Color.WHITE));


        Table lifeAndCoinTable = new Table();
        lifeAndCoinTable.top().left();
        lifeAndCoinTable.setFillParent(true);

        lifeAndCoinTable.add(lifeImage).padLeft(15).padTop(15);
        lifeAndCoinTable.add(lifeLabel).padLeft(15).padTop(5);
        lifeAndCoinTable.row();

        lifeAndCoinTable.add(coinImage).padLeft(15).padTop(15);
        lifeAndCoinTable.add(coinLabel).padLeft(15).padTop(15);

        Table scoreTable = new Table();
        scoreTable.top().right();
        scoreTable.setFillParent(true);

        scoreTable.add(scoreImage).padRight(30).padTop(15);
        scoreTable.row();
        scoreTable.add(scoreLabel).padRight(30).padTop(15);

        stage.addActor(lifeAndCoinTable);

        stage.addActor(scoreTable);

        //stage.addActor(pause);



    }

    void createImages()
    {
        coinImage = new Image(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\2 - Collectables\\Coin.png"));
        lifeImage = new Image(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\2 - Collectables\\Life.png"));
        scoreImage = new Image(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\8 - Buttons\\1 - Gameplay Buttons\\Score.png"));

    }


    /* * void createBtnAndAddListener()
    {
        pause = new ImageButton(new SpriteDrawable(new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\8 - Buttons\\2 - Options Buttons\\Check Sign.png"))));
        //pauseBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\8 - Buttons\\1 - Gameplay Buttons\\Pause.png"))));

        pause.setPosition(470,17, Align.bottomRight);

        pause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //pause the game
                createPausePanel();
            }
        });
    } */

    void createPausePanel()
    {
        pausePanel = new Image(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\1 - Pause Panel And Buttons\\Pause Panel.png"));
        resumeBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\1 - Pause Panel And Buttons\\Resume.png"))));
        quitBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\1 - Pause Panel And Buttons\\Quit 2.png"))));

        pausePanel.setPosition(GameInfo.width/2f,GameInfo.height/2f, Align.center);
        resumeBtn.setPosition(GameInfo.width/2f,GameInfo.height/2f+50, Align.center);
        quitBtn.setPosition(GameInfo.width/2f,GameInfo.height/2f-80, Align.center);

        resumeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                removePausePanel();
                GamaManager.getInstance().isPause=false;

            }
        });

        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(new MainMenu(game));

            }
        });

        stage.addActor(pausePanel);
        stage.addActor(resumeBtn);
        stage.addActor(quitBtn);

    }

    void pauseButtonListener()
    {
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!GamaManager.getInstance().isPause)
                {
                    GamaManager.getInstance().isPause=true;
                    createPausePanel();
                }

            }
        });
    }

    void removePausePanel()
    {
        pausePanel.remove();
        resumeBtn.remove();
        quitBtn.remove();
    }

    public void createGameOverPanel()
    {
        Image gameOverPanel = new Image(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\1 - Pause Panel And Buttons\\Show Score.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\5 - Fonts\\blow.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size=70;

        BitmapFont font = generator.generateFont(parameter);

        Label endScore = new Label(valueOf(GamaManager.getInstance().regularScore),new Label.LabelStyle(font,Color.WHITE));

        Label endCoinScore = new Label(valueOf(GamaManager.getInstance().coinScore),new Label.LabelStyle(font,Color.WHITE));

        gameOverPanel.setPosition(GameInfo.width/2f,GameInfo.height/2f,Align.center);
        endScore.setPosition(GameInfo.width/2f-30,GameInfo.height/2f+20, Align.center);
        endCoinScore.setPosition(GameInfo.width/2f-30,GameInfo.height/2f-90,Align.center);

        stage.addActor(gameOverPanel);
        stage.addActor(endScore);
        stage.addActor(endCoinScore);



    }

    public void incrementScore(int score)
    {
        GamaManager.getInstance().regularScore+=score;
        scoreLabel.setText(valueOf(GamaManager.getInstance().regularScore));
    }

    public void incrementCoins()
    {
        GamaManager.getInstance().coinScore++;
        coinLabel.setText("x"+ GamaManager.getInstance().coinScore);
        incrementScore(200);
    }

    public void incrementLife()
    {
        GamaManager.getInstance().lifeScore++;
        lifeLabel.setText("x"+ GamaManager.getInstance().lifeScore);
        incrementScore(300);
    }

    public void decrementLife()
    {
        GamaManager.getInstance().lifeScore--;
        if (GamaManager.getInstance().lifeScore>=0)
        {
            lifeLabel.setText("x" + GamaManager.getInstance().lifeScore);
        }
    }

    public Stage getStage()
    {
        return this.stage;
    }




} // UI Hud

