package net.mybluemix.eu_de.maxterminatorx.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;

import net.mybluemix.eu_de.maxterminatorx.animation.CompatAnimation;
import net.mybluemix.eu_de.maxterminatorx.characters.GameCharacter;
import net.mybluemix.eu_de.maxterminatorx.characters.Stickman;
import net.mybluemix.eu_de.maxterminatorx.characters.StickmanBeta;
import net.mybluemix.eu_de.maxterminatorx.interfaces.CharacterLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by maxterminatorx on 08-Jan-18.
 */

public class CharacterLoadTask extends AsyncTask<String,Void,GameCharacter>implements CharacterLoader {

    private static Context context;

    public static void setContext(Context c){
        context=c;
    }

    public static void nullContext(){
        context=null;
    }

    private String name;

    public CharacterLoadTask(String name){
        Log.i("Loading character: ",name);
        this.name = name;
    }

    @Override
    protected GameCharacter doInBackground(String... strings) {

        GameCharacter character = null;

        switch(strings[0]) {

            case "stickman":
                character = new Stickman();
                break;
            case "stickman_beta":
                character = new StickmanBeta();
                break;

        }

        character.load(GameCharacter.State.STAND_LEFT,
                new CompatAnimation(getTexture("characters/"+strings[0]+"/animation/left/stand.png"),
                        10,10,100));

        character.load(GameCharacter.State.STAND_LEFT,
                new CompatAnimation(getTexture("characters/"+strings[0]+"/animation/right/stand.png"),
                        10,10,100));

        return character;
    }

    @Override
    protected void onPostExecute(GameCharacter character){
        listener.onLoad(character);
    }


    @Override
    public void startLoad() {
        this.execute(name);
    }


    private OnloadListener listener;

    @Override
    public void setOnLoadListener(OnloadListener listener) {
        this.listener = listener;
    }


    public static Texture getTexture(String path){

        Texture texture=new Texture(Gdx.files.internal(path));
        Log.i("width->"+texture.getWidth(),"width->"+texture.getHeight());

        return texture;
    }

}
