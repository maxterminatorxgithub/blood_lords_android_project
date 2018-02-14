package net.mybluemix.eu_de.maxterminatorx.game_controls;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.mybluemix.eu_de.maxterminatorx.DisplaySettings;
import net.mybluemix.eu_de.maxterminatorx.interfaces.Animateable;
import net.mybluemix.eu_de.maxterminatorx.interfaces.Drawable;

/**
 * Created by maxterminatorx on 13-Feb-18.
 */

public class PlayerStateBar implements Drawable{

    private short points;
    private char side;
    private Texture content;

    PlayerStateBar(char side){
        this.side = side;
        points = 100;
        content = new Texture("lifecolor.png");
    }


    @Override
    public void draw(SpriteBatch batch) {
        switch(side) {
            case 'r':
                batch.draw(content, DisplaySettings.display().getWidth()*0.9f-DisplaySettings.display().getWidth()*0.003f*points,
                        DisplaySettings.display().getHeight()*0.9f,
                        DisplaySettings.display().getWidth()*0.003f*points,
                        DisplaySettings.display().getHeight()*0.02f);
                break;
            case 'l':
                batch.draw(content, DisplaySettings.display().getHeight()*0.1f,
                        DisplaySettings.display().getHeight()*0.9f,
                        DisplaySettings.display().getWidth()*0.003f*points,
                        DisplaySettings.display().getHeight()*0.02f);
                break;
        }
    }

    public void setState(short state) {
        points = state;
    }
}
