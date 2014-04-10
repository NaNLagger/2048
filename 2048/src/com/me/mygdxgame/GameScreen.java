package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	private Stage mainStage;
	private TextureActor background;
	private Group mainGroup;
	private ButtonActor[][] fields = new ButtonActor[4][4];
	private GameInputListner gameInputListner;
	
	public static int sizeField = 578; 
	

	public GameScreen(SpriteBatch batch) {
		mainStage = new Stage(MyStart.GAME_WIDTH, MyStart.GAME_HEIGHT, false, batch);
		gameInputListner = new GameInputListner(this);
		
		background = new TextureActor(new Texture("data/background.png"));
		background.setSize(sizeField, sizeField);
		background.setOrigin(background.getWidth()/2, background.getHeight()/2);
		background.setPosition(MyStart.GAME_WIDTH/2 - background.getOriginX(), MyStart.GAME_HEIGHT/2 - background.getOriginY());
		
		mainGroup = new Group();
		mainGroup.setSize(sizeField, sizeField);
		mainGroup.setOrigin(mainGroup.getWidth()/2, mainGroup.getHeight()/2);
		mainGroup.setPosition(MyStart.GAME_WIDTH/2 - mainGroup.getOriginX(), MyStart.GAME_HEIGHT/2 - mainGroup.getOriginY());
		mainGroup.addListener(gameInputListner);
		
		generateField();
		
		mainStage.addActor(background);
		mainStage.addActor(mainGroup);
		newValue();
		newValue();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		mainStage.act(delta);
		mainStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(mainStage);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	public void moveField(Vector2 value) {
		boolean flag = false;
		/*Вправо*/
		if(value.x == 1) {
			for(int i = 0; i < 4; i++) {
				
				for(int j = 3; j > 0 ; j--) {
					for(int k = 3; k > 0; k--) {
						if(fields[i][k].getValue() == 0 && fields[i][k-1].getValue() != 0) {
							fields[i][k].setValue(fields[i][k-1].getValue());
							fields[i][k-1].setValue(0);
							flag = true;
							Gdx.app.log("1", i + " | " + k);
						}
					}
				}
				
				for(int j = 3; j > 0; j--)	{
					if(fields[i][j].getValue() == fields[i][j-1].getValue() && fields[i][j].getValue() != 0 && fields[i][j-1].getValue() != 0) {
						fields[i][j].setValue(fields[i][j].getValue()+fields[i][j-1].getValue());
						for(int k=j-1;k > 0;k--)
							fields[i][k].setValue(fields[i][k-1].getValue());
						fields[i][0].setValue(0);
						flag = true;
						Gdx.app.log("2", i + " | " + j);
					}
				}
				
			}
		}
		
		/*Влево*/
		if(value.x == -1) {
			for(int i = 0; i < 4; i++) {
				
				for(int j = 3; j > 0 ; j--) {
					for(int k = 0; k < 3; k++) {
						if(fields[i][k].getValue() == 0 && fields[i][k+1].getValue() != 0) {
							fields[i][k].setValue(fields[i][k+1].getValue());
							fields[i][k+1].setValue(0);
							flag = true;
							Gdx.app.log("1", i + " | " + k);
						}
					}
				}
				
				for(int j = 0; j < 3; j++)	{
					if(fields[i][j].getValue() == fields[i][j+1].getValue() && fields[i][j].getValue() != 0 && fields[i][j+1].getValue() != 0) {
						fields[i][j].setValue(fields[i][j].getValue()+fields[i][j+1].getValue());
						for(int k=j+1;k < 3;k++)
							fields[i][k].setValue(fields[i][k+1].getValue());
						fields[i][3].setValue(0);
						flag = true;
						Gdx.app.log("2", i + " | " + j);
					}
				}
				
			}
		}
		
		/*Вверх*/
		if(value.y == 1) {
			for(int i = 0; i < 4; i++) {
				
				for(int j = 3; j > 0 ; j--) {
					for(int k = 3; k > 0; k--) {
						if(fields[k][i].getValue() == 0 && fields[k-1][i].getValue() != 0) {
							fields[k][i].setValue(fields[k-1][i].getValue());
							fields[k-1][i].setValue(0);
							flag = true;
							Gdx.app.log("1", i + " | " + k);
						}
					}
				}
				
				for(int j = 3; j > 0; j--)	{
					if(fields[j][i].getValue() == fields[j-1][i].getValue() && fields[j][i].getValue() != 0 && fields[j-1][i].getValue() != 0) {
						fields[j][i].setValue(fields[j][i].getValue()+fields[j-1][i].getValue());
						for(int k=j-1;k > 0;k--)
							fields[k][i].setValue(fields[k-1][i].getValue());
						fields[0][i].setValue(0);
						flag = true;
						Gdx.app.log("2", i + " | " + j);
					}
				}
				
			}
		}
		
		/*Вниз*/
		if(value.y == -1) {
			for(int i = 0; i < 4; i++) {
				
				for(int j = 3; j > 0 ; j--) {
					for(int k = 0; k < 3; k++) {
						if(fields[k][i].getValue() == 0 && fields[k+1][i].getValue() != 0) {
							fields[k][i].setValue(fields[k+1][i].getValue());
							fields[k+1][i].setValue(0);
							flag = true;
							Gdx.app.log("1", i + " | " + k);
						}
					}
				}
				
				for(int j = 0; j < 3; j++)	{
					if(fields[j][i].getValue() == fields[j+1][i].getValue() && fields[j][i].getValue() != 0 && fields[j+1][i].getValue() != 0) {
						fields[j][i].setValue(fields[j][i].getValue()+fields[j+1][i].getValue());
						for(int k=j+1;k < 3;k++)
							fields[k][i].setValue(fields[k+1][i].getValue());
						fields[3][i].setValue(0);
						flag = true;
						Gdx.app.log("2", i + " | " + j);
					}
				}
				
			}
		}
		
		Gdx.app.log("Enter", "--------------------------------------------------------------------------");
		if(flag) 
			newValue();
	}
	
	public void generateField() {
		
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++) {
				fields[i][j] = new ButtonActor(new Texture("data/field.png"), new Vector2(i,j));
				mainGroup.addActor(fields[i][j]);
			}
		
	}
	
	public void newValue() {
		Array<ButtonActor> array = new Array<ButtonActor>();
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++) {
				if(fields[i][j].getValue() == 0) {
					array.add(fields[i][j]);
					fields[i][j].setVisible(false);
				}
				else {
					fields[i][j].setVisible(true);
				}
			}
		int temp = MathUtils.random(array.size-1);
		array.get(temp).setValue(2);
		array.get(temp).setVisible(true);
	
	}
}
