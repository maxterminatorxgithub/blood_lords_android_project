package net.mybluemix.eu_de.maxterminatorx.game_controls;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.mybluemix.eu_de.maxterminatorx.BloodLordsStartPoint;
import net.mybluemix.eu_de.maxterminatorx.CharacterTable;
import net.mybluemix.eu_de.maxterminatorx.Component;
import net.mybluemix.eu_de.maxterminatorx.DisplaySettings;
import net.mybluemix.eu_de.maxterminatorx.animation.Animation;
import net.mybluemix.eu_de.maxterminatorx.animation.CompatAnimation;
import net.mybluemix.eu_de.maxterminatorx.characters.GameCharacter;
import net.mybluemix.eu_de.maxterminatorx.characters.Stickman;
import net.mybluemix.eu_de.maxterminatorx.characters.StickmanBeta;
import net.mybluemix.eu_de.maxterminatorx.interfaces.Animateable;
import net.mybluemix.eu_de.maxterminatorx.interfaces.CharacterLoader;
import net.mybluemix.eu_de.maxterminatorx.interfaces.Playable;
import net.mybluemix.eu_de.maxterminatorx.maps.GameMap;
import net.mybluemix.eu_de.maxterminatorx.maps.OvermindNest;

import java.util.Map;

/**
 * Created by maxterminatorx on 02-Jan-18.
 */

