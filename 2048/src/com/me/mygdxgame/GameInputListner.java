package com.me.mygdxgame;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class GameInputListner extends InputListener {
	private GameScreen game;
	private Vector2 temp;
	public GameInputListner(GameScreen game) {
		this.game = game;
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		temp = new Vector2(x, y);
		return true;
	}
	
	@Override
	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		temp.sub(x,y);
		if(temp.angle() > 135 && temp.angle() < 225) game.moveField(new Vector2(1,0));
		if(temp.angle() < 45 || temp.angle() > 315) game.moveField(new Vector2(-1,0));
		
		if(temp.angle() > 45 && temp.angle() < 135) game.moveField(new Vector2(0,-1));
		if(temp.angle() > 225 && temp.angle() < 315) game.moveField(new Vector2(0,1));
	}

	@Override
	public void touchDragged (InputEvent event, float x, float y, int pointer) {
	
	}
}
