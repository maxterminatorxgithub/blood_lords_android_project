package net.mybluemix.eu_de.maxterminatorx.maps;

import com.badlogic.gdx.graphics.Texture;

import net.mybluemix.eu_de.maxterminatorx.DisplaySettings;

/**
 * Created by maxterminatorx on 02-Jan-18.
 */

public class OvermindNest extends GameMap {

    private int screenWidth,screenHeight;

    public OvermindNest(int w,int h){
        super(mapMusics.get("overmind_nest_soundtrack"),2,1);
        screenWidth = h;
        screenHeight = w;

        setSkybox(new Texture("maps/overmind_nest/skybox.png"));


        addMapEntity(new MapEntity("maps/overmind_nest/entities/platform.png",
                -screenWidth,screenHeight/100*-5,
                screenWidth*4,screenHeight/2,1f));




    }

}
