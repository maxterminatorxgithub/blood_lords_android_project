package net.mybluemix.eu_de.maxterminatorx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.mybluemix.eu_de.maxterminatorx.animation.Animation;

/**
 * Created by maxterminatorx on 07-Jan-18.
 */

public class CharacterTable extends Component implements Component.ComponentLustener{

    private Animation select;
    private String selectedCharacter;

    public CharacterTable(Texture tex) {
        super(tex);
        select = new Animation(24,"hower_character/");
        this.setComponentListener(this);
    }

    @Override
    public void setSize(int w,int h){
        super.setSize(w,h);
        select.setSize(w/4,h/4);
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        select.setLocation(x+getWidth()/8,y+getHeight()/8*7);
    }

    @Override
    public void click(int x,int y){
        if(x>=getX()&&x<=getX()+getWidth()&&y>=getY()&&y<=getY()+getHeight()){
            clickUp(x,y);
        }

    }

    @Override
    public void nextScene(){
        select.nextScene();
    }

    @Override
    public void draw(SpriteBatch graph){
        super.draw(graph);
        select.draw(graph);
    }

    @Override
    public void clickUp(int x, int y) {

        int disX = x-getX();
        int disY = y-getY();

        int finalX = x-disX%(getWidth()/4)+select.getWidth()/2;
        int finalY = y-disY%(getHeight()/4)+select.getHeight()/2;


        BloodLordsStartPoint.logger.log("x: "+String.valueOf(finalX),"y: "+String.valueOf(finalY));

        select.setLocation(finalX,finalY);




        if(listener!=null)

            if(finalX >= getX() && finalX <= getX()+select.getWidth()
                    && finalY >= getY()+select.getHeight()*3 && finalY <= getY()+select.getHeight()*4){
                listener.onCharacterSelected(OnCharacterSelectedListener.STICKMAN);
            }
            else if(finalX >= getX()+select.getWidth() && finalX <= getX()+select.getWidth()*2
                && finalY >= getY()+select.getHeight()*3 && finalY <= getY()+select.getHeight()*4){
                listener.onCharacterSelected(OnCharacterSelectedListener.ILLIAS);
            }


    }

    private OnCharacterSelectedListener listener;

    public void setOnCharacterSelectedListener(OnCharacterSelectedListener listener){
        this.listener = listener;
    }

    public interface OnCharacterSelectedListener{

        String STICKMAN = "stickman";
        String ILLIAS = "illias";

        void onCharacterSelected(String characterSelected);
    }


}
