package helpers;

/**
 * Created by claud on 04/10/2017.
 */

public class GameData
{

    private int highscore;
    private int coinHighScore;

    private boolean easyDifficulty;
    private boolean mediumDifficulty;
    private boolean hardDifficulty;
    private boolean musicOn;


    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public int getCoinHighScore() {
        return coinHighScore;
    }

    public void setCoinHighScore(int coinHighScore) {
        this.coinHighScore = coinHighScore;
    }

    public boolean isEasyDifficulty() {
        return easyDifficulty;
    }

    public void setEasyDifficulty(boolean easyDifficulty) {
        this.easyDifficulty = easyDifficulty;
    }

    public boolean isMediumDifficulty() {
        return mediumDifficulty;
    }

    public void setMediumDifficulty(boolean mediumDifficulty) {
        this.mediumDifficulty = mediumDifficulty;
    }

    public boolean isHardDifficulty() {
        return hardDifficulty;
    }

    public void setHardDifficulty(boolean hardDifficulty) {
        this.hardDifficulty = hardDifficulty;
    }

    public boolean isMusicOn() {
        return musicOn;
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }
} //game data
