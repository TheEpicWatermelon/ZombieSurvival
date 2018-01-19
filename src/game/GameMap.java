package game;
// imports
import zombies.QuickZombie;
import zombies.RegularZombie;
import zombies.ToughZombie;
import zombies.Zombie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * [GameMap.java]
 * holds all the data and methods for the map of the game
 * @author Sasha Maximovitch
 * @date December 22nd, 2017
 */
public class GameMap {

    public Tile[][] mapTiles = new Tile[50][50];// holds the tiles for the map
    // class variables
    public static final int mapXDimension = 50;
    public static final int mapYDimension = 50;
    private List<User> users = Collections.synchronizedList(new ArrayList<>());
    private List<Zombie> zombies = new ArrayList<>();
    private final Random random = new Random();

    // constructor
    GameMap() {
        emptyMap();// make the map empty to start

        // get number of tree, cobble, fence groups and the number of houses
        int treeGroups = random.nextInt(11) + 20;
        int cobbleGroups = random.nextInt(6) + 15;
        int fenceGroups = random.nextInt(6) + 8;
        int houses = random.nextInt(6)+5;
        //int chests = random.nextInt(11)+10;

        // add all the trees, cobblestone, fences and houses
        addTrees(treeGroups);
        addCobbleStone(cobbleGroups);
        addFences(fenceGroups);
        addHouses(houses);
        //addChests(chests);
    }

    /**
     * removeZombie
     * get's a zombie to be removed from the map's list of zombies
     * @param zombie to be removed
     */

    public void removeZombie(Zombie zombie){
        zombies.remove(zombie);
    }// end removeZombie

    /**
     * removeUser
     * get's a user to be removed from the map's list of users
     * @param user to be removed
     */
    public void removeUser(User user){
        zombies.remove(user);
    }// end removeUser

    /**
     * mapToString
     * gets the whole map as one long string
     * @return a string that holds the map
     */
    public String mapToString(){
        StringBuilder string = new StringBuilder();//use string builder to add strings together
        for (int i = 0; i < mapTiles.length; i++) {
            for (int j = 0; j < mapTiles[0].length; j++) {
                // if this tile has zombie, put Z
                if (isZombieOn(j,i)){
                    string.append("Z");
                }else if(isUserOn(j,i)){// if there is a user on that tile, print X
                    string.append("X");
                }else {
                    string.append(mapTiles[i][j]);
                }
            }
        }
        return string.toString();
    }// end mapToString

    /**
     * isZombieOn
     * checks if a zombie is on the coordinates
     * @param x
     * @param y
     * @return true or false if zombie is on
     */

    public boolean isZombieOn(int x, int y) {
        for (Zombie zombie: zombies){
            if ( (zombie.getxCoord() == x) && (zombie.getyCoord() == y) ){
                return true;
            }
        }
        return false;
    }// end isZombieOn

    /**
     * isUserOn
     * checks if user is on the coordinates
     * @param x
     * @param y
     * @return true or false depending on if the user is on those coordinates
     */
    public boolean isUserOn(int x, int y) {
        for (User user: users){
            if ( (user.getxCoord() == x) && (user.getyCoord() == y) ){
                return true;
            }
        }
        return false;
    }// end isUserOn

    /**
     * getZombies
     * get's a list of zombies on the map
     * @return list of zomibes
     */
    public List<Zombie> getZombies(){
        return zombies;
    }// end getZombies

    /**
     * addUser
     * adds a user to the map
     * @param user to be added
     */
    public void addUser(User user){
        users.add(user);
    }// end addUser

    /**
     * placeUsers
     * spawns all the user's on the map
     */
    public void placeUsers(){
        // loop through all the users
        for(User user: users){
            Coord coords = findValidUserSpawn();// get a valid spawn then set that to the user's x and y coordinates
            user.setxCoord(coords.x);
            user.setyCoord(coords.y);
        }
    }// end placeUsers

    /**
     * findValidUserSpawn
     * loops through random coordinates and returns a valid coordinate for a user to spawn on
     * @return coordinates for a user's spawn
     */

    private Coord findValidUserSpawn() {
        Coord possibleCoords;
        do{
            possibleCoords = getRandomUserCoords();
        }while (!checkForValidSpawn(possibleCoords));

        return possibleCoords;
    }// end findValidUserSpawn

