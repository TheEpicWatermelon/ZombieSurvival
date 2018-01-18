package items;

/**
 * [Pistol.java]
 * holds data for the pistol weapon
 * @author Sasha Maximovitch
 * @date December 31st, 2017
 */
public class Pistol extends Weapon {
    // constructor
    public Pistol(){// default pistol
        super("Pistol");
        attack = 40;
        defense = 5;
        range = 5;
    }

    // Getter for attackRange, this is used when pistol is crafted, gives a range for the item's attack stats
    @Override
    protected int getAttackRange(boolean positive) {
        return positive ? 16 : 16;
    }// end getAttackRange

    // Getter for defense range, this is used when pistol is crafted, gives a range for the item's defense stat
    @Override
    protected int getDefenseRange(boolean positive) {
        return positive ? 6: 5;
    }// end getDefenseRange
}// end Pistol class
