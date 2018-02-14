package net.mybluemix.eu_de.maxterminatorx.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

import net.mybluemix.eu_de.maxterminatorx.BloodLordsStartPoint;
import net.mybluemix.eu_de.maxterminatorx.interfaces.Animateable;

/**
 * Created by maxterminatorx on 08-Jan-18.
 */

public class CompatAnimation implements Animateable {

    public static float DURATION = 1f/50;

    private float x,y,width,height;

    private Animation anime;

    private TextureRegion currentScene;

    private float stateTime;

    public static AssetManager manager = new AssetManager();

    public CompatAnimation(String path,final int cols,final int rows,final int sceneNum){
        this(new Texture(Gdx.files.internal(path)),cols,rows,sceneNum);
    }

    public CompatAnimation(Texture sprite,final int cols,final int rows,final int sceneNum){

        TextureRegion[][] tempData = TextureRegion.split(sprite, sprite.getWidth() / cols,
                sprite.getHeight() / rows);
        TextureRegion[] data = new TextureRegion[sceneNum];

        int i = 0;
        loadLoop:
        for (TextureRegion tx[] : tempData) {
            BloodLordsStartPoint.logger.log("length: ", String.valueOf(tx.length));
            for (TextureRegion tx2 : tx) {
                data[i] = tx2;
                i++;
                if (i >= data.length)
                    break loadLoop;
            }
        }

        anime = new Animation(DURATION, data);
        stateTime = 0;
    }


    @Override
    public void draw(SpriteBatch batch) {
            batch.draw(anime.getKeyFrame(stateTime,true),x,y,width,height);
    }

    public void draw(SpriteBatch batch,float x,float y,float width,float height) {
        batch.draw(anime.getKeyFrame(stateTime,true),x-width/2,y-height/2,width,height);
    }


    @Override
    public void nextScene() {
        stateTime += DURATION;
        currentScene = anime.getKeyFrame(stateTime);
    }

    public void loop(){
        anime.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void init(){
        stateTime = 0;
        //anime.setFrameDuration(0);
    }
}
