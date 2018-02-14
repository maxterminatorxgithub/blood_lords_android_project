package net.mybluemix.eu_de.maxterminatorx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.mybluemix.eu_de.maxterminatorx.characters.GameCharacter;
import net.mybluemix.eu_de.maxterminatorx.game_controls.GameMaster;
import net.mybluemix.eu_de.maxterminatorx.game_controls.Joystick;
import net.mybluemix.eu_de.maxterminatorx.game_controls.ScreenInputHandler;
import net.mybluemix.eu_de.maxterminatorx.interfaces.Animateable;
import net.mybluemix.eu_de.maxterminatorx.interfaces.GameLogger;
import net.mybluemix.eu_de.maxterminatorx.interfaces.Playable;
import net.mybluemix.eu_de.maxterminatorx.interfaces.ActivateActivity;

import java.util.Map;


public class BloodLordsStartPoint extends ApplicationAdapter {



	private SpriteBatch batch;
	private Texture img;
	private Map<String,Playable> musix;
	private String playMusic;
	public static GameLogger logger;
	private Joystick joystick;
	private Texture startBtn;
	private GameCharacter stickman;


	private GameMaster master;
	private static ActivateActivity chargeTo;

	private static String mode;
	private static String userId;

	private static boolean stopGame;


	private BitmapFont font;

	public BloodLordsStartPoint(GameLogger<String, String> logger, ActivateActivity chargeTo ,String mode){
		this.logger=logger;
		this.chargeTo = chargeTo;
		this.mode = mode;
		this.userId = userId;
		stopGame = false;

	}

	@Override
	public void create () {
		//musix.get("main").play();

		batch = new SpriteBatch();

		font = new BitmapFont();
		font.setColor(0,1,0,1);
		font.getData().scale(14);

		img = new Texture("game_title.png");
		logger.log("State: ","created");


		startBtn = new Texture("buttons/vs_cpu_btn.png");


		joystick = new Joystick(DisplaySettings.display().getWidth(),
				DisplaySettings.display().getHeight());


		master = new GameMaster();
		master.setJoystick(joystick);
		master.setMusix(musix);
		master.setFont(font);


		InputProcessor input = new ScreenInputHandler(master);

		Gdx.input.setInputProcessor(input);

		new GameThread(new Animateable[]{master}).start();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		master.draw(batch);
		batch.end();
	}

	@Override
	public void pause () {
		master.pause();
	}

	@Override
	public void resume () {
		master.resume();
	}

	public void setMusics(Map<String,Playable> musix) {
		this.musix = musix;
	}

	public static void returnWithState(boolean isWin) {

		if(mode.equals("user"))
				chargeTo.activate("net.mybluemix.eu_de.maxterminatorx.android.TableScore",isWin?"win":"lose");
		else if(mode.equals("demo"))
				chargeTo.activate("net.mybluemix.eu_de.maxterminatorx.android.LoginActivity",isWin?"win":"lose");

		stopGame = true;
	}


	public static class GameThread extends Thread{

		private Animateable[] animes;

		public GameThread(Animateable[] animes){
			this.animes = animes;
		}


		@Override
		public void run(){

			while(!stopGame) {
				for (Animateable d : animes) {
					d.nextScene();
					try {
						Thread.sleep(20);
					} catch (InterruptedException iex) {

					}
				}
			}
		}

	}

}
