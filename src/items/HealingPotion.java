package items;
// imports
import java.util.Random;

/**
 * [HealingPotion.java]
 * holds information for the Healing potion item
 * @author Sasha Maximovitch
 * @date December 31st, 2017
 */
public class HealingPotion extends Item {
    int healthGain;// health that is gained by using the potion

    // constructor
    HealingPotion(){// assign a health value
        super("Health Potion");
        Random random = new Random();
        healthGain = 10*(random.nextInt(10)+1);// add health gain(will be in a range of 10 - 100 increasing by 10 each time
    }
}// end HealingPotion class
