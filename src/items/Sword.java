package items;

/**
 * [Sword.java]
 * holds the data for the Sword Weapon
 * @author Sasha Maximovitch
 * @date December 31st, 2017
 */
public class Sword extends Weapon {

    // constructor
    public Sword(){// default sword
        super("Sword");
        attack = 50;
        defense = 50;
        range = Math.sqrt(2);// the range is root 2 because then you can attack zombies diagonally
    }
    // Getter for attackRange, this is used when sword is crafted, gives a range for the item's attack stats
    @Override
    protected int getAttackRange(boolean positive) {
        return positive ? 31 : 21;
    }// end getAttackRange

    // Getter for defense range, this is used when Sword is crafted, gives a range for the item's defense stat
    @Override
    protected int getDefenseRange(boolean positive) {
        return positive ? 21: 26;
    }// end GetDefenseRange
}// end Sword class
