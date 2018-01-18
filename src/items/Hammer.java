package items;

/**
 * [Hammer.java]
 * holds the information for a Hammer Weapon
 * @author Sasha Maximovitch
 * @date December 31st, 2017
 */
public class Hammer extends Weapon {
    // constructor
    public Hammer() {// default Hammer
        super("Hammer");
        attack = 10;
        defense = 10;
        range = Math.sqrt(2);
    }

    // Getter for attackRange, this is used when hammer is crafted, gives a range for the item's attack stats
    @Override
    protected int getAttackRange(boolean positive) {
        return positive ? 31 : 10;
    }// end getAttackRange

    // Getter for defense range, this is used when hammer is crafted, gives a range for the item's defense stat
    @Override
    protected int getDefenseRange(boolean positive) {
        return positive ? 16: 10;
    }// end getDefenseRange
}// end Hammer class
