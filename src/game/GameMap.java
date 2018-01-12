package game;

import zombies.QuickZombie;
import zombies.RegularZombie;
import zombies.ToughZombie;
import zombies.Zombie;

import java.util.*;

/**
 * Created by sasha on 12/22/2017.
 */
public class GameMap {

    private Tile[][] mapTiles = new Tile[50][50];

    private final int mapXDimension = 50;
    private final int mapYDimension = 50;
    private List<User> users = Collections.synchronizedList(new ArrayList<>());
    private List<Zombie> zombies = new ArrayList<>();
    private final Random random = new Random();

    GameMap() {
        emptyMap();

        int treeGroups = random.nextInt(11) + 20;
        int cobbleGroups = random.nextInt(6) + 15;
        int fenceGroups = random.nextInt(6) + 8;
        int houses = random.nextInt(6)+5;
        //int chests = random.nextInt(11)+10;

        addTrees(treeGroups);
        addCobbleStone(cobbleGroups);
        addFences(fenceGroups);
        addHouses(houses);
        //addChests(chests);
    }

    public void removeZombie(Zombie zombie){
        zombies.remove(zombie);
    }

    public void removeUser(User user){
        zombies.remove(user);
    }

    public String mapToString(){
        StringBuilder string = new StringBuilder();//use string builder to add strings together
        for (int i = 0; i < mapTiles.length; i++) {
            for (int j = 0; j < mapTiles[0].length; j++) {
                string.append(mapTiles[i][j]);
            }
        }
        return string.toString();
    }

    public List<Zombie> getZombies(){
        return zombies;
    }

    public void addUser(User user){
        users.add(user);
    }

    public void placeUsers(){
        // loop through all the users
        for(User user: users){
            Coord coords = findValidUserSpawn();
            user.setxCoord(coords.x);
            user.setyCoord(coords.y);
        }

    }

    private Coord findValidUserSpawn() {
        Coord possibleCoords;
        do{
            possibleCoords = getRandomUserCoords();
        }while (checkForValidSpawn(possibleCoords));

        return possibleCoords;
    }

    public Coord getRandomUserCoords() {
        int randx = random.nextInt(8)+1;// pick random distance from middle in x direction
        int randy = random.nextInt(8) + 1;// pick random distance from middle in y direction
        return new Coord(mapXDimension/2 + randx, mapYDimension/2 + randy);// return the coordinates for that random position near the middle of the map;
    }

    public void placeZombies(int wave) {
        int numberOfZombies = wave * 4;
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

    }

