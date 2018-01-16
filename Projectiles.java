/**
 * Zombies
 * projectiles.java
 * @author Izzy
 * @date 1/15/2018
 */
import java.awt.Graphics;

abstract class Projectiles{
  
  private int damage;
  private int x;
  private int y;
  private String direction;
  
  //constructor
  Projectiles(int damage, int x, int y, String direction){
    this.damage = damage;
    this.x = x;
    this.y = y;
    this.direction = direction;
  }//end of constructor
  
  /**setX
    * method that sets the x position
    * @param integer
    * @return nothing
    */ 
  public void setX(int x){
    this.x = x;
  }//end of setX
  
  
  /**getX
    * method that gets the x position
    * @param nothing
    * @return integer
    */ 
  public int getX(){
    return this.x;
  }//end of getX
  
  
  /**setY
    * method that sets the y position
    * @param integer
    * @return nothing
    */ 
  public void setY(int y){
    this.y = y;
  }//end of setY
    
  /**getY
    * method that gets the y position
    * @param nothing
    * @return integer
    */ 
  public int getY(){
    return this.y;
  }//end of getY
  
  /**setDir
    * method that sets direction
    * @param String
    * @return nothing
    */ 
  public void setDir(String direction){
    this.direction = direction;
  }//end of setDir
  
  /**getDir
    * method that gets direction
    * @param nothing
    * @return String
    */ 
  public String getDir(){
    return this.direction;
  }//end of getDir
  
  //abstract class for drawing projectile sprites
  public abstract void draw (Graphics g);
  
}//end of Projectiles