    /**
     * getRandomUserCoords
     * gets random coordinates near the centre of the map that will be used to get a user spawn
     * @return random coordinates
     */

    public Coord getRandomUserCoords() {
        int randx = random.nextInt(8)+1;// pick random distance from middle in x direction
        int randy = random.nextInt(8) + 1;// pick random distance from middle in y direction
        return new Coord(mapXDimension/2 + randx, mapYDimension/2 + randy);// return the coordinates for that random position near the middle of the map;
    }// end getRandomUserCoords

    /**
     * placeZombies
     * goes through all the zombies and places them on the map
     * @param wave
     */

    public void placeZombies(int wave) {
        int numberOfZombies = wave * 4;// number of zombie's is based on the wave number
        int numRegularZombies = numberOfZombies /2;// half of the zombies will be regular
        int numQuickZombies = numRegularZombies/2; // quarter of the zombies will be quick
        int numToughZombies = numRegularZombies/2;// quarter of the zombies will be tough

        // place regular zombies first
        for (int i = 0; i < numRegularZombies; i++) {
            int spawnSide = random.nextInt(3); // pick a random spawn side for the zombie, 0 top, 1 right, 2 bottom, 3 left
            Coord coords = findValidZombieSpawn(spawnSide);
            addZombie(new RegularZombie(coords.x, coords.y));
        }

        // place quick zombies
        for (int i = 0; i < numQuickZombies; i++) {
            int spawnSide = random.nextInt(3); // pick a random spawn side for the zombie, 0 top, 1 right, 2 bottom, 3 left
            Coord coords = findValidZombieSpawn(spawnSide);
            addZombie(new QuickZombie(coords.x, coords.y));
        }

        // place tough zombies
        for (int i = 0; i < numToughZombies; i++) {
            int spawnSide = random.nextInt(3); // pick a random spawn side for the zombie, 0 top, 1 right, 2 bottom, 3 left
            Coord coords = findValidZombieSpawn(spawnSide);
            addZombie(new ToughZombie(coords.x, coords.y));
        }
    }// end placeZombies

    /**
     * findValidZombieSpawn
     * gets random coordinates for the zombie to spawn on
     * @param side - on which side of the map the zombie will spawn
     * @return coordinates of the spawn
     */

    private Coord findValidZombieSpawn(int side){
        Coord possibleCoords;// loop through random coordinates
        do{
            possibleCoords = getRandomZombieSpawnCoords(side);

        }while(!checkForValidSpawn(possibleCoords));

        return possibleCoords;
    }// end findValidZombieSpawn

    /**
     * checkValidSpawn
     * gets coordinates and checks if those coordinates are a valid spawn
     * @param coords of spawn
     * @return return if spawn is valid or not
     */

    private boolean checkForValidSpawn(Coord coords){
        boolean canMove = false;
        for (User user: users){
            if(user.getxCoord() == coords.x && user.getyCoord() == coords.y){// check if coordinate already has a user on it
                return false; // return false if there is a user on those coordinates
            }
        }
        // check if able to spawn at coordinates(can only spawn on grass)
        if(mapTiles[coords.y][coords.x].getTileType() != 0){
            return false;
        }// check left, check if zombie/user can move to that tile
        if ( canMoveTo(coords.x-1,coords.y) ){
            canMove = true;
        }// check up
        if ( canMoveTo(coords.x, coords.y+1) ){
            canMove = true;
        }// check right
        if ( canMoveTo(coords.x+1, coords.y) ){
            canMove = true;
        }// check down
        if ( canMoveTo(coords.x,coords.y+1) ){
            canMove = true;
        }
        return canMove;
    }// end checkForValidSpawn

    /**
     * getRandomZombieSpawnCoords
     * gets random coordinates that zombie might spawn on(will be validated)
     * @param side - side the zombie spawns on
     * @return possible coordinates of a zombie spawn
     */

