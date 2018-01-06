package items;

import java.util.Random;

/**
 * Created by sasha on 1/2/2018.
 */
public abstract class Weapon extends Item {
    int range;
    int attack;
    int defense;

    protected Weapon(String name) {
        super(name);
    }

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
    }

    protected abstract int getAttackRange(boolean positive);

    protected abstract int getDefenseRange(boolean positive);
}


