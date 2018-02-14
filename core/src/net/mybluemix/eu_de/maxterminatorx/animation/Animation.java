package net.mybluemix.eu_de.maxterminatorx.animation;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import net.mybluemix.eu_de.maxterminatorx.interfaces.Animateable;

/**
 * Created by maxterminatorx on 29-Dec-17.
 */

public class Animation implements Animateable {


    private boolean loaded;
    private Texture scenes[];
    private int currentScene;
    private int x,y,width,height;
    private String path;

    public Animation(int scenesNumber,String path){
        this(scenesNumber,path,true);
    }

    public Animation(Animation anim){
        this.scenes = new Texture[anim.scenes.length];
        for(int i=0;i<this.scenes.length;i++){


            //refTexture(anim.scenes[i]);

        }


    }

    private Animation(){

    }





    public static Texture refTexture(Texture src){

        Texture refTexture = new Texture(src.getWidth(),
                src.getHeight(), Pixmap.Format.RGBA8888);

        src.getTextureData().prepare();
        Pixmap srcPix  = src.getTextureData().consumePixmap();
        Pixmap destPix = refTexture.getTextureData().consumePixmap();

        for(int i=0;i<refTexture.getWidth();i++){
            for(int j=0;j<refTexture.getHeight();j++){

                destPix.drawPixel(i,j,srcPix.getPixel(src.getWidth()-i-1,j));
            }
        }


        return refTexture;
    }


    public Animation(int scenesNumber,String path,boolean load){
        scenes = new Texture[scenesNumber];
        currentScene=x=y=0;
        width=height=200;
        this.path =path;
        if(load)
            load();
    }

    public void setSize(int w,int h){
        width=w;
        height=h;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }


    public void load(){

        for(int i=1;i<=scenes.length;i++){
            String prefsZero;
            if(i>=10000)
                prefsZero = "_";
            else if(i>=1000)
                prefsZero = "_0";
            else if(i>=100)
                prefsZero = "_00";
            else if(i>=10)
                prefsZero = "_000";
            else
                prefsZero = "_0000";

            scenes[i-1] = new Texture(path+prefsZero+i+".png");
        }
    }

    public void setLocation(int x,int y){
        this.x=x;this.y=y;
    }

    @Override
    public synchronized void nextScene(){

        currentScene++;

        if(currentScene>=scenes.length-1) {
            currentScene = 0;
            return;
        }

    }


    @Override
    public void draw(SpriteBatch batch) {

        //scenes[currentScene].getTextureData().consumePixmap().;
        batch.draw(scenes[currentScene],x-width/2,y-height/2,width,height);
    }


    public Array<Texture> toArray(){
        Array<Texture> arr = new Array<>();
        for(Texture txt :scenes)
            arr.add(txt);

        return arr;
    }

}
