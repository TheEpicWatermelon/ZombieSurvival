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

    // constructor
    Game(List<User> users){
        map = new GameMap();
        wave = 1;
        this.users = users;
        for(User user: this.users){
            map.addUser(user);
        }
        map.placeUsers();
        map.placeZombies(wave);
        zombies = map.getZombies();
    }
}
