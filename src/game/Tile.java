package game;

public class Tile {

    private int tileType;
    private items.Item itemOn;

    public int getTileType() {
        return tileType;
    }

    public void setTileType(int tileType) {
        this.tileType = tileType;
    }

    public items.Item getItemOn() {
        return itemOn;
    }

    public void setItemOn(items.Item itemOn) {
        this.itemOn = itemOn;
    }

    @Override
    public String toString() {
        return Integer.toString(tileType);
    }
}
