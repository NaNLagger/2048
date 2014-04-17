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
	private int bestValue;
	private TextLabel highScore;
	private TextLabel newGame;
	private TextLabel title;
	private TextLabel textWin;
	private Group mainGroup;
	private ButtonActor[][] fields = new ButtonActor[4][4];
	private GameInputListner gameInputListner;
	private boolean flagWin = false;
	private float alpha=0;
	
	public static Color[] colorField = new Color[13];
	
	public static int sizeField = 578; 
	public static BitmapFont[] Fonts = new BitmapFont[3];
	public static final int[] FONTS_SIZE = {
		56,
		100
	};
	
	

	public GameScreen(SpriteBatch batch) {
		String RUSENG_CHARS = "";
		for(int i=32; i<127; i++) 
		   RUSENG_CHARS += (char)i;
		  
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ClearSans.ttf"));
		Fonts[0] = generator.generateFont(FONTS_SIZE[0], RUSENG_CHARS, false);
		Fonts[0].getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose();
		
		//ClearSansBold.ttf
		  
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ClearSansBold.ttf"));
		Fonts[1] = generator.generateFont(FONTS_SIZE[1], RUSENG_CHARS, false);
		Fonts[1].getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose();
		
		Fonts[0].setColor(Color.WHITE);
		Fonts[1].setColor(Color.BLACK);
		
		/*Color*/
		colorField[0] = new Color(255f/255f, 255f/255f, 255f/255f, 1);//0
		colorField[1] = new Color(238f/255f, 228f/255f, 218f/255f, 1);//2
		colorField[2] = new Color(237f/255f, 224f/255f, 200f/255f, 1);//4
		colorField[3] = new Color(242f/255f, 177f/255f, 121f/255f, 1);//8
		colorField[4] = new Color(245f/255f, 149f/255f, 99f/255f, 1);//16
		colorField[5] = new Color(246f/255f, 124f/255f, 95f/255f, 1);//32
		colorField[6] = new Color(246f/255f, 94f/255f, 59f/255f, 1);//64
		colorField[7] = new Color(237f/255f, 207f/255f, 114f/255f, 1);//128
		colorField[8] = new Color(237f/255f, 204f/255f, 97f/255f, 1);//256
		colorField[9] = new Color(237f/255f, 200f/255f, 80f/255f, 1);//512
		colorField[10] = new Color(237f/255f, 197f/255f, 63f/255f, 1);//1024
		colorField[11] = new Color(237f/255f, 194f/255f, 46f/255f, 1);//2048
		
		mainStage = new Stage(MyStart.GAME_WIDTH, MyStart.GAME_HEIGHT, false, batch);
		gameInputListner = new GameInputListner(this);
		
		background = new TextureActor(new Texture("data/background.png"));
		background.setSize(sizeField, sizeField);
		background.setOrigin(background.getWidth()/2, background.getHeight()/2);
		background.setPosition(MyStart.GAME_WIDTH/2 - background.getOriginX(), MyStart.GAME_HEIGHT - background.getHeight()-140);
		
		mainGroup = new Group();
		mainGroup.setSize(sizeField, sizeField);
		mainGroup.setOrigin(mainGroup.getWidth()/2, mainGroup.getHeight()/2);
		mainGroup.setPosition(MyStart.GAME_WIDTH/2 - mainGroup.getOriginX(), MyStart.GAME_HEIGHT - mainGroup.getHeight()-140);
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
		
		title = new TextLabel(new Texture("data/field.png"));
		//title.setColor(143f/255f, 122f/255f, 102f/255f, 1);
		title.setSize(0, 100);
		title.setOrigin(title.getWidth()/2, title.getHeight()/2);
		title.setPosition(130, MyStart.GAME_HEIGHT-title.getHeight());
		title.setFont(Fonts[1]);
		title.setText("2048");
		
		textWin = new TextLabel(new Texture("data/field.png"));
		textWin.setColor(236f/255f, 196/255f, 0, 1);
		textWin.setSize(MyStart.GAME_WIDTH*1.5f, sizeField);
		textWin.setOrigin(textWin.getWidth()/2, textWin.getHeight()/2);
		textWin.setPosition(MyStart.GAME_WIDTH/2-textWin.getOriginX(), MyStart.GAME_HEIGHT-textWin.getHeight()-140);
		textWin.setFont(Fonts[1]);
		textWin.setText("You Win");
		textWin.setVisible(false);
		
		mainStage.addActor(score);
		mainStage.addActor(highScore);
		mainStage.addActor(newGame);
		mainStage.addActor(title);
		mainStage.addActor(textWin);
		
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(250f/255f, 248f/255f, 239f/255f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		mainStage.act(delta);
		mainStage.draw();
		
		if(flagWin) {
			textWin.setVisible(true);
			textWin.setColor(236f/255f, 196/255f, 0, alpha);
			alpha += 0.005;
			if(alpha > 0.7) flagWin = false;
		}
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
		String RUSENG_CHARS = "";
		for(int i=32; i<127; i++) 
		   RUSENG_CHARS += (char)i;
		  
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ClearSans.ttf"));
		Fonts[0] = generator.generateFont(FONTS_SIZE[0], RUSENG_CHARS, false);
		Fonts[0].getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose();
		  
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ClearSansBold.ttf"));
		Fonts[1] = generator.generateFont(FONTS_SIZE[1], RUSENG_CHARS, false);
		Fonts[1].getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose();
		
		Fonts[0].setColor(Color.WHITE);
		Fonts[1].setColor(Color.BLACK);
		
		title.setFont(Fonts[1]);
		score.setFont(Fonts[0]);
		highScore.setFont(Fonts[0]);
		newGame.setFont(Fonts[0]);
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
						scoreValue += fields[j][i].getValue();
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
						scoreValue += fields[j][i].getValue();
					}
				}
				
			}
		}
		
		if(flag) 
			newValue();
		
		score.setText("Score\n"+scoreValue);
		flagWin = checkWin();
		if(flagWin) {
			if(bestValue<scoreValue) {
				bestValue=scoreValue;
				highScore.setText("Best\n"+bestValue);
			}
		}
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
	
	public boolean checkWin() {
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++) {
				if(fields[i][j].getValue() >= 2048) 
					return true;
			}
		return false;
	}
}
