package items;

/**
 * [Rifle.java]
 * holds data for the Rifle weapon
 * @author Sasha Maximovitch
 * @date December 31st, 2017
 */
public class Rifle extends Weapon {
    // constructor
    public Rifle(){// default rifle
        super("Rifle");
        attack = 70;
        defense = 10;
        range = 10;
    }
    // Getter for attackRange, this is used when rifle is crafted, gives a range for the item's attack stats
    @Override
    protected int getAttackRange(boolean positive) {
        return positive ? 31 : 21;
    }// end getAttackRange

    // Getter for defense range, this is used when rifle is crafted, gives a range for the item's defense stat
    @Override
    protected int getDefenseRange(boolean positive) {
        return positive ? 11: 10;
    }// end getDefenseRange
}// end Rifle class
