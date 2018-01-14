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
    private int turn;// holds which user's turn it is
    private int actionMoves;// holds the current user's action moves

    // TODO - ADD USER ACTION MOVES PROCESSING

    // constructor
    Game(List<User> users){
        map = new GameMap();
        wave = 1;
        turn = users.get(0).getListNum();
        actionMoves = users.get(0).getSpeed();
        this.users = users;
        for(User user: this.users){
            map.addUser(user);
        }
        map.placeUsers();
        map.placeZombies(wave);
        zombies = map.getZombies();
    }

    public String getUserCoords(){
        StringBuilder string = new StringBuilder();
        for (User user: users){// loop through all the users
            string.append(user.getxCoord()+";"+user.getyCoord()+";");// get coordinates
        }
        return string.toString();// return the list of coordinates
    }

    public String getZombieCoords(){
        StringBuilder string = new StringBuilder();
        for(Zombie zombie: zombies){// loop through all the zombies
            string.append(zombie.getxCoord()+";"+zombie.getyCoord()+";");// get coordinates
        }

        return string.toString();// return coordinates
    }

    public boolean moveUser(User user, Coord coord) {
        boolean moved = map.moveUser(user, coord);

        if (moved){// if move was successful then send true
            actionMoves--;// decrease action moves
            return true;
        }else{// if move was not successful
            return false;
        }
    }

    public boolean userAttack(User user, Coord zombieCoords){
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
            zombies.remove(attackedZombie);

            Server.connectionHandlers.get(0).writeToUsers(Server.GAME_ZOMBIE_DEAD + attackedZombie.getxCoord()+attackedZombie.getyCoord());
            attackedZombie = null;
        }

        actionMoves--;// decrease actionMoves by one
        return true;// return attack success
    }

    public String mapToString(){// get map as one string
        return map.mapToString();
    }

    public int getTurn() {
        return turn;
    }

    public int processTurn(){
        if (actionMoves > 0){// if its still the user's turn, just return that it is still their turn
            return turn;
        }

        // if the user's turn ended, set the next turn to the next user
        turn++;
        if (turn > users.get(users.size()-1).getListNum()){// if all the users did a turn, do the zombie moves
            if (zombies.size() != 0) {// if there are still zombies alive
                List<ZombieMove> zombieMoves = map.moveZombies();// move the zombies
                for (ZombieMove zombieMove : zombieMoves) {// loop through all the moves and send them
                    String move = zombieMove.toString();
                    Server.connectionHandlers.get(0).writeToUsers(Server.GAME_ZOMBIE_MOVE + move);// send the moves for each zombie one at a time
                }
                users = Server.users;// refresh list of users
                turn = users.get(0).getListNum();// set the turn to the first user
                return turn;
            }else{// if all the zombies are dead
                // reheal all users
                for(User user: users){
                    user.setHealth(user.getMaxHealth());// set all their health to the max health
                    Server.connectionHandlers.get(user.getListNum()-1).write(Server.GAME_USER_HEALTH+user.getHealth());// give users their health
                    wave ++;// increase the wave by one
                    map.placeZombies(wave);// spawn the zombies
                    zombies = map.getZombies();
                    Server.connectionHandlers.get(0).writeToUsers(Server.GAME_ZOMBIE_SPAWNS + getZombieCoords());// send zombie coordinates to user;
                    turn = users.get(0).getListNum();
                    return turn;//return the turn for the first player
                }
            }
        }

        return turn;// if its not the end of all the user's turns just send the next user's turn
    }


}
