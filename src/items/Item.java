package items;

/**
 * [Item.java]
 * an abstract class that holds data and a method that will be used in subclasses
 * @author Sasha Maximovitch
 * @data December 30th, 2017
 */
public abstract class Item {
    Integer xCoord;// x coordinate of the item(used when item is dropped)
    Integer yCoord;// y coordinate of the item(used when item is dropped
    final String name;// the name of the item

    // constructor that sets the item's name
    protected Item(String name) {
        this.name = name;
    }
}// end Item class
