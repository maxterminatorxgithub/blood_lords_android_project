package net.mybluemix.eu_de.maxterminatorx.interfaces;

/**
 * Created by maxterminatorx on 31-Dec-17.
 */

public interface Playable {
    void play();
    void pause();
    void stop();
    void reset();
    void release();

    boolean isPlaying();
}
