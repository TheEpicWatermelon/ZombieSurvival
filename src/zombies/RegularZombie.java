package zombies;

/**
 * Created by sasha on 12/29/2017.
 */
public class RegularZombie extends Zombie {

    public RegularZombie(int yCoord, int xCoord){// add randomness later
        super(yCoord, xCoord);
        health = 50;
        attackValue = 50;
        defenseValue = 50;
        evasiveness = 50;
        speed = 5;
    }

}
