package game;
// imports
import items.Weapon;
import items.Knife;
import items.Rifle;
import items.Sword;
import items.Pistol;
import items.Axe;
import items.Hammer;
import zombies.Zombie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * [game.User.java]
 * holds all the data for a single user
 * @author Sasha Maximovitch
 * @date November 9th, 2017
 */

public class User {

    // global variables
    private String name;// user's name
    private int listNum;// user's list number
    private int xCoord;
    private int yCoord;
    private List<Weapon> inventory = new ArrayList<>();
    private int currentItem = 0;
    private String playerClass;
    private int kills;
    private int maxHealth;
    private int upgradePointStatus;// when this reaches 10 an upgrade point is added
    private int upgradePoints;// number of upgrade points
    private Random random = new Random();

    // STATS
    private int health;
    private int meleeAttack;
    private int meleeDefense;
    private int rangedAttack;
    private int evasiveness;
    private int speed;
    private int crafting;
    private int builder;

    // skills
    private int healthIncrease = 0;// index 0 when upgrading
    private int attack = 0;// index 1 when upgrading
    private int defense = 0;// index 2 when upgrading
    private int quickness = 0;// index 3 when upgrading
    private int building = 0;// index 4 when upgrading

    // constructor
    public User(){
    }

    // constructor - Used for tests
    public User(String name, int yCoord, int xCoord){
        this.name = name;
        this.yCoord = yCoord;
        this.xCoord = xCoord;
    }

    /**
     * attackZombie
     * method that calculates the user's attack of a zombie
     * @param zombie that is being attacked
     * @param ranged if the attack is ranged
     */

    public void attackZombie(Zombie zombie, boolean ranged){
        int randNum;
        if (ranged){// if this is a range attack, check hit chance by range skill
            randNum = random.nextInt(rangedAttack) + 50;// get a random number for 0-100, this will be used to see if the player avoids the attack or not
        }else{// if this is a melee attack, check hit chance by melee skill
            randNum = random.nextInt(meleeAttack) + 50;// get a random number for 0-100, this will be used to see if the player avoids the attack or not
        }
        if (randNum < zombie.getEvasiveness()){// if zombie evades the attack then do nothing
            return;
        }
        if (ranged){// if user uses ranged weapon
            int potentialAttackDamage = random.nextInt( inventory.get(0).attack - Math.min(20, rangedAttack)+1) + 20;// always a minimum of 20 attack damage
            int potentialDefense = random.nextInt(zombie.getDefenseValue()- Math.min(21,zombie.getDefenseValue()) + 20);// always a minimum of 20 defense
            int attackValue = potentialAttackDamage - potentialDefense;
            if (attackValue <= 0){// if attack value is less than or equal to zero, don't do anything
                return;
            }else{// if the damage breakthrough is successful, subtract that from players health
                zombie.setHealth(zombie.getHealth() - attackValue);
            }
        }else{// if user uses melee attack
            int potentialAttackDamage = random.nextInt(inventory.get(0).attack - Math.min(20, meleeAttack) + 1) + 20;// always a minimum of 20 attack damage
            int potentialDefense = random.nextInt(zombie.getDefenseValue()- Math.min(21,zombie.getDefenseValue()) + 20);// always a minimum of 20 defense
            int attackValue = potentialAttackDamage - potentialDefense;
            if (attackValue <= 0){// if attack value is less than or equal to zero, don't do anything
                return;
            }else{// if the damage breakthrough is successful, subtract that from players health
                zombie.setHealth(zombie.getHealth() - attackValue);
            }
        }
    }// end attackZombie

    /**
     * heal
     * add health to the current health of the user
     * @param healthGain - amount of health to be gained
     */
    public void heal(int healthGain){
        this.health += healthGain;
    }

    /**
     * upgrade
     * when a user wants to upgrade a skill this method is called, it selects the appropriate skill to be upgraded and upgrades certain stats as a result
     * @param index
     */
    public void upgrade(int index){
        switch(index) {
            case 0:// upgrade health skill, this increases the maximum health of the character by 5
                healthIncrease++;
                maxHealth += 5;
                break;
            case 1:// upgrade the attack skill, this increases the melee attack and ranged attack stats by 5
                attack++;
                meleeAttack+= 5;
                rangedAttack += 5;
                break;
            case 2:// upgrade the defense skill, this increases the melee defense stat by 5
                defense++;
                meleeDefense+= 5;
                break;
            case 3:// upgrade the quickness skill, this increases evasiveness stat by 5 and character can has one more move per turn
                quickness++;
                evasiveness+= 5;
                speed++;
                break;
            case 4:// upgrade the building skill, this increases crafting and builder stats by 5
                building++;
                crafting+= 5;
                builder+= 5;
                break;
            default:
                break;
        }
    }// end upgrade


