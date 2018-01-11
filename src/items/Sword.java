package items;

import java.util.Random;

/**
 * Created by sasha on 12/31/2017.
 */
public class Sword extends Weapon {

    public Sword(){// base level sword(the one you get at the start of the game
        super("Sword");
        attack = 50;
        defense = 50;
        range = Math.sqrt(2);// the range is root 2 because then you can attack zombies diagonally
    }

    @Override
    protected int getAttackRange(boolean positive) {
        return positive ? 31 : 21;
    }

    @Override
    protected int getDefenseRange(boolean positive) {
        return positive ? 21: 26;
    }
}