    private Coord getRandomZombieSpawnCoords(int side){
        int spawnXCoordinates = -1;
        int spawnYCoordinates = -1;
        switch(side){// spawn zombies on the selected side, with random coordinates
            case 0:
                spawnXCoordinates = random.nextInt(mapXDimension);
                spawnYCoordinates = 0;
                break;
            case 1:
                spawnXCoordinates = mapXDimension -1;
                spawnYCoordinates = random.nextInt(mapYDimension);
                break;
            case 2:
                spawnXCoordinates = random.nextInt(mapXDimension);
                spawnYCoordinates = mapYDimension -1;
                break;
            case 3:
                spawnXCoordinates = 0;
                spawnYCoordinates = random.nextInt(mapYDimension);
                break;
            default:
                break;
        }

        return new Coord(spawnXCoordinates,spawnYCoordinates);
    }// end getRandomZombieSpawnCoords

    /**
     * addZombie
     * adds zombie to the map's list of zombies
     * @param zombie zombie to be added
     */
    public void addZombie(Zombie zombie){
        zombies.add(zombie);
    }// end addZombie

    /**
     * changeTile
     * gets the coordinates of the tile to be changed and which tile type it should be changed to
     * @param yCoord
     * @param xCoord
     * @param tileType
     */

    public void changeTile(int yCoord, int xCoord, int tileType) {
        mapTiles[yCoord][xCoord].setTileType(tileType);
    }// end changeTile

    /**TESTING PURPOSES ONLY
     * displayMap
     * displays the map in the console
     */

    public void displayMap() {
        for (int i = 0; i < mapYDimension; i++) {
            for (int j = 0; j < mapXDimension; j++) {
                System.out.print(mapTiles[i][j]);
            }
            System.out.println();
        }
    }// end displayMap

    /**
     * emptyMap
     * sets all the tiles on the map to grass(used in the constructor)
     */

    private void emptyMap() {
        for (int i = 0; i < mapYDimension; i++) {
            for (int j = 0; j < mapXDimension; j++) {
                mapTiles[i][j] = new Tile();
                mapTiles[i][j].setTileType(0);
            }
        }
    }// end emptyMap

    /**
     * addTrees
     * add trees to the map depending on the number of tree groups calculated(used in constructor)
     * @param treeGroups
     */

    private void addTrees(int treeGroups) {
        Random random = new Random();
        int waitTime = 0;// wait time is used when looping through the map so that not all the tree groups get placed at once

        outerloop:// loop through the whole map
        for (int i = 0; i < mapYDimension; i++) {
            for (int j = 0; j < mapXDimension; j++) {
                if (waitTime != 0) {// wait for waitTime to count down
                    waitTime--;
                    continue;
                }
                // if there are no more treeGroups to be spawned then exit the loop
                if (treeGroups == 0) {
                    break outerloop;
                }

                int yCoord = i;
                int xCoord = j;
                int randomNum = random.nextInt(100);// random number is generated to see if the tree will spawn on that tile or not
                if (randomNum > 70 && randomNum < 72) {// if tree spawns on that tile, map a group of trees
                    treeGroups--;
                    int numberOfTrees = random.nextInt(6) + 1;// get the number of trees in the group(randomly chosen)
                    mapTiles[yCoord][xCoord].setTileType(1);
                    numberOfTrees--;
                    int up = 1;
                    int down = 1;
                    int right = 1;
                    int left = 1;
                    while (numberOfTrees > 0) {
                        int direction = random.nextInt(4);// get random directions to place trees
                        if (direction == 0) {//up
                            if (yCoord - up >= 0) {
                                mapTiles[yCoord - up][xCoord].setTileType(1);
                                up++;
                                numberOfTrees--;
                            }
                        } else if (direction == 1) {// right
                            if (xCoord + right < mapXDimension) {
                                mapTiles[yCoord][xCoord + right].setTileType(1);
                                right++;
                                numberOfTrees--;
                            }
                        } else if (direction == 2) {// down
                            if (yCoord + down < mapYDimension) {
                                mapTiles[yCoord + down][xCoord].setTileType(1);
                                down++;
                                numberOfTrees--;
                            }
                        } else {// left
                            if (xCoord - left >= 0) {
                                mapTiles[yCoord][xCoord - left].setTileType(1);
                                left++;
                                numberOfTrees--;
                            }
                        }
                    }
                    waitTime = random.nextInt(41) + 30;// set wait time
                }
            }
        }
    }// end addTrees

    /**
     * addCobbleStone
     * add cobblestone to the map depending on the number of cobbleGroups calculated(used in constructor)
     * @param cobbleGroups
     */

