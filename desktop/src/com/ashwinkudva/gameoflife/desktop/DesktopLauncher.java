package com.ashwinkudva.gameoflife.desktop;

import com.ashwinkudva.gameoflife.AssetLoader;
import com.ashwinkudva.gameoflife.GameOfLife;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher
{
	//MAIN DESKTOP RUNNER
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = AssetLoader.SCREEN_SIZE;
		config.height = AssetLoader.SCREEN_SIZE;
		new LwjglApplication(new GameOfLife(), config);
	}
}
