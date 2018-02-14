package net.mybluemix.eu_de.maxterminatorx.game_controls;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;

import net.mybluemix.eu_de.maxterminatorx.BloodLordsStartPoint;
import net.mybluemix.eu_de.maxterminatorx.DisplaySettings;

/**
 * Created by maxterminatorx on 03-Jan-18.
 */

public class ScreenInputHandler implements InputProcessor {



    private GameMaster master;
    private int displayHeight;

    public ScreenInputHandler(GameMaster master){
        this.master=master;
        displayHeight = DisplaySettings.display().getHeight();
    }




    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        BloodLordsStartPoint.logger.log("screenX: ",String.valueOf(screenX));
        BloodLordsStartPoint.logger.log("screenY: ",String.valueOf(screenY));
        BloodLordsStartPoint.logger.log("pointer: ",String.valueOf(pointer));
        BloodLordsStartPoint.logger.log("button: ",String.valueOf(button));



        return master.onTouch(screenX,displayHeight - screenY);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return master.onTouchRelease(screenX,displayHeight - screenY);
    }







    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }









    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
}
