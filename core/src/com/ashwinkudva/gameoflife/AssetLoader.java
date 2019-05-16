package com.ashwinkudva.gameoflife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AssetLoader
{

    //VARIABLES
    public static Texture whiteTileTexture;
    public static Texture blackTileTexture;

    public static Sprite whiteTileSprite;
    public static Sprite blackTileSprite;

    public static final int SCREEN_SIZE = 750;
    public static final int CELL_COUNT = 15;
    public static final int CELL_SIZE = 50;


    //METHODS
    public static void loadAssets()
    {
        whiteTileTexture = new Texture(Gdx.files.internal("whitetile.png"));
        blackTileTexture = new Texture(Gdx.files.internal("blacktile.png"));

        whiteTileTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        blackTileTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        whiteTileSprite = new Sprite(whiteTileTexture);
        blackTileSprite = new Sprite(blackTileTexture);
    }

}
