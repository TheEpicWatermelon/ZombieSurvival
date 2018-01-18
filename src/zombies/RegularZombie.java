package zombies;

/**
 * [RegularZombie.java]
 * holds data for the regular zombie
 * @author Sasha Maximovitch
 * @date December 29th, 2017
 */
public class RegularZombie extends Zombie {
    // constructor that sets the regular Zombie stats
    public RegularZombie(int yCoord, int xCoord){
        super(yCoord, xCoord);
        health = 50;
        attackValue = 50;
        defenseValue = 50;
        evasiveness = 50;
        speed = 5;
    }
}// end RegularZombie stats
