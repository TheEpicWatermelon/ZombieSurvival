package zombies;

/**
 * Created by sasha on 12/29/2017.
 */
public class ToughZombie extends Zombie{

    public ToughZombie(int yCoord, int xCoord){
        super(yCoord, xCoord);
        health = 80;
        attackValue = 70;
        defenseValue = 70;
        evasiveness = 30;
        speed = 4;
    }
}
