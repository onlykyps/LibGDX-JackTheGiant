package HUDs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rowg.jackthegiant.GameMain;
import com.sun.org.apache.bcel.internal.generic.AllocationInstruction;

import javax.swing.text.View;

import helpers.GamaManager;
import helpers.GameInfo;
import scenes.MainMenu;
import scenes.Options;

/**
 * Created by claud on 21/09/2017.
 */

public class OptionsButtons
{
    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;

    private ImageButton easy, medium, hard, backBtn;
    private Image sign;

    public OptionsButtons (GameMain game)
    {
        this.game=game;

        gameViewport = new FitViewport(GameInfo.width,GameInfo.height, new OrthographicCamera());

        stage = new Stage(gameViewport,game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createAndPositionButtons();
        addAllListeners();

        stage.addActor(easy);
        stage.addActor(hard);
        stage.addActor(medium);
        stage.addActor(backBtn);
        stage.addActor(sign);
    }

    void  createAndPositionButtons()
    {
        easy = new ImageButton(new SpriteDrawable(new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\8 - Buttons\\2 - Options Buttons\\Easy.png"))));
        medium = new ImageButton(new SpriteDrawable(new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\8 - Buttons\\2 - Options Buttons\\Medium.png"))));
        hard = new ImageButton(new SpriteDrawable(new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\8 - Buttons\\2 - Options Buttons\\Hard.png"))));

        sign = new Image(new SpriteDrawable(new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\8 - Buttons\\2 - Options Buttons\\Check Sign.png"))));
        backBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\8 - Buttons\\2 - Options Buttons\\Back.png"))));

        backBtn.setPosition(17,17,Align.bottomLeft);

        easy.setPosition(GameInfo.width/2f,GameInfo.height/2f+40, Align.center);
        medium.setPosition(GameInfo.width/2f,GameInfo.height/2f-60, Align.center);
        hard.setPosition(GameInfo.width/2f,GameInfo.height/2f-130, Align.center);
        positionTheSign();

       // sign.setPosition(GameInfo.width/2f+75,medium.getY()+15, Align.bottomLeft);

    }

    void addAllListeners()
    {
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {

                game.setScreen(new MainMenu(game));
            }
        });

        easy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                changeDifficultyMethod(0);
                sign.setY(easy.getY()+15);
            }
        });

        medium.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                changeDifficultyMethod(1);
                sign.setY(medium.getY()+15);

            }
        });

        hard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                changeDifficultyMethod(2);
                sign.setY(hard.getY()+15);

            }
        });

    }

    void positionTheSign()
    {
        if (GamaManager.getInstance().gameData.isEasyDifficulty())
        {
            sign.setPosition(GameInfo.width/2f+90,easy.getY()+15, Align.center);

        }
        else if (GamaManager.getInstance().gameData.isMediumDifficulty())
        {
            sign.setPosition(GameInfo.width/2f+90,medium.getY()+15, Align.center);

        }
        else if (GamaManager.getInstance().gameData.isHardDifficulty())
        {
            sign.setPosition(GameInfo.width/2f+90,hard.getY()+15, Align.center);

        }

    }

    void changeDifficultyMethod(int difficulty)
    {
        switch (difficulty)
        {
            case 0:
                GamaManager.getInstance().gameData.setEasyDifficulty(true);
                GamaManager.getInstance().gameData.setMediumDifficulty(false);
                GamaManager.getInstance().gameData.setHardDifficulty(false);

                break;

            case 1:
                GamaManager.getInstance().gameData.setEasyDifficulty(false);
                GamaManager.getInstance().gameData.setMediumDifficulty(true);
                GamaManager.getInstance().gameData.setHardDifficulty(false);

                break;

            case 2:
                GamaManager.getInstance().gameData.setEasyDifficulty(false);
                GamaManager.getInstance().gameData.setMediumDifficulty(false);
                GamaManager.getInstance().gameData.setHardDifficulty(true);

                break;
        }

        GamaManager.getInstance().saveData();
    }

        public Stage getStage()
        {
            return this.stage;
        }









} //options buttons
