package helpers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.sun.org.apache.bcel.internal.generic.INEG;

/**
 * Created by claud on 27/09/2017.
 */

public class GamaManager {
    private static final GamaManager ourInstance = new GamaManager();

    public GameData gameData;
    private Json json = new Json();

    private FileHandle fileHandle = Gdx.files.local("bin\\GameData.json");

    public boolean gameStartedFromMainMenu, isPause = true;
    public int lifeScore, coinScore, regularScore;

    private GamaManager()
    {

    }

    public void initializeGameData()
    {
        if(!fileHandle.exists())
        {
            gameData = new GameData();
            gameData.setHighscore(0);
            gameData.setCoinHighScore(0);
            gameData.setEasyDifficulty(false);
            gameData.setMediumDifficulty(true);
            gameData.setHardDifficulty(false);
            gameData.setMusicOn(false);

            saveData();

        }
        else
        {
            loadData();
        }
    }

    public void saveData()
    {
        if (gameData != null)
        {
            fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(gameData)),false);
        }
    }

    public void loadData()
    {
        gameData = json.fromJson(GameData.class,Base64Coder.decodeString(fileHandle.readString()));
    }

    public void checkForNewHS()
    {
        int oldHS = gameData.getHighscore();
        int oldCoinHS = gameData.getCoinHighScore();

        if (oldHS<regularScore)
        {
            gameData.setHighscore(regularScore);
        }
        if (oldCoinHS<coinScore)
        {
            gameData.setCoinHighScore(coinScore);
        }
    }

    public static GamaManager getInstance() {
        return ourInstance;
    }

} // gama manager

