package com.me.mygdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyStart extends Game {

	private SpriteBatch batch;
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	
	public static final float GAME_WIDTH = 1024;
	public static final float GAME_HEIGHT = 578;
	
	public static MyStart instance;
	
	public MyStart() {
	}
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		//TextureLoader.Initialization();
		menuScreen = new MenuScreen(batch);
		gameScreen = new GameScreen(batch);
		
		setScreen(gameScreen);
		instance = this;
	}	
	
	public void showMenu() {
		setScreen(menuScreen);
	}
	
	public void showGame() {
		gameScreen = new GameScreen(batch);
		setScreen(gameScreen);
	}
	
}
