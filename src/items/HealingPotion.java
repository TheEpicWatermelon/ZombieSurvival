package items;

import java.util.Random;

/**
 * Created by sasha on 12/31/2017.
 */
public class HealingPotion extends Item {
    int healthGain;

    HealingPotion(){// assign a health value
        super("Health Potion");
        Random random = new Random();
        healthGain = 10*(random.nextInt(10)+1);// add health gain(will be in a range of 10 - 100 increasing by 10 each time
    }
}
