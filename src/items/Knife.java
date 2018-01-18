package items;

/**
 * [Knife.java]
 * holds information for the Knife Weapon
 * @author Sasha Maximovitch
 * @date December 31st, 2017
 */
public class Knife extends Weapon {

    // constructor
    public Knife(){// default Knife
        super("Knife");
        attack = 60;
        defense = 5;
        range = Math.sqrt(2);
    }

    // Getter for attackRange, this is used when knife is crafted, gives a range for the item's attack stats
    @Override
    protected int getAttackRange(boolean positive) {
        return positive ? 21 : 21;
    }// end getAttackRange

    // Getter for defense range, this is used when Knife is crafted, gives a range for the item's defense stat
    @Override
    protected int getDefenseRange(boolean positive) {
        return positive ? 11: 5;
    }// end getDefenseRange
}// end Knife class
