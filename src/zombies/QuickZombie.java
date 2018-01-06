package zombies;

/**
 * Created by sasha on 12/29/2017.
 */
public class QuickZombie extends Zombie {

    public QuickZombie(int yCoord, int xCoord){// add randomness later
        super(yCoord, xCoord);
        attackValue = 35;
        defenseValue = 35;
        evasiveness = 70;
        speed = 7;
    }
}
