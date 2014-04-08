package com.me.mygdxgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ButtonActor extends TextureActor {
	private int value;
	private Vector2 position;	
	private BitmapFont font;

	public ButtonActor(Texture toDraw, Vector2 position) {
		super(toDraw);
		this.value = 0;
		this.position = position;
		setName(value + "");
		font = new BitmapFont();
        font.setColor(Color.RED);
        setSize(108, 108);
        setOrigin(getWidth()/2, getHeight()/2);
        setPosition(getWidth()*position.y + 16*(1+position.y), getHeight()*position.x + 16*(1+position.x));
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		font.draw(batch, value+"", getX()+getOriginX(), getY()+getOriginY());
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
	}
}
