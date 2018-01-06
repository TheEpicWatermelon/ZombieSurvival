package items;

import java.util.Random;

/**
 * Created by sasha on 12/31/2017.
 */
public class Axe extends Weapon {

    Axe(){// default axe\
        super("Axe");
        attack = 40;
        defense = 20;
        range = 1;
    }

    @Override
    protected int getAttackRange(boolean positive) {
        return positive ? 41 : 16;
    }

    @Override
    protected int getDefenseRange(boolean positive) {
        return positive ? 11: 11;
    }
}
