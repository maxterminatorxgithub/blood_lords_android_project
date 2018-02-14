package net.mybluemix.eu_de.maxterminatorx.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import net.mybluemix.eu_de.maxterminatorx.animation.Animation;
import net.mybluemix.eu_de.maxterminatorx.animation.CompatAnimation;
import net.mybluemix.eu_de.maxterminatorx.interfaces.Animateable;
import net.mybluemix.eu_de.maxterminatorx.interfaces.CharacterLoader;
import net.mybluemix.eu_de.maxterminatorx.maps.Camera;
import net.mybluemix.eu_de.maxterminatorx.maps.GameMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by maxterminatorx on 29-Dec-17.
 */

public class GameCharacter implements Animateable {


    public interface LoadCharInterface{
        CharacterLoader load(String name);
    }


    public static LoadCharInterface getLoader;



    private Map<State, CompatAnimation> states;
    private State currentState;
    private String name;

    public GameCharacter(String name,boolean load){
        states = new HashMap<State, CompatAnimation>();
        this.name = name;

        if(load)
            load();

    }

    public GameCharacter(String name){
        this(name,true);
    }


    public void load(){
        load(null);
    }

    public void load(State key,CompatAnimation value){
        states.put(key,value);
    }


    public void load(Runnable runWhenFinishLoading){



        //states.put(State.FORWARD,new Animation(22,"animations/forward/"+name+'/'+name));
        //states.put(State.FORWARD_REF,new Animation(22,"animations/forward_ref/"+name+'/'+name));



        //states.put(State.FORWORD_WEAPON,new Animation(22,"animations/forward_weapon/"+name+'/'+name));
        //states.put(State.FORWARD_WEAPON_REF,new Animation(22,"animations/forward_weapon_ref/"+name+'/'+name));


        //states.put(State.MELEE_ATTACK,new Animation(10,"animations/melee_attack/"+name+'/'+name));
        //states.put(State.MELEE_ATTACK_REF,new Animation(10,"animations/melee_attack_ref/"+name+'/'+name));

        //states.put(State.STAND,new CompatAnimation("characters/"+name+"/animation/left/stand.png",10,10,100));
        //states.get(State.STAND).loop();
        //states.put(State.STAND_REF,new CompatAnimation("characters/"+name+"/animation/right/stand.png",10,10,100));
        //states.get(State.STAND_REF).loop();
//
        //states.put(State.FORWARD,new CompatAnimation("characters/"+name+"/animation/left/forward.png",5,5,22));
        //states.get(State.FORWARD).loop();
        //states.put(State.FORWARD_REF,new CompatAnimation("characters/"+name+"/animation/right/forward.png",5,5,22));
        //states.get(State.FORWARD_REF).loop();

        //states.put(State.STAND_WEAPON,new Animation(100,"animations/stand_weapon/"+name+'/'+name));
        //states.put(State.STAND_WEAPON_REF,new Animation(100,"animations/stand_weapon_ref/"+name+'/'+name));


        states.put(State.FORWARD_LEFT,new CompatAnimation("characters/"+name+"/animation/left/forward.png",5,5,22));
        states.get(State.FORWARD_LEFT).loop();
        states.put(State.FORWARD_RIGHT,new CompatAnimation("characters/"+name+"/animation/right/forward.png",5,5,22));
        states.get(State.FORWARD_RIGHT).loop();


        states.put(State.FORWARD_WEAPON_LEFT,new CompatAnimation("characters/"+name+"/animation/left/forward_weapon.png",5,5,22));
        states.get(State.FORWARD_WEAPON_LEFT).loop();
        states.put(State.FORWARD_WEAPON_RIGHT,new CompatAnimation("characters/"+name+"/animation/right/forward_weapon.png",5,5,22));
        states.get(State.FORWARD_WEAPON_RIGHT).loop();

        states.put(State.MELEE_ATTACK_LEFT,new CompatAnimation("characters/"+name+"/animation/left/melee_attack.png",10,1,10));
        states.get(State.MELEE_ATTACK_LEFT).loop();
        states.put(State.MELEE_ATTACK_RIGHT,new CompatAnimation("characters/"+name+"/animation/right/melee_attack.png",10,1,10));
        states.get(State.MELEE_ATTACK_RIGHT).loop();


        states.put(State.STAND_LEFT,new CompatAnimation("characters/"+name+"/animation/left/stand.png",10,10,100));
        states.get(State.STAND_LEFT).loop();
        states.put(State.STAND_RIGHT,new CompatAnimation("characters/"+name+"/animation/right/stand.png",10,10,100));
        states.get(State.STAND_RIGHT).loop();

        states.put(State.STAND_WEAPON_LEFT,new CompatAnimation("characters/"+name+"/animation/left/stand_weapon.png",10,10,100));
        states.get(State.STAND_WEAPON_LEFT).loop();
        states.put(State.STAND_WEAPON_RIGHT,new CompatAnimation("characters/"+name+"/animation/right/stand_weapon.png",10,10,100));
        states.get(State.STAND_WEAPON_RIGHT).loop();

        states.put(State.TURN_SIDE_LEFT,new CompatAnimation("characters/"+name+"/animation/left/turn_side.png",5,7,35));
        states.get(State.TURN_SIDE_LEFT).loop();
        states.put(State.TURN_SIDE_RIGHT,new CompatAnimation("characters/"+name+"/animation/right/turn_side.png",5,7,35));
        states.get(State.TURN_SIDE_RIGHT).loop();

        states.put(State.TURN_SIDE_WEAPON_LEFT,new CompatAnimation("characters/"+name+"/animation/left/turn_side_weapon.png",5,7,35));
        states.get(State.TURN_SIDE_WEAPON_LEFT).loop();
        states.put(State.TURN_SIDE_WEAPON_RIGHT,new CompatAnimation("characters/"+name+"/animation/right/turn_side_weapon.png",5,7,35));
        states.get(State.TURN_SIDE_WEAPON_RIGHT).loop();

        states.put(State.WEAPON_ATTACK_LEFT,new CompatAnimation("characters/"+name+"/animation/left/weapon_attack.png",6,3,17));
        states.get(State.WEAPON_ATTACK_LEFT).loop();
        states.put(State.WEAPON_ATTACK_RIGHT,new CompatAnimation("characters/"+name+"/animation/right/weapon_attack.png",6,3,17));
        states.get(State.WEAPON_ATTACK_RIGHT).loop();

        states.put(State.WEAPON_ON_LEFT,new CompatAnimation("characters/"+name+"/animation/left/weapon_on.png",5,5,25));
        states.get(State.WEAPON_ON_LEFT).loop();
        states.put(State.WEAPON_ON_RIGHT,new CompatAnimation("characters/"+name+"/animation/right/weapon_on.png",5,5,25));
        states.get(State.WEAPON_ON_RIGHT).loop();

        states.put(State.WEAPON_OFF_LEFT,new CompatAnimation("characters/"+name+"/animation/left/weapon_off.png",5,5,25));
        states.get(State.WEAPON_OFF_LEFT).loop();
        states.put(State.WEAPON_OFF_RIGHT,new CompatAnimation("characters/"+name+"/animation/right/weapon_off.png",5,5,25));
        states.get(State.WEAPON_OFF_RIGHT).loop();

        setState(State.STAND_LEFT);

        if(runWhenFinishLoading != null)
            runWhenFinishLoading.run();
    }

