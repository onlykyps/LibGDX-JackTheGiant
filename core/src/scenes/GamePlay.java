package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rowg.jackthegiant.GameMain;

import Clouds.Clouds;
import HUDs.UIHud;
import helpers.GamaManager;
import player.player;
import clouds.CloudsController;
import helpers.GameInfo;

/**
 * Created by claud on 05/09/2017.
 */

public class GamePlay implements Screen, ContactListener
{


    private GameMain game;

    private OrthographicCamera mainCamera;
    private Viewport gameViewport;

    private OrthographicCamera box2DCamera;
    private Box2DDebugRenderer debugRenderer;

    private World world;

    private Sprite[] bgs;
    private float lastYPosition;

    private float cameraSpeed=10;
    private float maxSpeed=10;
    private float acceleration=10;

    private boolean touchedForTheFirstTime = false;

    private UIHud hud;

    private CloudsController cloudsController;

    private player player;

    private float lastPlayerY;





    public GamePlay(GameMain game)
    {
        this.game = game;
        mainCamera = new OrthographicCamera(GameInfo.width,GameInfo.height);
        mainCamera.position.set(GameInfo.width/2f,GameInfo.height/2f,0);

        gameViewport = new StretchViewport(GameInfo.width,GameInfo.height,mainCamera);

        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false,GameInfo.width/GameInfo.PPM,GameInfo.height/GameInfo.PPM);
        box2DCamera.position.set(GameInfo.width/2f,GameInfo.height/2f,0);


        debugRenderer = new Box2DDebugRenderer();

        hud = new UIHud(game);


        world = new World(new Vector2(0,-9.8f),true);
        //inform the world that the contact listener is the gameplay class
        world.setContactListener(this);

        cloudsController = new CloudsController(world);


        player = cloudsController.positionThePlayer(player);

        lastPlayerY = player.getY();

        createBackgrounds();

