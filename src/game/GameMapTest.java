package game;

import org.junit.*;
import zombies.RegularZombie;
import zombies.Zombie;

import java.util.List;

import static org.junit.Assert.*;

/**
 * [GameMapTest.java]
 * a collection of methods that helped me test the game map
 * @author Sasha Maximovitch
 * @date January 5th, 2018
 */
public class GameMapTest {
    @Test
    public void findNearestPlayer1() throws Exception {
        GameMap map = new GameMap();
        map.addUser(new User("User 1",4,4));
        map.addUser(new User("User 2", 8 , 10));

        User nearestPlayer = map.nearestPlayer(new RegularZombie(3,4));
        Assert.assertEquals("User 1", nearestPlayer.getName());
    }// end findNearestPlayer1

    @Test
    public void findNearestPlayer2() throws Exception {
        GameMap map = new GameMap();


        User nearestPlayer = map.nearestPlayer(new RegularZombie(3,4));
        Assert.assertNull(nearestPlayer);
    }// end findNearestPlayer2

    @Test
    public void findNearestPlayer3() throws Exception {
        GameMap map = new GameMap();

        map.addUser(new User("User 1",4,4));
        map.addUser(new User("User 2", 8 , 10));

        List<User> users = map.playersNextTo(new RegularZombie(3,4));

        Assert.assertEquals(1,users.size());
        Assert.assertEquals("User 1", users.get(0).getName());
    }// end findNearestPlayer3

    @Test
    public void findNearestPlayer4() throws Exception {
        GameMap map = new GameMap();

        map.addUser(new User("User 1",4,4));
        map.addUser(new User("User 2", 2 , 4));

        List<User> users = map.playersNextTo(new RegularZombie(3,4));

        Assert.assertEquals(2,users.size());
    }// end findNearestPlayer4

    @Test
    public void findNearestPlayer5() throws Exception {
        GameMap map = new GameMap();

        map.addUser(new User("User 1",6,4));
        map.addUser(new User("User 2", 8 , 10));

        List<User> users = map.playersNextTo(new RegularZombie(3,4));

        Assert.assertEquals(0,users.size());
    }// end findNearestPlayer5

    @Test
    public void zombieMove() throws Exception{
        GameMap map = new GameMap();


        Zombie zombie = new RegularZombie(2,2);

        ZombieMove move = map.moveZombie(zombie);

        for (int i = 0; i < move.move.size() ; i++) {
            System.out.println(move.move.get(i).x + " - " + move.move.get(i).y);

        }
    }// end zombieMove

    @Test
    public void printMap() throws Exception{
        GameMap map = new GameMap();
        map.displayMap();
        System.out.println(map.mapToString());
    }// end printMap

}