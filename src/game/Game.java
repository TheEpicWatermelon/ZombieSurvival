package game;

// imports
import zombies.Zombie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * [Game.java]
 * a state machine that controls the interaction between the server and the game, processes user's turns, sets the map, etc
 * @author Sasha Maximovitch
 * @date January 9th, 2017
 */

public class Game {

    // class variables
    private List<User> users = Collections.synchronizedList(new ArrayList<>());
    private List<Zombie> zombies = new ArrayList<>();
    private GameMap map;
    private int wave;// holds the wave number, this dictates the number of zombies spawned per wave
    private int turn;// holds which user's turn it is
    private int actionMoves;// holds the current user's action moves

    // constructor
    Game(List<User> users){
        map = new GameMap();
        wave = 1;
        turn = users.get(0).getListNum();
        actionMoves = users.get(0).getSpeed();// set the first player's action move(how many actions they can do) based on the user's speed
        this.users = users;
        for(User user: this.users){// add all the user's to the map
            map.addUser(user);
        }
        map.placeUsers();// spawn all the user's on the map
        map.placeZombies(wave);
        zombies = map.getZombies();
    }

    /**
     * getUserCoords
     * gets all the coordinates of the user, this will be used at the beginning of the game so that every player knows their coordinates
     * @return a string of all the player's coordinates seperated by semi-colons
     */
    public String getUserCoords(){
        StringBuilder string = new StringBuilder();
        for (User user: users){// loop through all the users
            string.append(user.getxCoord()+";"+user.getyCoord()+";");// get coordinates
        }
        return string.toString();// return the list of coordinates
    }// end getUserCoords

    /**
     * getZombieCoords
     * get the coordinates of all the zombies, will be sent at the beginning of the game
     * @return a string of all the zombie's coordinates, separated by semi-colons
     */
    public String getZombieCoords(){
        StringBuilder string = new StringBuilder();
        for(Zombie zombie: zombies){// loop through all the zombies
            string.append(zombie.getxCoord()+";"+zombie.getyCoord()+";");// get coordinates
        }

        return string.toString();// return coordinates
    }// end getZombieCoords

    /**
     * moveUser
     * gets the user that is moving and their target coordinates, then goes to map to see if the user can move
     * @param user
     * @param coord
     * @return true or false depending on if the move was successful
     */

    public boolean moveUser(User user, Coord coord) {
        boolean moved = map.moveUser(user, coord);

        if (moved){// if move was successful then send true
            actionMoves--;// decrease action moves
            return true;
        }else{// if move was not successful
            return false;
        }
    }// end moveUser

    /**
     * userAttack
     * gets the user that is attacking and which coordinates they want to attack, then processes if the attack was successful or not
     * @param user
     * @param zombieCoords
     * @return
     */

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

        double distanceToZombie = Math.sqrt(Math.pow( (user.getxCoord() + attackedZombie.getxCoord()), 2) + Math.pow( (user.getyCoord() + attackedZombie.getyCoord()), 2));// get distance to zombie

        if (user.getInventory(user.getCurrentItem()).range < distanceToZombie){// check if user with equipped weapon is in range of zombie
            return false;// if not then return attack fail
        }

        boolean ranged = false;

        if (user.getInventory(user.getCurrentItem()).range > Math.sqrt(2)){// check if the user's weapon is a ranged weapon
            ranged = true;
        }

        user.attackZombie(attackedZombie, ranged);// attack the zombie

        if (attackedZombie.getHealth() <= 0){// if zombie loses all of its health, remove it from the map
            zombies.remove(attackedZombie);
            map.removeZombie(attackedZombie);

            // give all user's the coordinates of the zombie that died
            Server.connectionHandlers.get(0).writeToUsers(Server.GAME_ZOMBIE_DEAD + attackedZombie.getxCoord()+attackedZombie.getyCoord());
            attackedZombie = null;
        }

        actionMoves--;// decrease actionMoves by one
        return true;// return attack success
    }// end userAttack

    /**
     * mapToString
     * get the whole map as one long string
     * @return the whole map as a string
     */
    public String mapToString(){// get map as one string
        return map.mapToString();
    }// end mapToString

    /**
     * getTurn
     * get the current turn(which player is moving)
     * @return the turn
     */

    public int getTurn() {
        return turn;
    }// end getTurn

    /**
     * processTurn
     * after every action, this method is called by the server to see if the user's turn has ended, if it hasn't return the
     * same player's turn, if not check if all player's moved(if turn > #of players), if not then do the zombie moves and then
     * go back to the first player's turn
     * @return - the turn number
     */

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
    }// end processTurn
}// end Game class
