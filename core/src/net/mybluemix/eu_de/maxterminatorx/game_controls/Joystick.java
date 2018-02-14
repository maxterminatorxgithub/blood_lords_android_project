package net.mybluemix.eu_de.maxterminatorx.game_controls;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.mybluemix.eu_de.maxterminatorx.interfaces.Drawable;

/**
 * Created by maxterminatorx on 27-Dec-17.
 */

public class Joystick implements Drawable{

    private Key up,down,left,right,w,x,y,z;

    private float upScale,downScale,leftScale,rightScale,
                  wScale,xScale,yScale,zScale;

    private JoystickListener joystickListener;

    private int width,height;

    private final int buttonSize;

    public Joystick(){
        this(1920,540);
    }

    public Joystick(int width,int height){
        this.width = width;
        this.height = height;
        buttonSize = width/9;
        up = new Key(new Texture("joystick/up.png"),buttonSize,buttonSize*2);
        down = new Key(new Texture("joystick/down.png"),buttonSize,0);
        left = new Key(new Texture("joystick/left.png"),0,buttonSize);
        right = new Key(new Texture("joystick/right.png"),buttonSize*2,buttonSize);
        w = new Key(new Texture("joystick/w.png"),buttonSize*7-this.width/100*3,buttonSize*2);
        x = new Key(new Texture("joystick/x.png"),buttonSize*6-this.width/100*3,buttonSize);
        y = new Key(new Texture("joystick/y.png"),buttonSize*8-this.width/100*3,buttonSize);
        z = new Key(new Texture("joystick/z.png"),buttonSize*7-this.width/100*3,0);
    }



    public void draw(SpriteBatch graphics){



        up.draw(graphics);
        down.draw(graphics);
        left.draw(graphics);
        right.draw(graphics);
        w.draw(graphics);
        x.draw(graphics);
        y.draw(graphics);
        z.draw(graphics);
    }


    public void setLocation(int x, int y) {
        this.height=y;
    }


    public void setSize(int w, int h) {
        this.width=width;
        this.height=height;
    }

    public void setJoystickListener(JoystickListener joystickListener){
        this.joystickListener = joystickListener;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean scaleUp(String key){

        if(joystickListener != null)
            joystickListener.onJoystickCliked(key);

        switch (key){
            case JoystickListener.UP_KEY:
                up.scale(1.5f);
                return true;
            case JoystickListener.DOWN_KEY:
                down.scale(1.5f);
                return true;
            case JoystickListener.LEFT_KEY:
                left.scale(1.5f);
                return true;
            case JoystickListener.RIGHT_KEY:
                right.scale(1.5f);
                return true;
            case JoystickListener.W_KEY:
                w.scale(1.5f);
                return true;
            case JoystickListener.X_KEY:
                x.scale(1.5f);
                return true;
            case JoystickListener.Y_KEY:
                y.scale(1.5f);
                return true;
            case JoystickListener.Z_KEY:
                z.scale(1.5f);
                return true;
        }



        return false;
    }


    public boolean scaleUp(int x,int y){
        if(x>=up.x&&x<=up.x+buttonSize&&y>=up.y&&y<=up.y+buttonSize)
            return scaleUp(JoystickListener.UP_KEY);
        if(x>=down.x&&x<=down.x+buttonSize&&y>=down.y&&y<=down.y+buttonSize)
            return scaleUp(JoystickListener.DOWN_KEY);
        if(x>=left.x&&x<=left.x+buttonSize&&y>=left.y&&y<=left.y+buttonSize)
            return scaleUp(JoystickListener.LEFT_KEY);
        if(x>=right.x&&x<=right.x+buttonSize&&y>=right.y&&y<=right.y+buttonSize)
            return scaleUp(JoystickListener.RIGHT_KEY);
        if(x>=w.x&&x<=w.x+buttonSize&&y>=w.y&&y<=w.y+buttonSize)
            return scaleUp(JoystickListener.W_KEY);
        if(x>=this.x.x&&x<=this.x.x+buttonSize&&y>=this.x.y&&y<=this.x.y+buttonSize)
            return scaleUp(JoystickListener.X_KEY);
        if(x>=this.y.x&&x<=this.y.x+buttonSize&&y>=this.y.y&&y<=this.y.y+buttonSize)
            return scaleUp(JoystickListener.Y_KEY);
        if(x>=z.x&&x<=z.x+buttonSize&&y>=z.y&&y<=z.y+buttonSize)
            return scaleUp(JoystickListener.Z_KEY);

        return false;
    }


    public void scaleDown(String key){

        if(joystickListener != null)
            joystickListener.onJoystickRelease(key,0);

        switch (key){
            case JoystickListener.UP_KEY:
                up.scale(1);
                return;
            case JoystickListener.DOWN_KEY:
                down.scale(1);
                return;
            case JoystickListener.LEFT_KEY:
                left.scale(1);
                return;
            case JoystickListener.RIGHT_KEY:
                right.scale(1);
                return;
            case JoystickListener.W_KEY:
                w.scale(1);
                return;
            case JoystickListener.X_KEY:
                x.scale(1);
                return;
            case JoystickListener.Y_KEY:
                y.scale(1);
                return;
            case JoystickListener.Z_KEY:
                z.scale(1);
                return;
        }
    }

    private class Key{

        Texture tex;
        float scale;
        int x,y,pointer;
        Key(Texture tex,int x,int y){
            this.tex=tex;
            scale = 1f;
            this.x = x;
            this.y = y;
        }

        public int pointer(){
            return pointer;
        }

        public int pointer(int pointer){
            return this.pointer=pointer;
        }

        public void draw(SpriteBatch drawer){
            float scaledOffset = buttonSize/8*scale;
            drawer.draw(tex,x,y+scaledOffset,buttonSize*scale,buttonSize*scale);
        }

        public void scale(float scale){
            this.scale = scale;
        }

    }

    public interface JoystickListener{

        String UP_KEY   = "up",   DOWN_KEY  = "down",
               LEFT_KEY = "left", RIGHT_KEY = "right",
               W_KEY    = "w",    X_KEY     = "x",
               Y_KEY    = "y",    Z_KEY     = "Z";


        /**
         *
         * @param key the key that was pressed
         */

        void onJoystickCliked(String key);

        void onJoystickRelease(String key,int delay);

    }


}