    // GETTERS AND SETTERS FOR STATS
    public int getMeleeAttack() {
        return meleeAttack;
    }

    public void setMeleeAttack(int meleeAttack) {
        this.meleeAttack = meleeAttack;
    }

    public int getMeleeDefense() {
        return meleeDefense;
    }

    public void setMeleeDefense(int meleeDefense) {
        this.meleeDefense = meleeDefense;
    }

    public int getRangedAttack() {
        return rangedAttack;
    }

    public void setRangedAttack(int rangedAttack) {
        this.rangedAttack = rangedAttack;
    }

    public int getEvasiveness() {
        return evasiveness;
    }

    public void setEvasiveness(int evasiveness) {
        this.evasiveness = evasiveness;
    }

    public int getCrafting() {
        return crafting;
    }

    public void setCrafting(int crafting) {
        this.crafting = crafting;
    }

    public int getBuilder() {
        return builder;
    }

    public void setBuilder(int builder) {
        this.builder = builder;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    // GETTERS AND SETTERS FOR USER SKILLS
    public int getHealthIncrease() {
        return healthIncrease;
    }

    public void setHealthIncrease(int healthIncrease) {
        this.healthIncrease = healthIncrease;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getQuickness() {
        return quickness;
    }

    public void setQuickness(int quickness) {
        this.quickness = quickness;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }


    // GETTERS AND SETTERS FOR USER
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getListNum() {
        return listNum;
    }

    public void setListNum(int listNum) {
        this.listNum = listNum;
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

    public List<Weapon> getInventory() {
        return inventory;
    }

    public Weapon getInventory(int index) {
        return inventory.get(index);
    }

    public void setInventory(List<Weapon> inventory) {
        this.inventory = inventory;
    }

    public int getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getUpgradePointStatus() {
        return upgradePointStatus;
    }

    public void setUpgradePointStatus(int upgradePointStatus) {
        this.upgradePointStatus = upgradePointStatus;
    }

    public int getUpgradePoints() {
        return upgradePoints;
    }

    public void setUpgradePoints(int upgradePoints) {
        this.upgradePoints = upgradePoints;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * setClass
     * user selects a class to be switched to, the appropriate classes default skills will be set, and a weapon will be assigned as well
     * @param classIndex - the index of the class - 0 is scout, 1 is sniper, 2 is thief, 3 is mechanic, 4 is tank
     */
    public void setClass(int classIndex){
        if (classIndex == 0){// set scout
            if (inventory.size() == 1){// remove previous item
                inventory.remove(0);
            }
            playerClass = "Scout";
            health = 60;
            meleeAttack = 70;
            meleeDefense = 40;
            rangedAttack = 20;
            evasiveness = 65;
            speed = 6;
            crafting = 30;
            builder = 30;
            inventory.add(new Knife());
        }else if(classIndex == 1){// set sniper
            if (inventory.size() == 1){// remove previous item
                inventory.remove(0);
            }
            playerClass = "Sniper";
            health = 50;
            meleeAttack = 40;
            meleeDefense = 40;
            rangedAttack = 70;
            evasiveness = 50;
            speed = 4;
            crafting = 50;
            builder = 50;
            inventory.add(new Rifle());
        }else if (classIndex == 2){// set thief
            if (inventory.size() == 1){// remove previous item
                inventory.remove(0);
            }
            playerClass = "Thief";
            health = 40;
            meleeAttack = 60;
            meleeDefense = 60;
            rangedAttack = 30;
            evasiveness = 60;
            speed = 4;
            crafting = 30;
            builder = 30;
            inventory.add(new Pistol());
        }else if(classIndex == 3){ // set mechanic
            if (inventory.size() == 1){// remove previous item
                inventory.remove(0);
            }
            playerClass = "Mechanic";
            health = 50;
            meleeAttack = 40;
            meleeDefense = 40;
            rangedAttack = 40;
            evasiveness = 50;
            speed = 4;
            crafting = 70;
            builder = 70;
            inventory.add(new Hammer());
        }else if(classIndex == 4){ // set tank
            if (inventory.size() == 1){// remove previous item
                inventory.remove(0);
            }
            playerClass = "Tank";
            health = 100;
            meleeAttack = 70;
            meleeDefense = 70;
            rangedAttack = 20;
            evasiveness = 30;
            speed = 4;
            crafting = 20;
            builder = 20;
            inventory.add(new Sword());
        }
    }// end setClass

}// end UserClass