    private void addCobbleStone(int cobbleGroups) {
        Random random = new Random();
        int waitTime = 0;// wait time that will skip over when looping through tiles

        outerloop:
        for (int i = 0; i < mapYDimension; i++) {
            for (int j = 0; j < mapXDimension; j++) {
                if (waitTime != 0) {// wait time counting down
                    waitTime--;
                    continue;
                }

                if (cobbleGroups == 0) {// if no more groups of cobblestone can be spawned
                    break outerloop;
                }

                int yCoord = i;
                int xCoord = j;
                int randomNum = random.nextInt(100);
                if (randomNum > 10 && randomNum < 12) {// if cobble stone is spawned on that tile
                    cobbleGroups--;
                    int numberOfCobble = random.nextInt(4) + 2;// see how many pieces of cobble will be spawned in that group
                    mapTiles[yCoord][xCoord].setTileType(1);
                    numberOfCobble--;
                    int up = 1;
                    int down = 1;
                    int right = 1;
                    int left = 1;
                    while (numberOfCobble > 0) {// spawn cobble in random directions
                        int direction = random.nextInt(4);
                        if (direction == 0) {//up
                            if (yCoord - up >= 0) {
                                mapTiles[yCoord - up][xCoord].setTileType(2);
                                up++;
                                numberOfCobble--;
                            }
                        } else if (direction == 1) {// right
                            if (xCoord + right < mapXDimension) {
                                mapTiles[yCoord][xCoord + right].setTileType(2);
                                right++;
                                numberOfCobble--;
                            }
                        } else if (direction == 2) {// down
                            if (yCoord + down < mapYDimension) {
                                mapTiles[yCoord + down][xCoord].setTileType(2);
                                down++;
                                numberOfCobble--;
                            }
                        } else {// left
                            if (xCoord - left >= 0) {
                                mapTiles[yCoord][xCoord - left].setTileType(2);
                                left++;
                                numberOfCobble--;
                            }
                        }
                    }
                    waitTime = random.nextInt(21) + 20;
                }
            }
        }
    }// end addCobbleStone

    /**
     * addFences
     * adds fences to the map depending on the number of fence groups calculated(used in the constructor)
     * @param fenceGroups
     */
    private void addFences(int fenceGroups) {
        Random random = new Random();
        int waitTime = 0;// wait time

        outerloop:
        for (int i = 10; i < mapYDimension - 10; i++) {// therefore fences can't go out of bounds since max length is 8
            for (int j = 10; j < mapXDimension - 10; j++) {
                if (waitTime != 0) {// wait time counts down
                    waitTime--;
                    continue;
                }

                if (fenceGroups == 0) {// if no more fence groups to be spawned then stop looping
                    break outerloop;
                }

                int yCoord = i;
                int xCoord = j;
                int randomNum = random.nextInt(100);
                if (randomNum > 57 && randomNum < 59) {// if fence starts on that tile build fence group
                    fenceGroups--;
                    int direction = random.nextInt(2);// 0 is horizontal, 1 is vertical
                    int length = random.nextInt(6)+3;

                    if (direction == 0){// horizontal
                        mapTiles[yCoord][xCoord].setTileType(3);
                        length--;

                        int left = 1;
                        int right = 1;
                        while (length > 0) {
                            int placement = random.nextInt(2);//0 is right, 1 is left
                            if (placement == 0) {
                                mapTiles[yCoord][xCoord + right].setTileType(3);
                                right++;
                                length--;
                            }else{
                                mapTiles[yCoord][xCoord-left].setTileType(3);
                                left--;
                                length--;
                            }
                        }
                    }else{// vertical
                        mapTiles[yCoord][xCoord].setTileType(3);
                        length--;

                        int up = 1;
                        int down = 1;
                        while (length > 0) {
                            int placement = random.nextInt(2);//0 is right, 1 is left
                            if (placement == 0) {
                                mapTiles[yCoord - up][xCoord].setTileType(3);
                                up++;
                                length--;
                            }else{
                                mapTiles[yCoord+down][xCoord].setTileType(3);
                                down--;
                                length--;
                            }
                        }
                    }
                    waitTime = random.nextInt(10) + 20;
                }
            }
        }
    }// end addFences

