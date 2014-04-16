package com.me.mygdxgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "2048";
		cfg.useGL20 = false;
		cfg.width = 400;
		cfg.height = 700;
		
		new LwjglApplication(new MyStart(), cfg);
	}
}
