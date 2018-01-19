package game;

/**
 * [Tile.java]
 * holds data for a tile on the map, the tileType can be changed
 * @author Sasha Maximovitch
 * @date December 21st, 2017
 */

public class Tile {

    private int tileType;// the type of tile 0 = grass, 1 = tree, 2 = cobble, 3 = fence, 4 = wall

    /**
     * getTileType
     * returns this tiles type
     * @return a number for tile type
     */
    public int getTileType() {
        return tileType;
    }// end getTileType

    /**
     * setTileType
     * takes an integer and sets the tile to that type
     * @param tileType
     */
    public void setTileType(int tileType) {
        this.tileType = tileType;
    }// end setTileType

    /**
     * toString
     * makes it so when you call tile in a print it will print out it's tile type, if its grass print a space
     * @return a string with the tile type
     */
    @Override
    public String toString() {
        if (tileType == 0){
            return " ";
        }else {
            return Integer.toString(tileType);
        }
    }// end toString
}// end Tile class
