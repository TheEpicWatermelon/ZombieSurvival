package zombies;

// imports
import game.User;
import org.junit.Test;

/**
 * [ZombieTest.java]
 * a method that tests a zombie's attack method
 * @author Sasha Maximovitch
 * @date January 6th, 2018
 */
public class ZombieTest {
    @Test
    public void attackPlayer() throws Exception {// Test a zombie attack with each type of zombie on each type of player
        for(int userType = 0; userType < 5; userType ++) {

            int failcount = 0;
            int rounds = 10000;
            for (int i = 0; i < rounds; i++) {
                Zombie zombie = new RegularZombie(0, 0);
                User user = new User();
                user.setxCoord(0);
                user.setyCoord(1);
                user.setClass(userType);

                int healthBefore = user.getHealth();
                zombie.attackPlayer(user);
                //System.out.println(healthBefore + " -> " + user.getHealth());
                if (healthBefore == user.getHealth()) {
                    failcount++;
                }
            }
            System.out.println("User type: " + userType + "  Attack failed " + failcount + " times (" + ((failcount * 100) / rounds) + "%)");
        }

        for(int userType = 0; userType < 5; userType ++) {

            int failcount = 0;
            int rounds = 10000;
            for (int i = 0; i < rounds; i++) {
                Zombie zombie = new QuickZombie(0, 0);
                User user = new User();
                user.setxCoord(0);
                user.setyCoord(1);
                user.setClass(userType);

                int healthBefore = user.getHealth();
                zombie.attackPlayer(user);
                //System.out.println(healthBefore + " -> " + user.getHealth());
                if (healthBefore == user.getHealth()) {
                    failcount++;
                }
            }
            System.out.println("User type: " + userType + "  Attack failed " + failcount + " times (" + ((failcount * 100) / rounds) + "%)");
        }

        for(int userType = 0; userType < 5; userType ++) {

            int failcount = 0;
            int rounds = 10000;
            for (int i = 0; i < rounds; i++) {
                Zombie zombie = new ToughZombie(0, 0);
                User user = new User();
                user.setxCoord(0);
                user.setyCoord(1);
                user.setClass(userType);

                int healthBefore = user.getHealth();
                zombie.attackPlayer(user);
                //System.out.println(healthBefore + " -> " + user.getHealth());
                if (healthBefore == user.getHealth()) {
                    failcount++;
                }
            }
            System.out.println("User type: " + userType + "  Attack failed " + failcount + " times (" + ((failcount * 100) / rounds) + "%)");
        }
    }
}// end ZombieTest class