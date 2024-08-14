package HUDs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rowg.jackthegiant.GameMain;
import com.sun.org.apache.xpath.internal.operations.String;

import helpers.GamaManager;
import helpers.GameInfo;
import scenes.MainMenu;

/**
 * Created by claud on 20/09/2017.
 */

public class HighScoreButtons
{

    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;

    private Label scoreLabel, coinLabel;

    private ImageButton backBtn;


    public HighScoreButtons(GameMain game)
    {
        this.game =game;


        gameViewport = new FitViewport(GameInfo.width, GameInfo.height,new OrthographicCamera());

        stage = new Stage(gameViewport,game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createAndPositionUIElements();

        stage.addActor(backBtn);
        stage.addActor(scoreLabel);
        stage.addActor(coinLabel);
    }


    void createAndPositionUIElements()
    {
        backBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\8 - Buttons\\2 - Options Buttons\\Back.png"))));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\5 - Fonts\\blow.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 40;

        BitmapFont scoreFont = generator.generateFont(parameter);
        BitmapFont coinFont = generator.generateFont(parameter);



        scoreLabel = new Label(java.lang.String.valueOf(GamaManager.getInstance().gameData.getHighscore()), new Label.LabelStyle(scoreFont, Color.WHITE));
        coinLabel = new Label(java.lang.String.valueOf(GamaManager.getInstance().gameData.getCoinHighScore()), new Label.LabelStyle(coinFont, Color.WHITE));

        scoreLabel.setPosition(GameInfo.width/2f,GameInfo.height/2f -120);
        coinLabel.setPosition(GameInfo.width/2f,GameInfo.height/2f-240);


        backBtn.setPosition(17,17, Align.bottomLeft);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });


    }

    public Stage getStage()
    {
        return this.stage;
    }







}//high score buttons



