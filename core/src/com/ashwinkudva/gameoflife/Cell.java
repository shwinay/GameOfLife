package com.ashwinkudva.gameoflife;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cell
{

    //VARIABLES
    private Sprite sprite;
    private SpriteBatch batch;
    private int x;
    private int y;
    private boolean alive;

    //CONSTRUCTOR
    public Cell(int x, int y, boolean alive, SpriteBatch batch)
    {
        this.x = x;
        this.y = y;
        this.alive = alive;
        this.batch = batch;
        sprite = AssetLoader.whiteTileSprite;
    }

    //METHODS

    public void render(float delta)
    {
        sprite.draw(batch);
        sprite.setPosition(x, y);
        update();
    }

    public void update()
    {
        if (alive)
        {
            sprite = AssetLoader.blackTileSprite;
        }
        else
        {
            sprite = AssetLoader.whiteTileSprite;
        }
    }

    //getters and setters
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public boolean getLiving()
    {
        return alive;
    }

    public void setLiving(boolean alive)
    {
        this.alive = alive;
    }

    public int getLivingValue()
    {
        if (alive)
        {
            return 1;
        }
        return 0;
    }
}
