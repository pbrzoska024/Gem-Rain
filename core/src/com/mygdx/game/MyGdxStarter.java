package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;

public class MyGdxStarter extends Game {


    public void create () {

        this.setScreen(new MyGdxGame(this));
    }

    public void render () {
        super.render();
    }


    public void dispose () {
    }
}
