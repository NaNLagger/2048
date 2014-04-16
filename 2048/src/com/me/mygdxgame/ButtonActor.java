package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

public class ButtonActor extends TextureActor {
	private int value;
	private Vector2 position;

	public ButtonActor(Texture toDraw, Vector2 position) {
		super(toDraw);
		this.value = 0;
		this.position = position;
		setName(value + "");
        int otstup = GameScreen.sizeField/32;
        setSize((GameScreen.sizeField-otstup*5)/4, (GameScreen.sizeField-otstup*5)/4);
        setOrigin(getWidth()/2, getHeight()/2);
        setPosition(getWidth()*position.y + otstup*(1+position.y), getHeight()*position.x + otstup*(1+position.x));
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		float size_height = GameScreen.FONTS_SIZE[0]*0.7f;
		GameScreen.Fonts[0].setColor(Color.WHITE);
		GameScreen.Fonts[0].drawMultiLine(batch, value+"", getX()+getOriginX(), getY()+getOriginY()+size_height/2, 0, HAlignment.CENTER);

	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public boolean checkPosition(Vector2 position) {
		if(position.x == position.x && position.y == position.y)
			return true;
		else
			return false;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
		float p = (float)(Math.log(value)/Math.log(2));
		setColor(1-p/11, p/11, 0, 1);
	}
}
