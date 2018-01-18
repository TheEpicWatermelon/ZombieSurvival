package items;

// imports
import java.util.Random;

/**
 * [Weapon.java]
 * and abstract method that creates variables that weapons will have, and a method for crafting, that is called when the weapon is crafted and sets random stats based
 * on the subclasses attack and defense range
 * @author Sasha Maximovitch
 * @date January 2nd, 2018
 */
public abstract class Weapon extends Item {
    public double range;// range of the weapon
    public int attack;// attack stat
    public int defense;// defense stat

    // constructor
    protected Weapon(String name) {
        super(name);
    }

    /**
     * crafting
     * this method is called when the weapon is crafted, assigns random attack and defense values based on the weapon's attack & defense ranges
     * @param crafting - the crafting stat of the player
     */
    protected void crafting(int crafting){
        Random random = new Random();
        int randNum = random.nextInt(101);
        if ( (crafting - randNum) >= 0){// this is for a positive buff in crafting
            attack = this.attack + random.nextInt(getAttackRange(true));// attack can be up to 30 greater than default value
            defense = this.defense + random.nextInt(getDefenseRange(true));// defense can be up to 15 greater than default value
        }else {// gives negative debuff
            attack = this.attack - random.nextInt(getAttackRange(false)); // attack can be up to 9 less than the default value
            defense = this.defense - random.nextInt(getDefenseRange(false));// defense can be up to 9 less than the default value
        }
    }// end crafting

    // getAttackRange - all subclasses will modify this method so that it will give the attack range, the boolean is true when there is a buff, and false when there is a debuff
    protected abstract int getAttackRange(boolean positive);

    // getDefenseRange - all subclasses will modify this method so that it will give the defense range, the boolean is true when there is a buff, and false when there is a debuff
    protected abstract int getDefenseRange(boolean positive);
}// end Weapon class


