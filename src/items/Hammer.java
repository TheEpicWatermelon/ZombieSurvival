package items;

import java.util.Random;

/**
 * Created by sasha on 12/31/2017.
 */
public class Hammer extends Weapon {

    Hammer() {// default items.Hammer
        super("Hammer");
        attack = 10;
        defense = 10;
        range = 1;
    }

    @Override
    protected int getAttackRange(boolean positive) {
        return positive ? 31 : 10;
    }

    @Override
    protected int getDefenseRange(boolean positive) {
        return positive ? 16: 10;
    }

}
