package items;

import java.util.Random;

/**
 * Created by sasha on 12/31/2017.
 */
public class Rifle extends Weapon {

    public Rifle(){// default rifle
        super("Rifle");
        attack = 70;
        defense = 10;
        range = 10;
    }

    @Override
    protected int getAttackRange(boolean positive) {
        return positive ? 31 : 21;
    }

    @Override
    protected int getDefenseRange(boolean positive) {
        return positive ? 11: 10;
    }
}