    @Override
    public void draw(SpriteBatch batch) {
        states.get(currentState).draw(batch,x,y, width, height);
    }

    private float x,y;


    public void setLocation(float x, float y) {
        this.x=x;this.y=y;
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    private int width,height;


    public void setSize(int w, int h) {
        width=w;height=h;
    }


    public void setState(State newState){
            currentState = newState;
            states.get(currentState).init();
    }

    @Override
    public void nextScene(){
        states.get(currentState).nextScene();
    }





    public enum State{
        FORWARD_LEFT,
        FORWARD_RIGHT,
        FORWARD_WEAPON_LEFT,
        FORWARD_WEAPON_RIGHT,
        MELEE_ATTACK_LEFT,
        MELEE_ATTACK_RIGHT,
        STAND_LEFT,
        STAND_RIGHT,
        STAND_WEAPON_LEFT,
        STAND_WEAPON_RIGHT,
        TURN_SIDE_LEFT,
        TURN_SIDE_RIGHT,
        TURN_SIDE_WEAPON_LEFT,
        TURN_SIDE_WEAPON_RIGHT,
        WEAPON_ATTACK_LEFT,
        WEAPON_ATTACK_RIGHT,
        WEAPON_ON_LEFT,
        WEAPON_ON_RIGHT,
        WEAPON_OFF_LEFT,
        WEAPON_OFF_RIGHT,
    }

}
