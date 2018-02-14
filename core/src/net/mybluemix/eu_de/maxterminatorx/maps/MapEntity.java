package net.mybluemix.eu_de.maxterminatorx.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.mybluemix.eu_de.maxterminatorx.interfaces.Drawable;

/**
 * Created by maxterminatorx on 02-Jan-18.
 */

public class MapEntity implements Drawable {

    private Texture texture;

    private float x,y,width,height, deep;


    MapEntity(String texturePath,int x,int y,int width,int height,float deep){
        texture = new Texture(texturePath);
        this.deep=deep;
        setSize(width,height);
        setLocation(x,y);
    }

    public void setDeep(float deep){
        this.deep=deep;
        setSize(width,height);
        setLocation(x,y);
    }

    public float getDeep(){
        return deep;
    }


    public void draw(SpriteBatch batch,Camera camera) {
        batch.draw(texture,x-camera.getX(),y-camera.getY(),width,height);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture,x,y,width,height);
    }


    public void setLocation(float x, float y) {
        this.x = x / deep;
        this.y = y / deep;
    }

    public void setSize(float w, float h) {
        this.width = w/deep;this.height = h/deep;
    }

    public void step(float step) {
        this.x += step / deep;
    }
}
