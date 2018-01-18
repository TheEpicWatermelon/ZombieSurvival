package zombies;

/**
 * [QuickZombie.java]
 * holds information for the quick zombie
 * @author Sasha Maximovitch
 * @date December 29th, 2017
 */
public class QuickZombie extends Zombie {
    // constructor that sets the quickzombie stats
    public QuickZombie(int yCoord, int xCoord){
        super(yCoord, xCoord);
        attackValue = 35;
        defenseValue = 35;
        evasiveness = 70;
        speed = 7;
    }
}// end QuickZombie class
