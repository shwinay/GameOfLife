package com.ashwinkudva.gameoflife;

import com.badlogic.gdx.Game;

public class GameOfLife extends Game
{

	//***********RULES TO CONWAY'S GAME OF LIFE***********
//	Any live cell with fewer than two live neighbors dies, as if by under population.
//	Any live cell with two or three live neighbors lives on to the next generation.
//	Any live cell with more than three live neighbors dies, as if by overpopulation.
//	Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.


	//VARIABLES
	private GameScreen gameScreen;

	//CONSTRUCTOR
	@Override
	public void create()
	{
		AssetLoader.loadAssets();
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}
}
