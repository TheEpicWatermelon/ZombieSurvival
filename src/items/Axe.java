package items;

/**
 * [Axe.java]
 * holds information for the axe weapon
 * @author Sasha
 * @date December 31st, 2017
 */
public class Axe extends Weapon {
    // constructor
    Axe(){// default axe
        super("Axe");
        attack = 40;
        defense = 20;
        range = Math.sqrt(2);
    }

    // Getter for attackRange, this is used when axe is crafted, gives a range for the item's attack stats
    @Override
    protected int getAttackRange(boolean positive) {
        return positive ? 41 : 16;
    }// end getAttackRange

    // Getter for defense range, this is used when axe is crafted, gives a range for the item's defense stat
    @Override
    protected int getDefenseRange(boolean positive) {
        return positive ? 11: 11;
    }// end getDefenseRange
}// end Axe class
