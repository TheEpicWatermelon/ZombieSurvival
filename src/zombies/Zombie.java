package zombies;

import game.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sasha on 12/29/2017.
 */
public abstract class Zombie {

    private final Random random = new Random();

    protected int xCoord;
    protected int yCoord;
    protected int attackValue;
    protected int defenseValue;
    protected int evasiveness;
    protected int speed;
    protected int health;

    public Zombie(int yCoord, int xCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public void attackPlayer(User user){// TODO IMPLEMENT WEAPONS
        int randNum = random.nextInt(61) + 40;// get a random number for 0-100, this will be used to see if the player avoids the attack or not
        if (randNum < user.getEvasiveness()){// if user evades the attack then do nothing
            return;
        }// if zombie successfully attacks the user calculate the damage
        int potentialAttackDamage = random.nextInt(attackValue - Math.min(21, attackValue)) + 20;// always a minimum of 20 attack damage
        int potentialDefense = random.nextInt(user.getMeleeDefense()- Math.min(21,user.getMeleeDefense())) + 20;// always a minimum of 20 defense
        int attackValue = potentialAttackDamage - potentialDefense;
        if (attackValue <= 0){// if attack value is less than or equal to zero, don't do anything
            return;
        }else{// if the damage breakthrough is successful, subtract that from players health
            user.setHealth(user.getHealth() - attackValue);
        }
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public int getDefenseValue() {
        return defenseValue;
    }

    public void setDefenseValue(int defenseValue) {
        this.defenseValue = defenseValue;
    }

    public int getEvasiveness() {
        return evasiveness;
    }

    public void setEvasiveness(int evasiveness) {
        this.evasiveness = evasiveness;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
