package game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sasha on 1/6/2018.
 */
public class ZombieMove {
    List<Coord> move = new ArrayList<>();
    User attackedPlayer = null;

    public String toString(){
        StringBuilder string = new StringBuilder();
        // loop through all the coords in move and add them to the string
        for (Coord coords: move){
            string.append(coords.x + ";" + coords.y + ";");// add the coordinates
        }
        if (attackedPlayer != null){// if zombie attacked player
            string.append("|");// this symbol means there is an attack
            string.append(attackedPlayer.getListNum());// give which user got attacked
        }

        return string.toString();
    }

}
