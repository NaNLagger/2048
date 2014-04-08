package com.me.mygdxgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextureActor extends Actor {
	
	private Texture toDraw;
	private TextureRegion toDrawRegion;
	
	public TextureActor(Texture toDraw) {
		this.toDraw = toDraw;
		this.toDrawRegion = null;
		setPosition(0, 0);
		setSize(toDraw.getWidth(), toDraw.getHeight());
		setOrigin(getWidth()/2, getHeight()/2);
	}
	
	public TextureActor(TextureRegion toDrawRegion) {
		this.toDrawRegion = toDrawRegion;
		this.toDraw = null;
		setPosition(0, 0);
		setSize(toDrawRegion.getRegionWidth(), toDrawRegion.getRegionHeight());
		setOrigin(getWidth()/2, getHeight()/2);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.setColor(getColor());
		if(toDraw != null) {
			batch.draw(toDraw, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0, toDraw.getWidth(), toDraw.getHeight(), false, false);		} 
		else {
			if(toDrawRegion != null)
				batch.draw(toDrawRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		}
	}
	
	public TextureRegion getTexture() {
		return toDrawRegion;
	}
}
