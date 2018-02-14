package net.mybluemix.eu_de.maxterminatorx.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.mybluemix.eu_de.maxterminatorx.DisplaySettings;
import net.mybluemix.eu_de.maxterminatorx.interfaces.Animateable;
import net.mybluemix.eu_de.maxterminatorx.interfaces.Playable;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by maxterminatorx on 02-Jan-18.
 */

public abstract class GameMap{


    private Texture skybox;

    private ArrayList<MapEntity> entities;

    protected static Map<String,Playable>mapMusics;

    public static void setMapMusics(Map<String,Playable> musix){
        mapMusics = musix;
    }




    private Playable playMusic;

    private int screenWidth,screenHeight;
    private int mapWidth,mapHeight;
    private float cameraX,cameraY;


    protected GameMap(Playable p,float scaleWidth,float scaleHeight){
        entities = new ArrayList<MapEntity>();
        playMusic = p;

        screenWidth = DisplaySettings.display().getWidth();
        screenHeight = DisplaySettings.display().getHeight();

        mapWidth = (int)scaleWidth*screenWidth;
        mapHeight = (int)scaleHeight*screenHeight;

        cameraX = mapWidth/2;
        cameraY = mapHeight/2;

        camera=new Camera(cameraX,cameraY);
    }


    protected void addMapEntity(MapEntity entity){
        entities.add(entity);
    }

    public int getMapWidth(){
        return this.mapWidth;
    }

    public int getMapHeight(){
        return this.mapHeight;
    }

    public int getScreenWidth(){
        return this.screenWidth;
    }

    public int getScreenHeight(){
        return this.screenHeight;
    }

    public void setSkybox(Texture skybox){
        this.skybox=skybox;
    }

    public void startMap(){
        playMusic.play();
    }

    public void pauseMap(){
        playMusic.pause();
    }

    public void setDeep(float deep){
        for(MapEntity entity:entities)
            entity.setDeep(deep);
    }

    public void drawMap(SpriteBatch batch){
        batch.draw(skybox,0,0,screenWidth,screenHeight);
        for(MapEntity entity:entities)
            entity.draw(batch);
    }


    private Camera camera;

    public Camera getCamera() {
        return camera;
    }

    public void step(float step){
        for(MapEntity entity:entities){
            entity.step(step*screenWidth/100);
        }
    }
}
