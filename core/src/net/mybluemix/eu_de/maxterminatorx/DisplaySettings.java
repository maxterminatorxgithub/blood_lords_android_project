package net.mybluemix.eu_de.maxterminatorx;

/**
 * Created by maxterminatorx on 28-Dec-17.
 */

public final class DisplaySettings {

    private static DisplaySettings display;

    public static DisplaySettings display(){
        return display;
    }

    public static boolean loaded(){
        return display!=null ? true : false;
    }

    public static void setDisplay(int width,int height){
        display = new DisplaySettings(width,height);
    }

    private int width,height;

    private DisplaySettings(int width,int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }





}
