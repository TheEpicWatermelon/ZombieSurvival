package items;

import java.util.Random;

/**
 * Created by sasha on 12/31/2017.
 */
public class Sword extends Weapon {

    Sword(){// base level sword(the one you get at the start of the game
        super("Sword");
        attack = 50;
        defense = 50;
        range = 1;
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