public class GameMaster implements Animateable,
                                    CharacterTable.OnCharacterSelectedListener,
                                    Joystick.JoystickListener{


    private static final String MENU = "menu";
    private static final String SELECT = "select";
    private static final String LOADING = "loading";
    private static final String GAME = "game";
    private static final String FINISH = "finish";

    private String stage;
    private Map<String,Playable> musix;
    private String currentMusic;
    private int screenWidth,screenHeight;

    private Player leftPlayer;
    private Player rightPlayer;
    private GameMap map;
    private Joystick joystick;

    private Texture gameTitle;
    private Component btnVSCPU;


    private CharacterTable characterTable;
    private Animation selected;

    private CompatAnimation loading;


    private Texture charLeft,charRight;


    public GameMaster(){
        screenWidth = DisplaySettings.display().getWidth();
        screenHeight = DisplaySettings.display().getHeight();

        stage = MENU;
        initMenu();
        initSelect();
        initGame();

    }

    private void initMenu(){
        gameTitle = new Texture("game_title.png");

        btnVSCPU = new Component(new Texture("buttons/vs_cpu_btn.png"));
        btnVSCPU.setComponentListener((x,y)->{
            musix.get("main").stop();

            musix.get("main").reset();
            musix.get("main").release();
            stage = SELECT;
            musix.get("character_table").play();

        });
        btnVSCPU.setLocation(screenWidth/100*33,screenHeight/100*10);
        btnVSCPU.setSize(screenWidth/100*33,screenHeight/100*12);
    }

    private void initSelect(){
        characterTable = new CharacterTable(new Texture("character_table.png"));
        characterTable.setOnCharacterSelectedListener(this);
        characterTable.setSize(screenWidth/2,screenHeight/100*80);
        characterTable.setLocation(screenWidth/4,screenHeight/100*20);

        //selected = new Animation(24,"hower_character/");



    }

    private void initGame(){
        loading = new CompatAnimation("sprites/loading.png",13,4,50);
        loading.loop();
    }


    public void setLeftPlayer(Player p){
        leftPlayer = p;
    }

    public void setRightPlayer(Player p){
        rightPlayer = p;
    }

    public void setJoystick(Joystick joystick){
        this.joystick = joystick;
        this.joystick.setJoystickListener(this);
    }

    public boolean checkReady(){
        return leftPlayer!=null&&rightPlayer!=null;
    }


    public void reset(){
        leftPlayer = null;
        rightPlayer = null;
    }

    public void setStage(String stage){
        this.stage = stage;
    }

    @Override
    public void draw(SpriteBatch batch) {

        switch(stage) {

            case MENU:
                batch.draw(gameTitle,screenWidth/8,screenHeight/3,
                        screenWidth/4*3,screenHeight/4*2);
                btnVSCPU.draw(batch);

                return;
            case SELECT:
                characterTable.draw(batch);
                if(charLeft!=null)
                    batch.draw(charLeft,0,0,screenWidth/4,screenHeight/3*2);
                if(charRight!=null)
                    batch.draw(charRight,screenWidth/4*3,0,screenWidth/4,screenHeight/3*2);
                return;

            case LOADING:
                loading.draw(batch,screenWidth/2,screenHeight/2,screenWidth/2,screenHeight);
                return;

            case GAME:
                //loading.draw(batch,screenWidth/2,screenHeight/2,screenWidth/2,screenHeight);

                if(map!=null)
                    map.drawMap(batch);

                rightPlayer.draw(batch);
                leftPlayer.draw(batch);
                joystick.draw(batch);
                return;

            case FINISH:
                font.draw(batch,this.winTitle,screenWidth/4,screenHeight/2);
        }
    }

    public boolean onTouch(int screenX,int screenY){
        switch (stage) {

            case MENU:
                btnVSCPU.click(screenX,screenY);
                return true;

            case SELECT:
                characterTable.click(screenX,screenY);

                return true;

            case GAME:


                return joystick.scaleUp(screenX,
                        screenY);

            default:
                return true;
        }

    }

    public boolean onTouchRelease(int screenX,int screenY){
        if(stage.equals(GAME)) {
            joystick.scaleDown(Joystick.JoystickListener.RIGHT_KEY);
            joystick.scaleDown(Joystick.JoystickListener.LEFT_KEY);
            joystick.scaleDown(Joystick.JoystickListener.UP_KEY);
            joystick.scaleDown(Joystick.JoystickListener.DOWN_KEY);
            joystick.scaleDown(Joystick.JoystickListener.W_KEY);
            joystick.scaleDown(Joystick.JoystickListener.X_KEY);
            joystick.scaleDown(Joystick.JoystickListener.Y_KEY);
            joystick.scaleDown(Joystick.JoystickListener.Z_KEY);
        }
        return true;
    }


    public void updateGame(){

        float newCamX = Math.abs(leftPlayer.getX()+rightPlayer.getX())/2;

        map.getCamera().setX(newCamX);

        if(rightPlayer.getHealth() == 0){
            stage = FINISH;
            for(Playable music : musix.values()) {
                try {
                    if (music.isPlaying()) {
                        music.stop();
                        music.release();
                    }
                }catch (IllegalStateException lex){

                }
            }
        }

    }

    private short waitForReturn;

    @Override
    public void nextScene() {

        switch (stage){

            case GAME:
                leftPlayer.nextScene();
                rightPlayer.nextScene();
                updateGame();

                return;

            case SELECT:
                characterTable.nextScene();
                return;
            case LOADING:
                loading.nextScene();
                return;

            case FINISH:
                if(waitForReturn > 250){
                    BloodLordsStartPoint.returnWithState(true);
                }
                waitForReturn++;

        }


    }


    public void setMusix(Map<String,Playable> musix) {

        this.musix = musix;
        startMusic("main");
    }


    public void startMusic(String name){
        if(currentMusic!=null){
            this.musix.get(currentMusic).stop();
            this.musix.get(currentMusic).release();
        }
        currentMusic = name;
        this.musix.get(name).play();
        GameMap.setMapMusics(musix);
    }

    @Override
    public void onCharacterSelected(String characterSelected) {
        BloodLordsStartPoint.logger.log("selected Character: ",characterSelected);
        switch (characterSelected){
            case CharacterTable.OnCharacterSelectedListener.STICKMAN:
                if(charLeft == null)
                    charLeft = new Texture("characters/stickman/left.png");
                else if(charRight == null)
                    charRight = new Texture("characters/stickman/right.png");
                else {
                    stage = LOADING;
                    map = new OvermindNest(screenWidth,screenHeight);
                    this.musix.get("character_table").stop();
                    this.musix.get("character_table").release();


                    leftPlayer = new Player(Player.LEFT);

                    rightPlayer = new Player(Player.RIGHT);

                    /*
                    CharacterLoader leftLoader = GameCharacter.getLoader.load("stickman_beta");
                    CharacterLoader rightLoader = GameCharacter.getLoader.load("stickman_beta");
                    leftLoader.setOnLoadListener(character ->{
                        leftPlayer.setCharacter(character);
                        leftPlayer.setSize(screenWidth/4,screenHeight/2);
                        leftPlayer.setLocation(screenWidth/2,screenHeight/2);
                        leftPlayer.move("stand");

                    });
                    leftLoader.startLoad();*/

                    /*
                    leftPlayer.setCharacter(new Stickman());
                    leftPlayer.setSize(screenWidth/4,screenHeight/2);
                    leftPlayer.setLocation(screenWidth/2,screenHeight/2);
                    leftPlayer.move("stand");*/


                    /*rightLoader.setOnLoadListener(character ->{
                        rightPlayer.setCharacter(character);
                        rightPlayer.setSize(screenWidth/4,screenHeight/2);
                        rightPlayer.setLocation(screenWidth/2,screenHeight/2);
                        rightPlayer.move("stand");

                        map.startMap();
                        stage = GAME;
                    });
                    rightLoader.startLoad();*/

                    /*
                    rightPlayer.setCharacter(new StickmanBeta());
                    rightPlayer.setSize(screenWidth/4,screenHeight/2);
                    rightPlayer.setLocation(screenWidth/2,screenHeight/2);
                    rightPlayer.move("stand");*/


                    leftPlayer.setCharacter(new Stickman());
                    leftPlayer.setSize(screenWidth / 4, screenHeight / 2);
                    leftPlayer.setLocation(screenWidth / 4, screenHeight /3);
                    leftPlayer.move("stand");
                    rightPlayer.setCharacter(new StickmanBeta());
                    rightPlayer.setSize(screenWidth / 4, screenHeight / 2);
                    rightPlayer.setLocation(screenWidth / 4*3, screenHeight / 3);
                    rightPlayer.move("stand");

                    leftPlayer.setOponent(rightPlayer);
                    rightPlayer.setOponent(leftPlayer);

                    leftPlayer.setGameMap(map);
                    rightPlayer.setGameMap(map);
                    map.startMap();
                    stage = GAME;





                }
                return;
        }
    }

    @Override
    public void onJoystickCliked(String key) {
        switch (key){
            case Joystick.JoystickListener.RIGHT_KEY:

                leftPlayer.move("forward");

                if(leftPlayer.getTurn() == Player.RIGHT) {
                    if (leftPlayer.getX() - rightPlayer.getX() < 0) {
                        leftPlayer.setTurn(Player.LEFT);
                        leftPlayer.move("turn side");
                    }
                    break;
                }
                else {
                    leftPlayer.move("forward");
                }
                return;
            case Joystick.JoystickListener.LEFT_KEY:

                leftPlayer.move("backward");

                if(leftPlayer.getTurn() == Player.LEFT) {
                    if (leftPlayer.getX() - rightPlayer.getX() > 0) {
                        leftPlayer.setTurn(Player.RIGHT);
                        leftPlayer.move("turn side");
                    }
                    break;
                }
                else {
                    leftPlayer.move("backward");
                }
                return;
            case Joystick.JoystickListener.X_KEY:
                if(Math.abs(leftPlayer.getX()-rightPlayer.getX())<=DisplaySettings.display().getWidth()*0.2){
                    rightPlayer.hit((short)85);

                }
                leftPlayer.move("attack");
                return;
        }
    }

    @Override
    public void onJoystickRelease(String key, int delay) {
        leftPlayer.move("stand");
    }

    private Playable stoppedMusic;

    public void pause() {
        for(Playable music:musix.values()){
            try {
                if (music.isPlaying()) {
                    stoppedMusic = music;
                    stoppedMusic.pause();
                    return;
                }
            }catch(IllegalStateException lex){

            }
        }
    }

    public void resume() {
        if(stoppedMusic == null)
            return;
        stoppedMusic.play();
    }

    private BitmapFont font;
    private final String winTitle = "YOU WIN!";

    public void setFont(BitmapFont font) {
        this.font = font;

    }
}
