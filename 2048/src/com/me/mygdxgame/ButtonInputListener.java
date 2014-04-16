package com.me.mygdxgame;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class ButtonInputListener extends InputListener {
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		MyStart.instance.showGame();
		return true;
	}
}
