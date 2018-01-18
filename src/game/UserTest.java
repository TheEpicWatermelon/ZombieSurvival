package game;

import org.junit.Test;
import zombies.QuickZombie;
import zombies.RegularZombie;
import zombies.ToughZombie;
import zombies.Zombie;

import static org.junit.Assert.*;

/**
 * [UserTest.java]
 * a series of methods that test the User class
 * @author Sasha Maximovitch
 * @data January 6th, 2018
 */
public class UserTest {
    @Test// check every single type of class in both melee and ranged against every single type of zombie
    public void attackZombie() throws Exception {
        System.out.println("REGULAR ZOMBIE - MELEE");
        for(int userType = 0; userType < 5; userType ++) {// regular zombie - melee

            int failcount = 0;
            int rounds = 10000;
            for (int i = 0; i < rounds; i++) {
                Zombie zombie = new RegularZombie(0, 0);
                User user = new User();
                user.setxCoord(0);
                user.setyCoord(1);
                user.setClass(userType);

                int healthBefore = zombie.getHealth();
                user.attackZombie(zombie, false);
                //System.out.println(healthBefore + " -> " + user.getHealth());
                if (healthBefore == zombie.getHealth()) {
                    failcount++;
                }
            }
            System.out.println("User type: " + userType + "  Attack failed " + failcount + " times (" + ((failcount * 100) / rounds) + "%)");
        }

        System.out.println("REGULAR ZOMBIE - RANGED");
        for(int userType = 0; userType < 5; userType ++) {// regular zombie - melee

            int failcount = 0;
            int rounds = 10000;
            for (int i = 0; i < rounds; i++) {
                Zombie zombie = new RegularZombie(0, 0);
                User user = new User();
                user.setxCoord(0);
                user.setyCoord(1);
                user.setClass(userType);

                int healthBefore = zombie.getHealth();
                user.attackZombie(zombie, true);
                //System.out.println(healthBefore + " -> " + user.getHealth());
                if (healthBefore == zombie.getHealth()) {
                    failcount++;
                }
            }
            System.out.println("User type: " + userType + "  Attack failed " + failcount + " times (" + ((failcount * 100) / rounds) + "%)");
        }

        System.out.println("QUICK ZOMBIE - MELEE");
        for(int userType = 0; userType < 5; userType ++) {// regular zombie - melee

            int failcount = 0;
            int rounds = 10000;
            for (int i = 0; i < rounds; i++) {
                Zombie zombie = new QuickZombie(0, 0);
                User user = new User();
                user.setxCoord(0);
                user.setyCoord(1);
                user.setClass(userType);

                int healthBefore = zombie.getHealth();
                user.attackZombie(zombie, false);
                //System.out.println(healthBefore + " -> " + user.getHealth());
                if (healthBefore == zombie.getHealth()) {
                    failcount++;
                }
            }
            System.out.println("User type: " + userType + "  Attack failed " + failcount + " times (" + ((failcount * 100) / rounds) + "%)");
        }

        System.out.println("REGULAR ZOMBIE - RANGED");
        for(int userType = 0; userType < 5; userType ++) {// regular zombie - melee

            int failcount = 0;
            int rounds = 10000;
            for (int i = 0; i < rounds; i++) {
                Zombie zombie = new QuickZombie(0, 0);
                User user = new User();
                user.setxCoord(0);
                user.setyCoord(1);
                user.setClass(userType);

                int healthBefore = zombie.getHealth();
                user.attackZombie(zombie, true);
                //System.out.println(healthBefore + " -> " + user.getHealth());
                if (healthBefore == zombie.getHealth()) {
                    failcount++;
                }
            }
            System.out.println("User type: " + userType + "  Attack failed " + failcount + " times (" + ((failcount * 100) / rounds) + "%)");
        }

        System.out.println("TOUGH ZOMBIE - MELEE");
        for(int userType = 0; userType < 5; userType ++) {// regular zombie - melee

            int failcount = 0;
            int rounds = 10000;
            for (int i = 0; i < rounds; i++) {
                Zombie zombie = new ToughZombie(0, 0);
                User user = new User();
                user.setxCoord(0);
                user.setyCoord(1);
                user.setClass(userType);

                int healthBefore = zombie.getHealth();
                user.attackZombie(zombie, false);
                //System.out.println(healthBefore + " -> " + user.getHealth());
                if (healthBefore == zombie.getHealth()) {
                    failcount++;
                }
            }
            System.out.println("User type: " + userType + "  Attack failed " + failcount + " times (" + ((failcount * 100) / rounds) + "%)");
        }

        System.out.println("TOUGH ZOMBIE - RANGED");
        for(int userType = 0; userType < 5; userType ++) {// regular zombie - melee

            int failcount = 0;
            int rounds = 10000;
            for (int i = 0; i < rounds; i++) {
                Zombie zombie = new ToughZombie(0, 0);
                User user = new User();
                user.setxCoord(0);
                user.setyCoord(1);
                user.setClass(userType);

                int healthBefore = zombie.getHealth();
                user.attackZombie(zombie, true);
                //System.out.println(healthBefore + " -> " + user.getHealth());
                if (healthBefore == zombie.getHealth()) {
                    failcount++;
                }
            }
            System.out.println("User type: " + userType + "  Attack failed " + failcount + " times (" + ((failcount * 100) / rounds) + "%)");
        }
    }// end attackZombie
}// end UserTest class