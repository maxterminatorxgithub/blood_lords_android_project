package net.mybluemix.eu_de.maxterminatorx.interfaces;

import net.mybluemix.eu_de.maxterminatorx.characters.GameCharacter;
import net.mybluemix.eu_de.maxterminatorx.maps.GameMap;

/**
 * Created by maxterminatorx on 08-Jan-18.
 */

public interface CharacterLoader {


    void startLoad();

    void setOnLoadListener(OnloadListener listener);


    public interface OnloadListener{

        void onLoad(GameCharacter character);

    }


}
