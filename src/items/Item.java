package items;

/**
 * Created by sasha on 12/30/2017.
 */
public abstract class Item {
    Integer xCoord;
    Integer yCoord;
    final String name;

    protected Item(String name) {
        this.name = name;
    }
}
