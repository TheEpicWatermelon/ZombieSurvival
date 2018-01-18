package zombies;

/**
 * [ToughZombie.java]
 * holds data for the tough zombie
 * @author Sasha Maximovitch
 * @date December 29th, 2017
 */
public class ToughZombie extends Zombie{
    // constructor that sets the tough zombie stats
    public ToughZombie(int yCoord, int xCoord){
        super(yCoord, xCoord);
        health = 80;
        attackValue = 70;
        defenseValue = 70;
        evasiveness = 30;
        speed = 4;
    }
}// end ToughZombie class