    /**
     * addHouses
     * add houses to the map depending on the number of houses calculated(used in the constructor)
     * @param houses
     */
    private void addHouses(int houses) {
        Random random = new Random();

        outerloop:
        for (int i = 15; i < mapYDimension - 10; i++) {// houses maximum length is 6 so it will not go out of bounds
            for (int j = 15; j < mapXDimension - 10; j++) {
                if (houses == 0){
                    break outerloop;
                }

                int yCoord = i;
                int xCoord = j;
                int randomNum = random.nextInt(100);
                if (randomNum > 44 && randomNum < 46){// if the random number picks 45 then a house can be made
                    int houseDimension = random.nextInt(3)+4;
                    int direction = random.nextInt(4);// 0 is up then right, 1 is down then right, 2 is up then left, 3 is down then left(how the house is built)

                    boolean houseNearby = false;
                    for (int k = yCoord+houseDimension; k >= yCoord - houseDimension; k--) {// check if there is a house nearby
                        for (int l = xCoord - houseDimension; l <= xCoord+houseDimension ; l++) {
                            if (mapTiles[k][l].getTileType() == 4){
                                houseNearby = true;
                            }
                        }
                    }
                    if (houseNearby){// if there is a house nearby, don't create a house
                        break;
                    }
                    houses--;

                    // create the house

                    if (direction == 0){// up then right
                        for (int k = yCoord; k > yCoord - houseDimension; k--) {//walls up
                            mapTiles[k][xCoord].setTileType(4);
                            mapTiles[k][xCoord + houseDimension -1].setTileType(4);
                        }
                        for (int k = xCoord; k < xCoord + houseDimension; k++) {
                            mapTiles[yCoord][k].setTileType(4);
                            mapTiles[yCoord-houseDimension+1][k].setTileType(4);
                        }

                        // make every tile inside grass
                        for (int k = yCoord - 1; k > yCoord-houseDimension+1; k--) {
                            for (int l = xCoord + 1; l < xCoord + houseDimension - 1; l++) {
                                mapTiles[k][l].setTileType(0);
                            }
                        }

                        // create door
                        int side = random.nextInt(4); // 0 top , 1 right, 2 bottom, 3 left
                        int placement = random.nextInt(houseDimension-2) + 1;
                        if (side == 0){// top
                            mapTiles[yCoord - houseDimension +1][xCoord + placement].setTileType(0);
                        }else if(side == 1){// right
                            mapTiles[yCoord - placement][xCoord + houseDimension - 1].setTileType(0);
                        }else if (side == 2) {// bottom
                            mapTiles[yCoord][xCoord+placement].setTileType(0);
                        }else{// left
                            mapTiles[yCoord-placement][xCoord].setTileType(0);
                        }



                    }else if (direction == 1){// down then right
                        for (int k = yCoord; k < yCoord + houseDimension; k++) {//walls up
                            mapTiles[k][xCoord].setTileType(4);
                            mapTiles[k][xCoord + houseDimension -1].setTileType(4);
                        }
                        for (int k = xCoord; k < xCoord + houseDimension; k++) {// walls left
                            mapTiles[yCoord][k].setTileType(4);
                            mapTiles[yCoord+houseDimension-1][k].setTileType(4);
                        }

                        // make every tile inside grass
                        for (int k = yCoord + 1; k < yCoord+houseDimension-1; k++) {
                            for (int l = xCoord + 1; l < xCoord + houseDimension - 1; l++) {
                                mapTiles[k][l].setTileType(0);
                            }
                        }

                        // create door
                        int side = random.nextInt(4); // 0 top , 1 right, 2 bottom, 3 left
                        int placement = random.nextInt(houseDimension-2) + 1;
                        if (side == 0){// top
                            mapTiles[yCoord][xCoord + placement].setTileType(0);
                        }else if(side == 1){// right
                            mapTiles[yCoord + placement][xCoord + houseDimension - 1].setTileType(0);
                        }else if (side == 2) {// bottom
                            mapTiles[yCoord+houseDimension -1][xCoord+placement].setTileType(0);
                        }else{// left
                            mapTiles[yCoord+placement][xCoord].setTileType(0);
                        }


                    }else if (direction == 2){// up then left
                        for (int k = yCoord; k > yCoord - houseDimension; k--) {//walls up
                            mapTiles[k][xCoord].setTileType(4);
                            mapTiles[k][xCoord - houseDimension +1].setTileType(4);
                        }
                        for (int k = xCoord; k > xCoord - houseDimension; k--) {
                            mapTiles[yCoord][k].setTileType(4);
                            mapTiles[yCoord-houseDimension+1][k].setTileType(4);
                        }

                        // make every tile inside grass
                        for (int k = yCoord - 1; k > yCoord-houseDimension+1; k--) {
                            for (int l = xCoord - 1; l > xCoord - houseDimension + 1; l--) {
                                mapTiles[k][l].setTileType(0);
                            }
                        }

                        // create door
                        int side = random.nextInt(4); // 0 top , 1 right, 2 bottom, 3 left
                        int placement = random.nextInt(houseDimension-2) + 1;
                        if (side == 0){// top
                            mapTiles[yCoord - houseDimension +1][xCoord - placement].setTileType(0);
                        }else if(side == 1){// right
                            mapTiles[yCoord - placement][xCoord].setTileType(0);
                        }else if (side == 2) {// bottom
                            mapTiles[yCoord][xCoord-placement].setTileType(0);
                        }else{// left
                            mapTiles[yCoord-placement][xCoord-houseDimension+1].setTileType(0);
                        }



                    }else {// down then left
                        for (int k = yCoord; k < yCoord + houseDimension; k++) {//walls up
                            mapTiles[k][xCoord].setTileType(4);
                            mapTiles[k][xCoord - houseDimension +1].setTileType(4);
                        }
                        for (int k = xCoord; k > xCoord - houseDimension; k--) {
                            mapTiles[yCoord][k].setTileType(4);
                            mapTiles[yCoord+houseDimension-1][k].setTileType(4);
                        }

                        // make every tile inside grass
                        for (int k = yCoord + 1; k < yCoord+houseDimension-1; k++) {
                            for (int l = xCoord - 1; l > xCoord - houseDimension + 1; l--) {
                                mapTiles[k][l].setTileType(0);
                            }
                        }

                        // create door
                        int side = random.nextInt(4); // 0 top , 1 right, 2 bottom, 3 left
                        int placement = random.nextInt(houseDimension-2) + 1;
                        if (side == 0){// top
                            mapTiles[yCoord][xCoord - placement].setTileType(0);
                        }else if(side == 1){// right
                            mapTiles[yCoord + placement][xCoord].setTileType(0);
                        }else if (side == 2) {// bottom
                            mapTiles[yCoord+houseDimension -1][xCoord-placement].setTileType(0);
                        }else{// left
                            mapTiles[yCoord+placement][xCoord-houseDimension+1].setTileType(0);
                        }
                    }
                }
            }
        }
    }// end add houses

