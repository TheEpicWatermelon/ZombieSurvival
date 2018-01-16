/**
 * Zombies
 * Monster.java
 * @author Izzy
 * @date 1/15/2018
 */

import java.util.Scanner;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Graphics;

class Monster extends NPC{
  
  //stats
  private int coordinateLocationX;
  private int coordinateloacrionY;
  private int health;
  private int damage;
  private boolean drawMonster = false;
  private Map worldMap;
  //counter for seekPlayer method
  private int seekCount = 0;
  //counter for attackPlayer method
  private int attackCount = 0;
  //counter for move method
  private int moveCount = 0;
  private Rectangle monsterBox;
  
  int currentSprite = 0;
  BufferedImage[] monsterSprites;
  String currentlyFacing = "";
  //used to determine what sprites to show according to the user input
  int leftSpriteNum = 9, rightSpriteNum = 5;
  
  //constructor
  Monster(int health, int damage, int x, int y){// constructor
    super(health,damage,x,y);
    
    try {
      BufferedImage monsterSheet = ImageIO.read(new File("monsterSprite.png"));
      
      monsterBox = new Rectangle(getX(),getY(),120,120);
      
      final int width = 256;
      final int height = 256;
      final int rows = 2;
      final int cols = 5;
      monsterSprites = new BufferedImage[rows * cols];
      
      for (int j = 0; j < rows; j++){
        for (int i = 0; i < cols; i++){
          monsterSprites[(j*cols)+i] = monsterSheet.getSubimage(i * width,j * height,width,height);
        }
      }
      
    } catch(Exception e) { System.out.println("error loading sheet");};    
  }// end of constructor
  
  /**showMonster
    * method that decides whether or not to show monster sprite if the monster is within the players vision
    * @param Player's x and y coordinates
    * @return nothing
    */ 
  public void showMonster(int x, int y){
    for(int i = -4;i <= 4; i++){
      for(int j = -4;j <= 4; j++){
        if((x+i == getX()) && (y+j == getY())){
          drawMonster = true;
        }
        else{
          drawMonster = false; 
        }
      }
    }
  }//end of showMonster
  
  /**attackPlayer
   * method that determines if the the player is range of an attack, and if they are, reduce player's health
   * @param Player object called player
   * @return nothing
   */ 
  public void attackPlayer(Player player){
    
    //reset attack counter
    if(attackCount >= 50){
      attackCount = 0;
    }
    
    //delays monster attack, prevents spam
    if(attackCount % 50 == 0){
      if(Math.abs(getX() - player.getX()) <= 1){
        if(Math.abs(getY() - player.getY()) <= 1){
          player.setHealth(player.getHealth()-1);
        }
      }
    }
    
    //update attackCount
    attackCount++;
  }//end of attackPlayer
  
  /**seekPlayer
   * method that changes the monsters x and y coordinates based on player coordinates to make monster move towards player
   * @param Player object and a Map object
   * @return nothing
   */ 
  public void seekPlayer(Player player, Map map){
    int deltaY = player.getY() - getY();
    int deltaX = player.getX() - getX();
    
    //prevent monster from moving too fast
    if(seekCount >= 60){
      seekCount = 0;
    }
    
    if(seekCount % 60 == 0){
      if(deltaX <=4 && deltaY <= 4){
        if((deltaX >= deltaY && deltaX > 0)&&(map.getWorldMap()[getY()][getX()+1].getPassability()==true)){
          setX(getX()+1);
        }
        else if((deltaX > deltaY && deltaX < 0)&&(map.getWorldMap()[getY()][getX()-1].getPassability()==true)){
          setX(getX()-1);
        }
        else if((deltaY > deltaX && deltaY > 0)&&(map.getWorldMap()[getY()+1][getX()].getPassability()==true)){
          setY(getY()+1);
        }
        else if((deltaY < deltaX && deltaY < 0)&&(map.getWorldMap()[getY()-1][getX()].getPassability()==true)){
          setY(getY()-1);
        }
      }
    }
    seekCount++;
  }//end of seekPlayer
  
  /**getMonsterBox
   * method that returns the Monster object's bounding box
   * @param nothing
   * @return Rectangle 
   */ 
  public Rectangle getMonsterBox(){
    return this.monsterBox;
  }//end of getMonsterBox
  
  /**draw
   * method that draws warrior sprites
   * @param Graphics and direction as a String
   * @param player map coordinates
   * @return nothing
   */ 
  public void draw(Graphics g, String direction, int x, int y){
    
    int screenCX = (120*(getX()+5-x))-180;
    int screenCY = (120*(getY()+5-y))-240;
    
    monsterBox = new Rectangle(screenCX+60,screenCY+80,60,60);
    
    //accurate directions'
    if(getX() < x){
      direction = "right";
    }
    else{
      direction = "left";
    }
    
    //reset move counter
    if(moveCount >= 35){
      moveCount = 0;
    }
    
    //resets sprite number trackers when they go through one cycle of animation, max = 5 different sprites for animation
    if(rightSpriteNum >=9){
      rightSpriteNum = 5;
    }
    else if(leftSpriteNum >= 4){
      leftSpriteNum = 0;
    }
    
    //delays showing the walking animation
    if(moveCount % 35 == 0){
      //draw monster sprite walking leftwards
      if(direction.equals("left")){
        currentSprite = leftSpriteNum;
        g.drawImage(monsterSprites[currentSprite],screenCX,screenCY,null);
        //subtracts from leftSpriteNum to allow program to use the next warrior sprite thats walking left, for animation
        leftSpriteNum++;
        currentlyFacing = "left";
      }
      //draw monster sprite walking rightwards
      else if(direction.equals("right")){
        currentSprite = rightSpriteNum;
        g.drawImage(monsterSprites[currentSprite],screenCX,screenCY,null);
        rightSpriteNum++;
        currentlyFacing = "right";
      }
      //draw monster sprite walking downwards
      else if(direction.equals("down")){
        if(currentlyFacing.equals("left")){
          currentSprite = leftSpriteNum;
          g.drawImage(monsterSprites[currentSprite],screenCX,screenCY,null);
          leftSpriteNum++;
        }
        else{
          currentSprite = rightSpriteNum;
          g.drawImage(monsterSprites[currentSprite],screenCX,screenCY,null);
          rightSpriteNum++;
        }
      }
      //draw monster sprite walking upwards
      else if(direction.equals("up")){
        if(currentlyFacing.equals("left")){
          currentSprite = leftSpriteNum;
          g.drawImage(monsterSprites[currentSprite],screenCX,screenCY,null);
          leftSpriteNum++;
        }
        else{
          currentSprite = rightSpriteNum;
          g.drawImage(monsterSprites[currentSprite],screenCX,screenCY,null);
          rightSpriteNum++;
        }
      }
    }
    //if monster is standing (not moving)
    g.drawImage(monsterSprites[currentSprite],screenCX,screenCY,null);
    
    //update move counter
    moveCount++;
  }//end of draw
  
}// end of monster class