package com.rowg.jackthegiant;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helpers.GamaManager;
import scenes.GamePlay;
import scenes.MainMenu;

public class GameMain extends Game {
	SpriteBatch batch;



	
	@Override
	public void create () {
		batch = new SpriteBatch();
		GamaManager.getInstance().initializeGameData();
        setScreen(new MainMenu(this));
	}

	@Override
	public void render ()
	{
		super.render();
	}

	public SpriteBatch getBatch()
    {
        return this.batch;
    }
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
