package items;

import java.util.Random;

/**
 * Created by sasha on 12/31/2017.
 */
public class Knife extends Weapon {

    public Knife(){// default items.Knife
        super("Knife");
        attack = 60;
        defense = 5;
        range = 1;
    }

    @Override
    protected int getAttackRange(boolean positive) {
        return positive ? 21 : 21;
    }

    @Override
    protected int getDefenseRange(boolean positive) {
        return positive ? 11: 5;
    }
}
