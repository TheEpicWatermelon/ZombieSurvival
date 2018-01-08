package items;

import java.util.Random;

/**
 * Created by sasha on 12/31/2017.
 */
public class Pistol extends Weapon {

    public Pistol(){// default pistol
        super("Pistol");
        attack = 40;
        defense = 5;
        range = 5;
    }

    @Override
    protected int getAttackRange(boolean positive) {
        return positive ? 16 : 16;
    }

    @Override
    protected int getDefenseRange(boolean positive) {
        return positive ? 6: 5;
    }
}
