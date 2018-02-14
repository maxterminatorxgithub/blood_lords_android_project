package net.mybluemix.eu_de.maxterminatorx.game_controls;

import com.badlogic.gdx.InputProcessor;

/**
 * Created by maxterminatorx on 12-Jan-18.
 */

public class JoystickController implements Joystick.JoystickListener ,InputProcessor{



    @Override
    public void onJoystickCliked(String key) {

    }

    @Override
    public void onJoystickRelease(String key, int delay) {

    }





    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
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
