/**
 * Zombie
 * Bullet.java
 * @authors Izzy
 * @date 1/15/2018
 */ 

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

class Bullet extends Projectiles{
  
  //array to store bullet sprites
  private BufferedImage[] bulletSprite;
  private Rectangle bulletBox;

  //constructor for Bullet class
  Bullet(int damage, int x, int y, String direction){
    super(damage,x,y,direction);
    
    try{
      //get bullet image file
      BufferedImage bulletSheet = ImageIO.read(new File("arrowSprite.png"));
      
      //collision box for bullet
      bulletBox = new Rectangle(getX(),getY(),120,120);
      
      //the properties of one bullet
      final int width = 128;
      final int height = 128;
      //colomns and rows for sprite sheet
      final int rows = 2;
      final int cols = 2;
      
      //set size for bulletSprite array
      bulletSprite = new BufferedImage[rows * cols];
      
      //store each sprite into bulletSprite array
      for (int j = 0; j < rows; j++){
        for (int i = 0; i < cols; i++){          
          bulletSprite[(j*cols)+i] = bulletSheet.getSubimage(i * width,j * height,width,height);
        }
      }
    }
    catch(Exception e) { System.out.println("error loading sheets");};    
  }
  
  /**tick
   * method that changes an Bullet object's coordinates
   * @param nothing
   * @return nothing
   */ 
  public void tick(){
    if(getDir().equals("right")){
    setX(getX()+60);
    }
    else if(getDir().equals("left")){
      setX(getX()-60);
    }
    else if(getDir().equals("up")){
      setY(getY()-60);
    }
    else{
      setY(getY()+60);
    }
  }
  
  /**getBulletBox
   * method that returns the bullet bounding box
   * @param nothing
   * @return Rectangle
   */
  public Rectangle getBulletBox(){
    return this.bulletBox;
  }
  
  /**draw
   * method that draws bullet sprites 
   * @param Graphics g
   * @return nothing
   */ 
  public void draw(Graphics g){
    bulletBox = new Rectangle(getX(),getY(),120,120);
      
      if(getDir().equals("up")){
        g.drawImage(bulletSprite[0],getX(),getY(),null);
      }
      else if(getDir().equals("left")){
        g.drawImage(bulletSprite[1],getX(),getY(),null);
      }
      else if(getDir().equals("down")){
        g.drawImage(bulletSprite[2],getX(),getY(),null);
      }
      else if(getDir().equals("right")){
        g.drawImage(bulletSprite[3],getX(),getY(),null);
    }
  }
}