    /**
     * addChest
     * add chests to the map depending on the number of chests calculated(used in the constructor)
     * @param chests
     */
    private void addChests(int chests){
        int waitTime = 0;

        outerloop:// loop through the whole map
        for (int i = 0; i < mapYDimension; i++) {
            for (int j = 0; j < mapXDimension; j++) {
                if (waitTime != 0) {
                    waitTime--;
                    continue;
                }

                if (chests == 0) {// if there are no more chests left to be placed, break out of the loop
                    break outerloop;
                }

                int yCoord = i;
                int xCoord = j;
                int randomNum = random.nextInt(100);
                if (randomNum > 10 && randomNum < 12) {// if random number chooses 11 then place chest there
                    if (mapTiles[yCoord][xCoord].getTileType() == 4 || mapTiles[yCoord][xCoord].getTileType() == 3){
                        continue;
                    }
                    chests--;
                    mapTiles[yCoord][xCoord].setTileType(5);
                    waitTime = random.nextInt(21) + 20;
                }
            }
        }
    }// end add chests

    /**
     * nearestPlayer
     * gets a zombie and gives which user is the closest to it
     * @param zombie - which zombie to see from
     * @return nearest user
     */
    public User nearestPlayer(Zombie zombie){
        double minDistance = mapXDimension + 1;
        User closestPlayer = null;

        for (User user: users){
            double distance = Math.sqrt(Math.pow((zombie.getxCoord()-user.getxCoord()),2) + Math.pow((zombie.getyCoord() - user.getyCoord()),2));// get distance
            if (distance < minDistance){
                closestPlayer = user;
                minDistance = distance;
            }
        }
        return closestPlayer;
    }// end nearestPlayer

    /**
     * playersNextTo
     * get a list of players that are adjacent to a zombie
     * @param z zombie that will be checked around from
     * @return
     */

