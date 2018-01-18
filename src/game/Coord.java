package game;

/**
 * [Coord.java]
 * Holds variables for the x and y coordinates
 * @author Sasha Maximovitch
 * @date January 4th, 2018
 */
public class Coord {
    public final int x;
    public final int y;

    // constructor - sets the x , y coordinates
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * toString
     * returns the coordinates as a neat string
     * @return a string with coordinates
     */

    @Override
    public String toString() {
        return x + " - " + y;
    }// end toString
}
