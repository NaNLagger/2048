package com.me.mygdxgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextLabel extends TextureActor {
	private String value = new String();
	private BitmapFont font;

	public TextLabel(Texture toDraw) {
		super(toDraw);
		value = "";
		font = GameScreen.Fonts[0];
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		float size_height = GameScreen.FONTS_SIZE[0]*0.7f;
		font.drawMultiLine(batch, value, getX()+getOriginX(), getY()+getOriginY()+size_height/2, 0 , HAlignment.CENTER);
	}
	
	public void setText(String value) {
		this.value = value;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}
}