    public List<User> playersNextTo(Zombie z){
        return playersNextTo(z.getxCoord(), z.getyCoord());// calls the playersNextTo method with the zombie's coordinates
    }// end playersNextTo

    /**
     * playersNextTo
     * see which players are adjacent to a certain coordinates
     * @param x
     * @param y
     * @return list of users that are adjacent to that coordinate
     */

    public List<User> playersNextTo(int x, int y){
        List<User> players = new ArrayList<>();

        for(User user: users){
            if ( (Math.abs(user.getyCoord()-y) <2) && (Math.abs(user.getxCoord()-x) <2) ){// check if player is next to that coordinate
                players.add(user);
            }
        }
        return players;
    }// end playersNextTo

    /**
     * moveZombies
     * move's all the zombies on the map(this is done when the player's end all their turns
     * @return a list of zombie moves to be given to the users
     */

    public List<ZombieMove> moveZombies(){
        List<ZombieMove> moves = new ArrayList<>();
        for (Zombie zombie: zombies){
            moves.add(moveZombie(zombie));
            // loop through users an see if their health is at zero or below(dead)
            for (User user: users){
                Server.connectionHandlers.get(user.getListNum()-1).write(Server.GAME_USER_HEALTH+user.getHealth());//send all users their health
                if (user.getHealth() <= 0){
                    Server.connectionHandlers.get(user.getListNum()-1).write(Server.GAME_USER_DEAD );
                    Server.connectionHandlers.get(user.getListNum()-1).running = false;// close the user
                }
            }
        }
        return moves;
    }// end moveZombies

    /**
     * moveUser
     * gets a user and coordinates the user wants to move to, checks if the user can move there, if they can't then return false, if they can, move the user and return true
     * @param user
     * @param coord
     * @return boolean if user moved or not
     */

    public boolean moveUser(User user, Coord coord){
        // first calculate if the user can move to that coordinate
        double moveDistance = Math.sqrt( Math.pow( (user.getxCoord() - coord.x), 2) + Math.pow( (user.getyCoord() - coord.y), 2) );// get the distance to the square

        if (moveDistance > Math.sqrt(2)){// if the distance to move is greater than root 2 than it is impossible to move(root 2 is used because that is the distance to move diagonally)
            return false;
        }

        if (mapTiles[coord.y][coord.x].getTileType() != 0){// if tile type is not grass
            return false;
        }

        // now move the user
        user.setxCoord(coord.x);
        user.setyCoord(coord.y);

        return true;
    }// end moveUser

    /**
     * moveZombie
     * move a single zombie through all of its turn, return a ZombieMove object that will hold its movement and attacks
     * @param zombie
     * @return
     */

    public ZombieMove moveZombie(Zombie zombie) {
        ZombieMove move = new ZombieMove();
        int actions = zombie.getSpeed(); // number of actions the zombie has
        move.move.add(new Coord(zombie.getxCoord(),zombie.getyCoord()));// the first values in the move are the zombie's initial coordinates
        while(actions > 0){
            List<User> users = playersNextTo(zombie);// get a list of users that are adjacent to the zombie
            if (users.size() > 0){// if there are users next to zombie, randomly pick one to attack
                int index = random.nextInt(users.size());// pick a random user to attack
                move.attackedPlayer = users.get(index);// set attackedPlayer
                zombie.attackPlayer(users.get(index));
                break;// no more moves after an attack
            }// if there is no player next to zombie, check if a player is within 10 squares of a zombie
            User user = nearestPlayer(zombie);
            if (user != null){// if a user is within sight range of zombie
                Coord coords = moveZombieTowards(user,zombie);
                if (coords.x == -1){// no available moves
                    break;
                }else{// if there is a valid move, register the move
                    move.move.add(coords);
                    zombie.setxCoord(coords.x);
                    zombie.setyCoord(coords.y);
                    actions --;
                    continue;
                }
            }else {// else the zombie does a random move
                Coord coords = randomZombieMove(zombie);
                if (coords.x == -1){// no available moves
                    break;
                }else{
                    move.move.add(coords);
                    zombie.setxCoord(coords.x);
                    zombie.setyCoord(coords.y);
                    actions--;
                    continue;
                }

            }
        }
        return move;
    }// end moveZombie

