package net.mybluemix.eu_de.maxterminatorx.android;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Display;

import net.mybluemix.eu_de.maxterminatorx.DisplaySettings;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import net.mybluemix.eu_de.maxterminatorx.BloodLordsStartPoint;
import net.mybluemix.eu_de.maxterminatorx.interfaces.CharacterLoader;
import net.mybluemix.eu_de.maxterminatorx.interfaces.Playable;

import java.util.HashMap;
import java.util.Map;

public class AndroidLauncher extends AndroidApplication{


	private static AndroidLauncher current;

	private String mode;

	@RequiresApi(api = Build.VERSION_CODES.N)
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		current = this;
		super.onCreate(savedInstanceState);
		CharacterLoadTask.setContext(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		Display d = getWindowManager().getDefaultDisplay();

		DisplaySettings.setDisplay(d.getWidth(),d.getHeight());

		Log.i("display size:",DisplaySettings.display().getWidth()
		+"x"+DisplaySettings.display().getHeight());

		MediaPlayer player = null;


		Map<String,Playable> musics = new HashMap<>();
		musics.put("main",new MusicHandler(MediaPlayer.create(this,R.raw.entry_soundtrack1)));
		musics.put("character_table",new MusicHandler(MediaPlayer.create(this,R.raw.choose_character_music)));
		musics.put("overmind_nest_soundtrack",new MusicHandler(MediaPlayer.create(this,R.raw.overmind_soundtrack)));


		if(UserLocation.activetedService!=null)
			UserLocation.activetedService.stopService(new Intent(this,UserLocation.class));

		String mode = this.getIntent().getExtras().getString("mode");
		BloodLordsStartPoint starter = new BloodLordsStartPoint(Log::i,AndroidLauncher::returnTo,mode);
		starter.setMusics(musics);

		initialize(starter, config);
	}


	public static class MusicHandler implements Playable{

		private MediaPlayer mp;


		public MusicHandler(MediaPlayer mp){
			this.mp=mp;
			mp.setLooping(true);
		}


		@Override
		public void play() {
			try {
				mp.start();
			}catch (Exception ex){

			}
		}


		@Override
		public void pause() {
			mp.pause();
		}

		@Override
		public void stop() {
			mp.stop();
		}

		@Override
		public void reset() {
			mp.reset();
		}

		@Override
		public void release() {
			mp.release();
		}

		@Override
		public boolean isPlaying() {
			return mp.isPlaying();
		}
	}



	@NonNull
	public static CharacterLoader loadCharacter(String name){
		return new CharacterLoadTask(name);
	}

	//new to stop activity whhile not displying to user
	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}


	static void returnTo(String className,String state) {
		try {
			Intent intent = new Intent(current, Class.forName(className));

			intent.putExtra("state",state);

			current.startActivity(intent);
			current.finish();


		}catch (ClassNotFoundException cnfex){
			System.exit(1);
		}


	}
}
