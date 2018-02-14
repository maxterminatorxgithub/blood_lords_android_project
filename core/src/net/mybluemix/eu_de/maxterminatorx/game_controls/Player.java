package net.mybluemix.eu_de.maxterminatorx.game_controls;

import net.mybluemix.eu_de.maxterminatorx.maps.Camera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.mybluemix.eu_de.maxterminatorx.animation.Animation;
import net.mybluemix.eu_de.maxterminatorx.characters.Stickman;
import net.mybluemix.eu_de.maxterminatorx.characters.StickmanBeta;
import net.mybluemix.eu_de.maxterminatorx.interfaces.RightORLeftOption;
import net.mybluemix.eu_de.maxterminatorx.characters.GameCharacter;
import net.mybluemix.eu_de.maxterminatorx.interfaces.Animateable;
import net.mybluemix.eu_de.maxterminatorx.maps.GameMap;

/**
 * Created by maxterminatorx on 02-Jan-18.
 */

public class Player implements Animateable {


    public static final byte LEFT = 1;
    public static final byte RIGHT = 2;

    public static final float SPEED_WEAPON_OFF = 0.9f;
    public static final float SPEED_WEAPON_ON = 3.5f;

    private GameCharacter character;

    private byte turn;
    private short health;
    private boolean running;
    private boolean backruning;
    private boolean weapon;

    private short attaking;

    private PlayerStateBar playerStateBar;


    public Player(@RightORLeftOption byte turn){
        this.turn=turn;
        health = 1000;
        playerStateBar = new PlayerStateBar(turn == LEFT?'l':'r');
    }



    public void setCharacter(GameCharacter character){
        this.character=character;
    }

    public void switchTurn(){
        turn = turn == LEFT ? RIGHT : LEFT;
    }

    @Override
    public void draw(SpriteBatch batch) {
        playerStateBar.draw(batch);
        character.draw(batch);
    }

    public void hit(short dmg) {
        health-=dmg;
        if(health<0)
            health=0;
        playerStateBar.setState((short)(health/1000f*100));
    }

    private Player oponent;

    public void setOponent(Player p){
        oponent = p;
    }

    private GameMap map;

    public void setGameMap(GameMap m){
        map = m;
    }

    @Override
    public void nextScene() {
        if(attaking>0&&attaking<18)
            attaking++;
        else if(attaking != 0){
            attaking = 0;
            move("stand");
        }
        if(running) {
            if(character.getX()-oponent.character.getX()<map.getScreenWidth()) {
                character.setLocation(character.getX() + SPEED_WEAPON_OFF * map.getScreenWidth() / 100, character.getY());
                oponent.character.setLocation(oponent.character.getX() - SPEED_WEAPON_OFF * map.getScreenWidth() / 100, oponent.character.getY());
                map.step(-SPEED_WEAPON_OFF);
            }
        }
        else if(backruning) {
            if(character.getX()-oponent.character.getX()>-map.getScreenWidth()) {
                character.setLocation(character.getX() - SPEED_WEAPON_OFF * map.getScreenWidth() / 100, character.getY());
                oponent.character.setLocation(oponent.character.getX() + SPEED_WEAPON_OFF * map.getScreenWidth() / 100, oponent.character.getY());
                map.step(SPEED_WEAPON_OFF);
            }
        }
        character.nextScene();
    }

    public void load(String gameChar) {
        switch (gameChar){
            case "stickman":
                character = new Stickman();
                return;
            case "stickman_beta":
                character = new StickmanBeta();
                return;
        }
    }

    public void move(String move) {

        if(attaking != 0)
            return;

        if(turn == LEFT)
            switch (move){
                case "stand":
                    running = false;
                    backruning = false;
                    character.setState(GameCharacter.State.STAND_LEFT);
                    return;
                case "forward":
                    running = true;
                    backruning = false;
                    character.setState(GameCharacter.State.FORWARD_LEFT);
                    return;
                case "backward":
                    running = false;
                    backruning = true;
                    character.setState(GameCharacter.State.FORWARD_LEFT);
                    return;
                case "turn side":
                    running = false;
                    backruning = false;
                    character.setState(GameCharacter.State.TURN_SIDE_RIGHT);
                    return;
                case "attack":
                    running = false;
                    backruning = false;
                    attaking = 1;
                    character.setState(GameCharacter.State.MELEE_ATTACK_LEFT);
                    return;
            }
        else if(turn == RIGHT)
            switch (move){
                case "stand":
                    running = false;
                    backruning = false;
                    character.setState(GameCharacter.State.STAND_RIGHT);
                    return;
                case "forward":
                    running = true;
                    backruning = false;
                    character.setState(GameCharacter.State.FORWARD_RIGHT);
                    return;
                case "backward":
                    running = false;
                    backruning = true;
                    character.setState(GameCharacter.State.FORWARD_RIGHT);
                    return;
                case "turn side":
                    running = false;
                    backruning = false;
                    character.setState(GameCharacter.State.TURN_SIDE_LEFT);
                    return;
                case "attack":
                    running = false;
                    attaking = 1;
                    backruning = false;
                    character.setState(GameCharacter.State.MELEE_ATTACK_RIGHT);
                    return;
            }

    }

    public void setSize(int w,int h){
        character.setSize(w,h);
    }

    public void setLocation(int x,int y){
        character.setLocation(x,y);
    }

    public float getX(){
        return character.getX();
    }

    public float getY(){
        return character.getY();
    }

    public byte getTurn() {
        return turn;
    }

    public void setTurn(@RightORLeftOption byte turn){
        this.turn=turn;
    }

    public short getHealth() {
        return health;
    }
}