    /**
     * randomZombieMove
     * gets random coordinate for a zombie to move to
     * @param zombie
     * @return coordinates for the zombie to move to
     */

    private Coord randomZombieMove(Zombie zombie) {

        List<Coord> possibleMoves = new ArrayList<>();

        if ( canMoveTo(zombie.getxCoord()-1,zombie.getyCoord()) ){
            possibleMoves.add(new Coord(zombie.getxCoord()-1, zombie.getyCoord()));
        }// check up
        if ( canMoveTo(zombie.getxCoord(), zombie.getyCoord()+1) ){
            possibleMoves.add(new Coord(zombie.getxCoord(), zombie.getyCoord()-1));
        }// check right
        if ( canMoveTo(zombie.getxCoord()+1, zombie.getyCoord()) ){
            possibleMoves.add(new Coord(zombie.getxCoord()+1, zombie.getyCoord()));
        }// check down
        if ( canMoveTo(zombie.getxCoord(),zombie.getyCoord()+1) ){
            possibleMoves.add(new Coord(zombie.getxCoord(),zombie.getyCoord()+1));
        }

        // check if there are possible moves
        if (possibleMoves.size() == 0){
            return new Coord(-1,-1);
        }

        // else just pick a random move
        int randomNum = random.nextInt(possibleMoves.size());
        // return the moves
        return possibleMoves.get(randomNum);
    }// end randomZombieMove

    /**
     * moveZombieTowards
     * give user and zombie, then get coordinates that will try and move the zombie closer to the user
     * @param user
     * @param zombie
     * @return coordinates for the zombie to move to
     */
    private Coord moveZombieTowards(User user, Zombie zombie) {
        // 4 possible ways to move to
        // arrange them in the order of best to worst
        // iterate in order, pick first that you could move to

        List<Coord> possibleMoves = new ArrayList<>();
        // check all 4 directions if zombie can move there
        // check left
        if ( canMoveTo(zombie.getxCoord()-1,zombie.getyCoord()) ){
            possibleMoves.add(new Coord(zombie.getxCoord()-1, zombie.getyCoord()));
        }// check up
        if ( canMoveTo(zombie.getxCoord(), zombie.getyCoord()+1) ){
            possibleMoves.add(new Coord(zombie.getxCoord(), zombie.getyCoord()+1));
        }// check right
        if ( canMoveTo(zombie.getxCoord()+1, zombie.getyCoord()) ){
            possibleMoves.add(new Coord(zombie.getxCoord()+1, zombie.getyCoord()));
        }// check down
        if ( canMoveTo(zombie.getxCoord(),zombie.getyCoord()+1) ){
            possibleMoves.add(new Coord(zombie.getxCoord(),zombie.getyCoord()+1));
        }

        if (possibleMoves.size() == 0){// if there are no possible moves
            return new Coord(-1,-1);// since they are both -1 that means there are no valid moves
        }

        double smallestDistance = mapXDimension + 1;
        int index = -1;
        // loop through all the possible moves and calculate the distance
        for (int i = 0; i < possibleMoves.size(); i++) {
            double distance = Math.sqrt(Math.pow(possibleMoves.get(i).x - user.getxCoord(), 2) + Math.pow(possibleMoves.get(i).y - user.getyCoord(), 2));// use distance formulate to calculate distance
            if (distance < smallestDistance){// if distance calculated is smaller than smallest distance already calculated
                smallestDistance = distance;// set smallest distance to that distance
                index = i;// get the index for return
            }
        }

        // give best move
        return possibleMoves.get(index);

    }// end moveZombieTowards

    /**
     * canMoveTo
     * gets coordinates and checks if user/zombie can move on that coordinate
     * @param x
     * @param y
     * @return true/false depending on if movement is possible
     */

    private boolean canMoveTo(int x, int y) {
        //check out of bounds
        if ( (x < 0) || (x >= mapXDimension) || (y < 0) || (y >= mapYDimension)){
            return false;
        }
        // check that there is something other than grass in X Y
        if (mapTiles[y][x].getTileType() != 0){
            return false;
        }
        // check that there are no zombies in X Y
        for (Zombie zombie: zombies) {
            if ( (zombie.getyCoord() == y) && (zombie.getxCoord() == x)){
                return false;
            }
        }
        return true;
    }// end canMoveTo
}// end GameMap
