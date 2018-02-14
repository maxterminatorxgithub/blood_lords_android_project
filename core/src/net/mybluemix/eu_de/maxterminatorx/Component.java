package net.mybluemix.eu_de.maxterminatorx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.mybluemix.eu_de.maxterminatorx.animation.Animation;
import net.mybluemix.eu_de.maxterminatorx.interfaces.Animateable;

/**
 * Created by maxterminatorx on 03-Jan-18.
 */

public class Component implements Animateable{



    private Texture texture;
    private int x,y,width,height ;

    public Component(Texture tex){
        x=y=0;
        width = tex.getWidth();
        height = tex.getHeight();
        texture = tex;
    }


    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture,x,y,width,height);
    }


    public void setLocation(int x, int y) {
        this.x=x;
        this.y=y;
    }


    public void setSize(int w, int h) {
        width=w;height=h;
    }


    @Override
    public void nextScene() {

    }

    public ComponentLustener listener;

    public void setComponentListener(ComponentLustener listener){
        this.listener=listener;
    }

    public void click(int x,int y){
        if(listener!=null)
            if(x>=this.x&&y>=this.y&&x<=this.x+width&&y<=this.y+height)
                listener.clickUp(x-this.x,y-this.y);
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }


    public interface ComponentLustener{
        public void clickUp(int x,int y);
    }

}
