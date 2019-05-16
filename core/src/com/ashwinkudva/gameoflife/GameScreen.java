package com.ashwinkudva.gameoflife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class GameScreen implements Screen
{

    //VARIABLES
    private GameOfLife game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Cell[][] field;
    private ArrayList<Cell> cellsToChange;
    private ArrayList<Boolean> cellValuesToChange;

    //CONSTRUCTOR
    public GameScreen(GameOfLife game)
    {
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(true, AssetLoader.SCREEN_SIZE, AssetLoader.SCREEN_SIZE);
        field = new Cell[AssetLoader.CELL_COUNT][AssetLoader.CELL_COUNT];
        cellsToChange = new ArrayList<Cell>();
        cellValuesToChange = new ArrayList<Boolean>();
        InputProcessor inputProcessor = new InputProcessor()
        {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button)
            {
                //mouse input
                if (button == Input.Buttons.LEFT)
                {
                    for (int i = 0; i < AssetLoader.CELL_COUNT; i ++)
                    {
                        for (int j = 0; j < AssetLoader.CELL_COUNT; j ++)
                        {
                            int x = field[i][j].getX();
                            int y = field[i][j].getY();
                            if (screenX >= x && screenX <= (x + AssetLoader.CELL_SIZE))
                            {
                                if (screenY > y && screenY < (y + AssetLoader.CELL_SIZE))
                                {
                                    field[i][j].setLiving(true);
                                }
                            }
                        }
                    }
                }
                if (button == Input.Buttons.RIGHT)
                {
                    for (int i = 0; i < AssetLoader.CELL_COUNT; i ++)
                    {
                        for (int j = 0; j < AssetLoader.CELL_COUNT; j ++)
                        {
                            int x = field[i][j].getX();
                            int y = field[i][j].getY();
                            if (screenX >= x && screenX <= (x + AssetLoader.CELL_SIZE))
                            {
                                if (screenY > y && screenY < (y + AssetLoader.CELL_SIZE))
                                {
                                    field[i][j].setLiving(false);
                                }
                            }
                        }
                    }
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        };
        Gdx.input.setInputProcessor(inputProcessor);
        init();
    }


    //METHODS
    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        ////////////////

        //render field
        for (int i = 0; i < AssetLoader.CELL_COUNT; i ++)
        {
            for (int j = 0; j < AssetLoader.CELL_COUNT; j++)
            {
                field[i][j].render(delta);
                field[i][j].update();
            }
        }

        ////////////////
        batch.end();

        //keyboard input
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
        {
            updateGeneration();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E))
        {
            init();
            cellValuesToChange = new ArrayList<Boolean>();
            cellsToChange = new ArrayList<Cell>();
        }

    }


    public void init()
    {
        //initialize field with all dead cells
        for (int i = 0; i < AssetLoader.CELL_COUNT; i ++)
        {
            for (int j = 0; j < AssetLoader.CELL_COUNT; j ++)
            {
                field[i][j] = new Cell(j * AssetLoader.CELL_SIZE, i * AssetLoader.CELL_SIZE, false, batch);
            }
        }

    }

    //updates the field simultaneously each time a generation passes
    public void updateGeneration()
    {
        for (int i = 0; i < AssetLoader.CELL_COUNT; i ++)
        {
            for (int j = 0; j < AssetLoader.CELL_COUNT; j ++)
            {
                int liveNeighbors = returnLiveNeighbors(i, j);

                //add cells to change to an arraylist to be changed simultaneously
                if (liveNeighbors < 2  || liveNeighbors > 3)
                {
                    cellsToChange.add(field[i][j]);
                    cellValuesToChange.add(false);
                }
                if (liveNeighbors == 3)
                {
                    cellsToChange.add(field[i][j]);
                    cellValuesToChange.add(true);
                }
            }
        }

        //simultaneously change cells
        for (int i = 0; i < cellsToChange.size(); i ++)
        {
            cellsToChange.get(i).setLiving(cellValuesToChange.get(i));
        }

        //flush arraylists
        cellsToChange = new ArrayList<Cell>();
        cellValuesToChange = new ArrayList<Boolean>();
    }

    //returns the number of neighbor cells alive for a cell at a given row and column
    public int returnLiveNeighbors(int row, int column)
    {
        int sum = 0;
        Cell top, bottom, left, right, topRight, topLeft, bottomRight, bottomLeft;
        top = bottom = left = right = topRight = topLeft = bottomLeft = bottomRight = new Cell(0, 0, false, batch);
        if (row > 0 && row < (AssetLoader.CELL_COUNT - 1) && column > 0 && column < (AssetLoader.CELL_COUNT - 1))
        {
            top = field[row - 1][column];
            bottom = field[row + 1][column];
            left = field[row][column - 1];
            right = field[row][column + 1];
            topRight = field[row - 1][column + 1];
            topLeft = field[row - 1][column - 1];
            bottomRight = field[row + 1][column + 1];
            bottomLeft = field[row + 1][column - 1];
        }

        if (row == 0)
        {
            if (column == 0)
            {
                right = field[row][column + 1];
                bottom = field[row + 1][column];
                bottomRight = field[row + 1][column + 1];
            }
            else if (column == (AssetLoader.CELL_COUNT - 1))
            {
                bottom = field[row + 1][column];
                left = field[row][column - 1];
                bottomLeft = field[row + 1][column - 1];
            }
            else
            {
                bottom = field[row + 1][column];
                left = field[row][column - 1];
                right = field[row][column + 1];
                bottomRight = field[row + 1][column + 1];
                bottomLeft = field[row + 1][column - 1];
            }
        }

        else if (row == (AssetLoader.CELL_COUNT - 1))
        {
            if (column == 0)
            {
                right = field[row][column + 1];
                topRight = field[row - 1][column + 1];
                top = field[row - 1][column];
            }
            else if (column == (AssetLoader.CELL_COUNT - 1))
            {
                top = field[row - 1][column];
                left = field[row][column - 1];
                topLeft = field[row - 1][column - 1];
            }
            else
            {
                top = field[row - 1][column];
                left = field[row][column - 1];
                right = field[row][column + 1];
                topRight = field[row - 1][column + 1];
                topLeft = field[row - 1][column - 1];
            }
        }

        else if (column == 0)
        {
            bottomRight = field[row + 1][column + 1];
            right = field[row][column + 1];
            topRight = field[row - 1][column + 1];
            top = field[row - 1][column];
            bottom = field[row + 1][column];
        }

        else if (column == (AssetLoader.CELL_COUNT - 1))
        {
            top = field[row - 1][column];
            bottom = field[row + 1][column];
            left = field[row][column - 1];
            topLeft = field[row - 1][column - 1];
            bottomLeft = field[row + 1][column - 1];
        }

        sum += top.getLivingValue() + bottom.getLivingValue() + left.getLivingValue() + right.getLivingValue() + topRight.getLivingValue()
                + topLeft.getLivingValue() + bottomLeft.getLivingValue() + bottomRight.getLivingValue();
        return sum;
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void show()
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }

}
