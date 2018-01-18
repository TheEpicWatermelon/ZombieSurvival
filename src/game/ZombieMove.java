package game;

import java.util.ArrayList;
import java.util.List;

/**
 * [ZombieMove.java]
 * holds information on a zombies whole turn, stores movement and an attack if it occurs
 * @author Sasha Maximovitch
 * @data January 6th, 2017
 */
public class ZombieMove {
    List<Coord> move = new ArrayList<>();// a list of coordinates that hold all the moves of the zombie
    User attackedPlayer = null;// a player that is attacked during that zombie's turn, if it is null then a player was not attacked

    /**
     * toString
     * puts the move as one string, with the coordinates of movement first and then attacked player at the end(if player is attacked). This is used for output to all players
     * @return a string that holds all the zombie moves in its current turn
     */
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
    }// end toString
}// end ZombieMove class
