/** 
 * Zombie
 * Player.class
 * @authors Izzy
 * @date 1/15/2018
 */ 
import java.awt.Graphics;
import java.util.ArrayList;

abstract class Player{
  //player info
  private String name;
  
  //stats
  private int health;
  private int exp;
  private int x;
  private int y;
  private int screenX;
  private int screenY;

  //basic constructor
  Player(String name, int health, int exp, int x, int y){
    this.name = name;
    this.health = health;
    this.exp = exp;
    this.x = x;
    this.y = y;
  }//end of player constructor
  
  
  /**getName
   * getter method for name variable
   */
  public String getName(){
    return this.name;
  }//end of getName
  
  /**setName
   * setter method for name variable
   */ 
  public void setName(String name){
    this.name = name;
  }//end of setName
  
  /**getHealth
   * getter method for health variable
   */ 
  public int getHealth(){
    return this.health;
  }//end of getHealth
  
  /**setHealth
   * setter method for health variable
   */ 
  public void setHealth(int health){
    this.health = health;
  }//end of setHealth
  
  /**getExp
   * getter method for exp variable
   */ 
  public int getExp(){
    return this.exp;
  }//end of getExp
  
  /**setExp
   * setter method for exp variable
   */ 
  public void setExp(int exp){
    this.exp = exp;
  }//end of setExp
  
  /**getX
   * getter method for x position variable
   */ 
  public int getX(){
    return this.x;
  }//end of getX
  
  /**setX
   * setter method for x position variable
   */ 
  public void setX(int x){
    this.x = x;
  }//end of setX
  
  /**getY
   * getter method for y position variable
   */ 
  public int getY(){
    return this.y;
  }//end of getY
  
  /**setY
   * setter method for y position variable
   */ 
  public void setY(int y){
    this.y = y;
  }//end of setY
  
  /**getScreenX
   * getter method for screen X position variable
   */ 
   public int getScreenX(){
    return this.screenX;
  }//end getScreenX
  
  /**setScreenX
   * setter method for screen x position variable
   */ 
  public void setScreenX(int x){
    this.screenX = x;
  }//end of setScreenX
  
  
  //abstract method for drawing sprites
  public abstract void draw(Graphics g, String direction);
  
  //abstract method for the basic attack (spacebar) of characters
  public abstract void basicAttack(String direction);
  
  //abstract method for controlling archer and mage class' projectiles
  public abstract void tick();
  
  //abstract method for that updates player's coordinates
  public abstract void render(Graphics g);
  
  //abstract method that sets the value for spriteShown
  public abstract void setSpriteShown(boolean spriteShown);

  //abstract method that checks if the sprite has been shown
  public abstract boolean getSpriteShown();
  
  //abstract method that checks if the player has attacked
  public abstract void checkBasicAttackHit(ArrayList <Monster> monsters);
  
}//end of player