/**
 * Zombies
 * NPC.java
 * @author Izzy
 * @date 1/15/2018
 */

import java.awt.Graphics;

abstract class NPC{
  
  private int health;
  private int damage;
  private int x;
  private int y;
  private Map worldMap;
  
  NPC(int health, int damage, int x, int y){
    this.health = health;
    this.damage = damage;
    this.x = x;
    this.y = y;
    this.worldMap = worldMap;
  }//end of player constructor
  
  
  /* getHealth
   * getter method for health variable
   */ 
  public int getHealth(){
    return this.health;
  }
  
  /* setHealth
   * setter method for health variable
   */ 
  public void setHealth(int health){
    this.health = health;
  }
  
  /* getDamage
   * getter method for damage variable
   */ 
  public int getDamage(){
    return this.damage;
  }
  
  /* setDamage
   * setter method for damage variable
   */ 
  public void setDamage(int Damage){
    this.damage = damage;
  }
  
   /* getX
   * getter method for x position variable
   */ 
  public int getX(){
    return this.x;
  }
  
  /* setX
   * setter method for x position variable
   */ 
  public void setX(int x){
    this.x = x;
  }
  
   /* getY
   * getter method for y position variable
   */ 
  public int getY(){
    return this.y;
  }
  
  /* setY
   * setter method for y position variable
   */ 
  public void setY(int y){
    this.y = y;
  }
  
   //abstract method for drawing sprites
  public abstract void draw(Graphics g, String direction, int x, int y);
  
 
}