        setCameraSpeed();


    }

    void handleInput(float dt)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            player.movePlayer(-2);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            player.movePlayer(+2);
        }
        else
        {
            player.setWalking(false);
        }
    }

    void checkForFirstTouch()
    {
        if (!touchedForTheFirstTime)
        {
            if (Gdx.input.justTouched())
            {
                touchedForTheFirstTime = true;
                GamaManager.getInstance().isPause=false;
            }
        }
    }

    void update(float dt)
    {
        checkForFirstTouch();
        if (!GamaManager.getInstance().isPause)
        {
            handleInput(dt);
            moveCamera(dt);
            checkBackgroundsOutOfBounds();
            cloudsController.setCameraY(mainCamera.position.y);
            cloudsController.createAndArrangeNewClouds();
            cloudsController.removeOffScreenCollectables();
            checkPlayersBounds();
            countScore();
        }
    }

    void moveCamera(float delta)
    {
        mainCamera.position.y-=cameraSpeed*delta;
        cameraSpeed+=acceleration*delta;
        if(cameraSpeed>maxSpeed)
        {
            cameraSpeed=maxSpeed;
        }
    }

    void setCameraSpeed()
    {
        if(GamaManager.getInstance().gameData.isEasyDifficulty())
        {
            cameraSpeed=80;
            maxSpeed=100;
        }
        else if (GamaManager.getInstance().gameData.isMediumDifficulty())
        {
            cameraSpeed=100;
            maxSpeed=150;
        }
        else if (GamaManager.getInstance().gameData.isHardDifficulty())
        {
            cameraSpeed=120;
            maxSpeed=170;
        }
    }

    void createBackgrounds()
    {
        bgs = new Sprite[3];

        for (int i=0;i<bgs.length;i++)
        {
            bgs[i]=new Sprite(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\7 - Backgrounds\\Game BG.png"));
            bgs[i].setPosition(0,-(i*bgs[i].getHeight()));
            lastYPosition = Math.abs(bgs[i].getY());
        }
    }

    void drawBackgrounds()
    {
        for (int i = 0;i<bgs.length;i++)
        {
            game.getBatch().draw(bgs[i],bgs[i].getX(),bgs[i].getY());
        }
    }

    void checkBackgroundsOutOfBounds()
    {
        for (int i=0;i<bgs.length;i++)
        {
            if((bgs[i].getY()-bgs[i].getHeight()/2-5)> mainCamera.position.y)
            {
                float newPosition = bgs[i].getHeight()+lastYPosition;
                bgs[i].setPosition(0,-newPosition);
                lastYPosition = Math.abs((newPosition));
            }
        }

    }

    void checkPlayersBounds()
    {
        if (player.getY()-GameInfo.height/2 - player.getHeight()/2f > mainCamera.position.y)
        {
            if (!player.isDead())
            {
                playedDied();
            }
        }

        if (player.getY()+GameInfo.height/2f+player.getHeight()/2f <mainCamera.position.y)
        {
            if (!player.isDead())
            {
                playedDied();
            }
        }

         if (player.getX()>GameInfo.width)
         {
             if (!player.isDead())
             {
                 playedDied();
             }
         }
         else if (player.getX()<0)
         {
             if (!player.isDead())
             {
                 playedDied();
             }
         }
    }

    void countScore()
    {
        if (lastPlayerY>player.getY())
        {
            hud.incrementScore(1);
            lastPlayerY = player.getY();
        }
    }

    void playedDied ()
    {
        GamaManager.getInstance().isPause = true;
        hud.decrementLife();
        //decrement life count
        player.setDead(true);
        player.setPosition(1000,1000);

        if (GamaManager.getInstance().lifeScore<0)
        {
            //no more lives left to continue the game

            //check if we have a new high-score
            GamaManager.getInstance().checkForNewHS();

            //show end-score to the user
            hud.createGameOverPanel();

            //load main menu

            RunnableAction run = new RunnableAction();
            run.setRunnable(new Runnable() {
                @Override
                public void run()
                {
                    game.setScreen(new MainMenu(game));
                }
            });

            SequenceAction sa = new SequenceAction();
            sa.addAction(Actions.delay(3f));
            sa.addAction(Actions.fadeOut(1f));
            sa.addAction(run);

            hud.getStage().addAction(sa);


        }
        else
        {
            //reload the game so the player can continue to play
            RunnableAction run = new RunnableAction();
            run.setRunnable(new Runnable() {
                @Override
                public void run()
                {
                    game.setScreen(new GamePlay(game));
                }
            });

            SequenceAction sa = new SequenceAction();
            sa.addAction(Actions.delay(3f));
            sa.addAction(Actions.fadeOut(1f));
            sa.addAction(run);

            hud.getStage().addAction(sa);
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta)
    {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        drawBackgrounds();
        cloudsController.drawClouds(game.getBatch());
        cloudsController.drawCollectables(game.getBatch());
        player.drawPlayerIdle(game.getBatch());
        player.drawPlayerAnimation(game.getBatch());


        game.getBatch().end();

        debugRenderer.render(world,box2DCamera.combined);

        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();
        hud.getStage().act();

        game.getBatch().setProjectionMatrix(mainCamera.combined);
        mainCamera.update();



        player.updatePlayer();
        world.step(Gdx.graphics.getDeltaTime(),6,2);


    }

    @Override
    public void resize(int width, int height)
    {
        gameViewport.update(width,height);
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

        world.dispose();
        for (int i=0;i< bgs.length;i++)
        {
            bgs[i].getTexture().dispose();
        }

        player.getTexture().dispose();
        debugRenderer.dispose();

    }

    @Override
    public void beginContact(Contact contact)
    {
        Fixture body1, body2;

        if (contact.getFixtureA().getUserData() == "Player")
        {
            body1 = contact.getFixtureA();
            body2 = contact.getFixtureB();

        }
        else
        {
            body1 = contact.getFixtureB();
            body2 = contact.getFixtureA();
        }

        if (body1.getUserData() == "Player" && body2.getUserData() == "Coin")
        {
            //collided with the coin
            body2.setUserData("Remove");
            hud.incrementCoins();
            cloudsController.removeCollectables();

        }

        if (body1.getUserData() == "Player" && body2.getUserData() == "Life")
        {
            //collided with life
            body2.setUserData("Remove");
            hud.incrementLife();
            cloudsController.removeCollectables();

        }

        if (body1.getUserData() == "Player" && body2.getUserData() == "Dark Cloud")
        {
            if(!player.isDead())
            {
                playedDied();
            }

        }




    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
} //gameplay
