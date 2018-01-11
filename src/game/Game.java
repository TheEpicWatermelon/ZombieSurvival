package game;

import zombies.Zombie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private List<User> users = Collections.synchronizedList(new ArrayList<>());
    private List<Zombie> zombies = new ArrayList<>();
    private GameMap map;
    private int wave;
    private int turn;

    // constructor
    Game(List<User> users){
        map = new GameMap();
        wave = 1;
        turn = 1;
        this.users = users;
        for(User user: this.users){
            map.addUser(user);
        }
        map.placeUsers();
        map.placeZombies(wave);
        zombies = map.getZombies();
    }

    public boolean attack(User user, Coord zombieCoords){/// TODO add integration of user's action moves decreasing
        // loop through all the zombies to find
        Zombie attackedZombie = null;
        for(Zombie zombie: zombies){
            if( (zombie.getxCoord() == zombieCoords.x) && (zombie.getyCoord() == zombieCoords.y) ){
                attackedZombie = zombie;
                break;
            }
        }
        if (attackedZombie == null){// if user gives wrong coordinates
            return false;// return attack fail
        }

        double distanceToZombie = Math.sqrt(Math.pow( (user.getxCoord() + attackedZombie.getxCoord()), 2) + Math.pow( (user.getyCoord() + attackedZombie.getyCoord()), 2));

        if (user.getInventory(user.getCurrentItem()).range < distanceToZombie){// check if user with equipped weapon is in range of zombie
            return false;// if not then return attack fail
        }

        boolean ranged = false;

        if (user.getInventory(user.getCurrentItem()).range > Math.sqrt(2)){// check if the user's weapon is a ranged weapon
            ranged = true;
        }

        user.attackZombie(attackedZombie, ranged);

        if (attackedZombie.getHealth() <= 0){
            attackedZombie = null;
            // TODO - give all users the location of the dead zombie
        }

        return true;// return attack success
    }
}