    private Coord findValidZombieSpawn(int side){
        Coord possibleCoords;
        do{
            possibleCoords = getRandomZombieSpawnCoords(side);

        }while(!checkForValidSpawn(possibleCoords));

        return possibleCoords;
    }

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
        }// check left
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
    }

    private Coord getRandomZombieSpawnCoords(int side){
        int spawnXCoordinates = -1;
        int spawnYCoordinates = -1;
        switch(side){
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
        }

        return new Coord(spawnXCoordinates,spawnYCoordinates);
    }

    public void addZombie(Zombie zombie){
        zombies.add(zombie);
    }

    public String getMap() {
        return null;
    }

    public int getMapXDimension() {
        return mapXDimension;
    }

    public int getMapYDimension() {
        return mapYDimension;
    }

    public void changeTile(int yCoord, int xCoord, int tileType) {
        mapTiles[yCoord][xCoord].setTileType(tileType);
    }

    public void displayMap() {
        for (int i = 0; i < mapYDimension; i++) {
            for (int j = 0; j < mapXDimension; j++) {
                System.out.print(mapTiles[i][j]);
            }
            System.out.println();
        }
    }

    private void emptyMap() {
        for (int i = 0; i < mapYDimension; i++) {
            for (int j = 0; j < mapXDimension; j++) {
                mapTiles[i][j] = new Tile();
                mapTiles[i][j].setTileType(0);
            }

        }
    }

    private void addTrees(int treeGroups) {
        Random random = new Random();
        int waitTime = 0;

        outerloop:
        for (int i = 0; i < mapYDimension; i++) {
            for (int j = 0; j < mapXDimension; j++) {
                if (waitTime != 0) {
                    waitTime--;
                    continue;
                }

                if (treeGroups == 0) {
                    break outerloop;
                }

                int yCoord = i;
                int xCoord = j;
                int randomNum = random.nextInt(100);
                if (randomNum > 70 && randomNum < 72) {
                    treeGroups--;
                    int numberOfTrees = random.nextInt(6) + 1;
                    mapTiles[yCoord][xCoord].setTileType(1);
                    numberOfTrees--;
                    int up = 1;
                    int down = 1;
                    int right = 1;
                    int left = 1;
                    while (numberOfTrees > 0) {
                        int direction = random.nextInt(4);
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
                    waitTime = random.nextInt(41) + 30;
                }
            }
        }
    }

    private void addCobbleStone(int cobbleGroups) {
        Random random = new Random();
        int waitTime = 0;

        outerloop:
        for (int i = 0; i < mapYDimension; i++) {
            for (int j = 0; j < mapXDimension; j++) {
                if (waitTime != 0) {
                    waitTime--;
                    continue;
                }

                if (cobbleGroups == 0) {
                    break outerloop;
                }

                int yCoord = i;
                int xCoord = j;
                int randomNum = random.nextInt(100);
                if (randomNum > 10 && randomNum < 12) {
                    cobbleGroups--;
                    int numberOfCobble = random.nextInt(4) + 2;
                    mapTiles[yCoord][xCoord].setTileType(1);
                    numberOfCobble--;
                    int up = 1;
                    int down = 1;
                    int right = 1;
                    int left = 1;
                    while (numberOfCobble > 0) {
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
    }

    private void addFences(int fenceGroups) {
        Random random = new Random();
        int waitTime = 0;

        outerloop:
        for (int i = 10; i < mapYDimension - 10; i++) {// therefore fences can't go out of bounds since max length is 8
            for (int j = 10; j < mapXDimension - 10; j++) {
                if (waitTime != 0) {
                    waitTime--;
                    continue;
                }

                if (fenceGroups == 0) {
                    break outerloop;
                }

                int yCoord = i;
                int xCoord = j;
                int randomNum = random.nextInt(100);
                if (randomNum > 57 && randomNum < 59) {
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
                    }else{
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
    }

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
                if (randomNum > 44 && randomNum < 46){
                    int houseDimension = random.nextInt(3)+4;
                    int direction = random.nextInt(4);// 0 is up then right, 1 is down then right, 2 is up then left, 3 is down then left

                    boolean houseNearby = false;
                    for (int k = yCoord+houseDimension; k >= yCoord - houseDimension; k--) {
                        for (int l = xCoord - houseDimension; l <= xCoord+houseDimension ; l++) {
                            if (mapTiles[k][l].getTileType() == 4){
                                houseNearby = true;
                            }
                        }
                    }
                    if (houseNearby){
                        break;
                    }
                    houses--;

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
                        for (int k = xCoord; k < xCoord + houseDimension; k++) {
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
    }

    private void addChests(int chests){
        int waitTime = 0;

        outerloop:
        for (int i = 0; i < mapYDimension; i++) {
            for (int j = 0; j < mapXDimension; j++) {
                if (waitTime != 0) {
                    waitTime--;
                    continue;
                }

                if (chests == 0) {
                    break outerloop;
                }

                int yCoord = i;
                int xCoord = j;
                int randomNum = random.nextInt(100);
                if (randomNum > 10 && randomNum < 12) {
                    if (mapTiles[yCoord][xCoord].getTileType() == 4 || mapTiles[yCoord][xCoord].getTileType() == 3){
                        continue;
                    }
                    chests--;
                    mapTiles[yCoord][xCoord].setTileType(5);
                    waitTime = random.nextInt(21) + 20;
                }
            }
        }
    }

    public User nearestPlayer(Zombie zombie){
        double minDistance = mapXDimension + 1;
        User closestPlayer = null;

        for (User user: users){
            double distance = Math.sqrt(Math.pow((zombie.getxCoord()-user.getxCoord()),2) + Math.pow((zombie.getyCoord() - user.getyCoord()),2));
            if (distance < minDistance){
                closestPlayer = user;
                minDistance = distance;
            }
        }
        return closestPlayer;
    }

    public List<User> playersNextTo(Zombie z){
        return playersNextTo(z.getxCoord(), z.getyCoord());
    }

    public List<User> playersNextTo(int x, int y){
        List<User> players = new ArrayList<>();

        for(User user: users){
            if ( (Math.abs(user.getyCoord()-y) <2) && (Math.abs(user.getxCoord()-x) <2)){
                players.add(user);
            }
        }
        return players;
    }

    public List<ZombieMove> moveZombies(){
        List<ZombieMove> moves = new ArrayList<>();
        for (Zombie zombie: zombies){
            moves.add(moveZombie(zombie));
            // loop through users an see if their health is at zero or below(dead)
            for (User user: users){
                if (user.getHealth() <= 0){
                    Server.connectionHandlers.get(user.getListNum()).write(Server.GAME_USER_DEAD );
                }
            }
        }
        return moves;
    }

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
    }

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
    }

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
            double distance = Math.sqrt(Math.pow(possibleMoves.get(i).x + user.getxCoord(), 2) + Math.pow(possibleMoves.get(i).y + user.getyCoord(), 2));// use distance formulate to calculate distance
            if (distance < smallestDistance){// if distance calculated is smaller than smallest distance already calculated
                smallestDistance = distance;// set smallest distance to that distance
                index = i;// get the index for return
            }
        }

        // give best move
        return possibleMoves.get(index);

    }

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
    }
}
