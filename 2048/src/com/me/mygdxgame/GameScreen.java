package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	private Stage mainStage;
	private TextureActor background;
	private TextLabel score;
	private int scoreValue;
	private TextLabel highScore;
	private TextLabel newGame;
	private TextLabel title;
	private Group mainGroup;
	private ButtonActor[][] fields = new ButtonActor[4][4];
	private GameInputListner gameInputListner;
	
	public static int sizeField = 578; 
	public static BitmapFont[] Fonts = new BitmapFont[3];
	public static final int[] FONTS_SIZE = {
		56
	};
	

	public GameScreen(SpriteBatch batch) {
		String RUSENG_CHARS = "";
		for(int i=32; i<127; i++) 
		   RUSENG_CHARS += (char)i;
		  
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ClearSans.ttf"));
		Fonts[0] = generator.generateFont(FONTS_SIZE[0], RUSENG_CHARS, false);
		Fonts[0].getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose();
		  
		Fonts[0].setColor(Color.WHITE);
		
		mainStage = new Stage(MyStart.GAME_WIDTH, MyStart.GAME_HEIGHT, false, batch);
		gameInputListner = new GameInputListner(this);
		
		background = new TextureActor(new Texture("data/background.png"));
		background.setSize(sizeField, sizeField);
		background.setOrigin(background.getWidth()/2, background.getHeight()/2);
		background.setPosition(MyStart.GAME_WIDTH/2 - background.getOriginX(), MyStart.GAME_HEIGHT - background.getHeight()-120);
		
		mainGroup = new Group();
		mainGroup.setSize(sizeField, sizeField);
		mainGroup.setOrigin(mainGroup.getWidth()/2, mainGroup.getHeight()/2);
		mainGroup.setPosition(MyStart.GAME_WIDTH/2 - mainGroup.getOriginX(), MyStart.GAME_HEIGHT - mainGroup.getHeight()-120);
		mainGroup.addListener(gameInputListner);
		
		generateField();
		
		mainStage.addActor(background);
		mainStage.addActor(mainGroup);
		newValue();
		newValue();
		
		score = new TextLabel(new Texture("data/field.png"));
		score.setColor(187f/255f, 173f/255f, 160f/255f, 1);
		score.setSize(250, 200);
		score.setOrigin(score.getWidth()/2, score.getHeight()/2+30);
		score.setPosition(0 + 20, 40);
		score.setText("Score\n0");
		
		highScore = new TextLabel(new Texture("data/field.png"));
		highScore.setColor(187f/255f, 173f/255f, 160f/255f, 1);
		highScore.setSize(250, 200);
		highScore.setOrigin(highScore.getWidth()/2, highScore.getHeight()/2+30);
		highScore.setPosition(MyStart.GAME_WIDTH/2 + 20, 40);
		highScore.setText("Best\n0");
		
		newGame = new TextLabel(new Texture("data/field.png"));
		newGame.setColor(143f/255f, 122f/255f, 102f/255f, 1);
		newGame.setSize(300, 100);
		newGame.setOrigin(newGame.getWidth()/2, newGame.getHeight()/2);
		newGame.setPosition(MyStart.GAME_WIDTH-newGame.getWidth()-10, MyStart.GAME_HEIGHT-newGame.getHeight()-10);
		newGame.setText("New Game");
		newGame.addListener(new ButtonInputListener());
		
		mainStage.addActor(score);
		mainStage.addActor(highScore);
		mainStage.addActor(newGame);
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
						scoreValue += fields[i][j].getValue();
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
						scoreValue += fields[i][j].getValue();
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
						scoreValue += fields[i][j].getValue();
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
						scoreValue += fields[i][j].getValue();
					}
				}
				
			}
		}
		
		Gdx.app.log("Enter", "--------------------------------------------------------------------------");
		if(flag) 
			newValue();
		
		score.setText("Score\n"+scoreValue);
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
