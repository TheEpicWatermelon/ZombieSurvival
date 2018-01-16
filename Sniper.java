/**
 * Zombie
 * Sniper.class
 * @authors Izzy
 * @date 1/15/2018
 */ 

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Rectangle;

class Sniper extends Player{
  
  //ArrayList that stores Bullet objects, so user can fire multiple at the same time
  private ArrayList <Bullet> bulletList = new ArrayList<Bullet>();
  
  //temporay Bullet object used for adding Bullet objects inside arrays
  private Bullet tempBullet;
  //integer for the player coordinates on the screen
  private int playerScreenCoordinate = 420;
  //keeps track of which sprite has been displayed
  private int currentSprite = 0;
  //array that stores sniper sprites
  private BufferedImage[] sniperSprite;
  //keeps track of which way the sprite is facing
  private String currentlyFacing = "";
  //used to determine what sniperSprite to show according to the user input
  private int leftSpriteNum = 5, rightSpriteNum = 0;
  
  //boolean value to check if sprite has been shown or not
  private boolean spriteShown = false;
  
  //sniper stats
  int attackStat;
  int defenseStat;
  int evasivnessStat;
  
  //constructor for Sniper class
  Sniper(String name, int health, int exp, int x, int y){
    super(name,health,exp,x,y);
    
    try {
      //get sniper image file
      BufferedImage sniperSheet = ImageIO.read(new File("archerSprite.png"));
      
      //properties for one sniper sprite
      final int width = 256;
      final int height = 256;
      //properties of spritesheet
      final int rows = 2;
      final int cols = 5;
      
      //set size for sniperSprite array
      sniperSprite = new BufferedImage[rows * cols];
      
      //divides sprites up from sprite sheet and load them individually into array
      for (int j = 0; j < rows; j++){
        for (int i = 0; i < cols; i++){          
          sniperSprite[(j*cols)+i] = sniperSheet.getSubimage(i * width,j * height,width,height);
        }
      }
      
    } catch(Exception e) { System.out.println("error loading sheets");};    
  }//end of constructor
  
  
  //getters and setters for the stats
  // gets the attack stat
  public int getAttackStat(){
    return attackStat;
  }
  //sets the attack stat
  public void setAttackStat(int stat){
    attackStat = stat;
  }
  
  //gets the defense stat
  public int getDefenseStat(){
    return defenseStat;
  }
  //sets the defense stat
  public void setDefenseStat(int stat){
    defenseStat = stat;
  }
  
  //gets the evasivness stat
  public int getEvasivnessStat(){
    return evasivnessStat;
  }
  //sets the evasivnessStat
  public void setEvasivnessStat(int stat){
    evasivnessStat = stat;
  }
  
  /**setSpriteShown
    * method to set the value for spriteShown boolean variable
    * @param boolean value
    * @return nothing
    */ 
  public void setSpriteShown(boolean spriteShown){
    this.spriteShown = spriteShown;
  }//end of setSpriteShown
  
  /**getSpriteShown
    * method to get the value for spriteShown boolean variable
    * @param nothing
    * @return boolean value
    */ 
  public boolean getSpriteShown(){
    return this.spriteShown;
  }//end of getSpriteShown
  
  
  /**tick
    * method that gets makes an Bullet object move on screen
    * @param an ArrayList of Tiles (wall)
    * @return nothing
    */ 
  public void tick(){
    for(int i = 0;i < bulletList.size();i++){
      tempBullet = bulletList.get(i);
      
      //remove bullet object if they go off screen, free memory
      if(Math.abs(tempBullet.getY() - getY()) >= 800){
        removeBullet(tempBullet);
      }
      else if(Math.abs(tempBullet.getX() - getX()) >= 800){
        removeBullet(tempBullet);
      }
      
      //subtract x,y coordinates of bullet
      tempBullet.tick();
    }
  }//end of tick
  
  /**render
    * method that gets an bullet object from bulletList ArrayList and draws the object
    * @param Graphics g
    * @return nothing
    */ 
  public void render(Graphics g){
    for(int i = 0;i < bulletList.size();i++){
      tempBullet = bulletList.get(i);
      tempBullet.draw(g);
    }
  }//end of render
  
  /**checkBasicAttackHit
    * method that checks if the bounding boxes of bullet and monster object collide
    * if they collide, remove bullet object and reduce monster health
    * @param ArrayList of Monster objects;
    * @return nothing
    */ 
  public void checkBasicAttackHit(ArrayList<Monster> monsters){
    for(int i = 0;i < monsters.size();i++){
      for(int j = 0;j < bulletList.size();j++){    
        if(bulletList.get(j).getBulletBox().intersects(monsters.get(i).getMonsterBox())){
          bulletList.remove(bulletList.get(j));
          monsters.get(i).setHealth(monsters.get(i).getHealth() - 3);
        }
      }
    }
  }//end of checkBasicAttackHit
  
  /**addBullet
    * method that adds an Bullet object into bulletList arrayList
    * @param Bullet object
    * @return nothing
    */ 
  public void addBullet(Bullet block){
    bulletList.add(block);
  }//end of addBullet
  
  /**removeBullet
    * method that removes an Bullet object into bulletList arrayList
    * @param Bullet object
    * @return nothing
    */ 
  public void removeBullet(Bullet block){
    bulletList.remove(block);
  }//end removeBullet
  
  /**draw
    * method that draws sniper sprites 
    * @param Graphics and direction as a String
    * @return nothing
    */ 
  public void draw(Graphics g, String direction){
    //resets sprite number trackers when they go through one cycle of animation, max = 5 different sprites for animation
    if(rightSpriteNum >=4){
      rightSpriteNum = 0;
    }
    else if(leftSpriteNum >= 9){
      leftSpriteNum = 5;
    }
    
    if(spriteShown == false){
      //draw sniper sprite walking leftwards
      if(direction.equals("left")){
        currentSprite = leftSpriteNum;
        g.drawImage(sniperSprite[currentSprite],playerScreenCoordinate,playerScreenCoordinate,null);
        //subtracts from leftSpriteNum to allow program to use the next warrior sprite thats walking left, for animation
        leftSpriteNum++;
        currentlyFacing = "left";
      }
      //draw sniper sprite walking rightwards
      else if(direction.equals("right")){
        currentSprite = rightSpriteNum;
        g.drawImage(sniperSprite[currentSprite],playerScreenCoordinate,playerScreenCoordinate,null);
        rightSpriteNum++;
        currentlyFacing = "right";
      }
      //draw sniper sprite walking downwards
      else if(direction.equals("down")){
        if(currentlyFacing.equals("left")){
          currentSprite = leftSpriteNum;   
          g.drawImage(sniperSprite[currentSprite],playerScreenCoordinate,playerScreenCoordinate,null);
          leftSpriteNum++;
        }
        else{
          currentSprite = rightSpriteNum;
          g.drawImage(sniperSprite[currentSprite],playerScreenCoordinate,playerScreenCoordinate,null);
          rightSpriteNum++;
        }
      }
      //draw sniper sprite walking upwards
      else if(direction.equals("up")){
        if(currentlyFacing.equals("left")){
          currentSprite = leftSpriteNum;
          g.drawImage(sniperSprite[currentSprite],playerScreenCoordinate,playerScreenCoordinate,null);
          leftSpriteNum++;
        }
        else{
          currentSprite = rightSpriteNum;
          g.drawImage(sniperSprite[currentSprite],playerScreenCoordinate,playerScreenCoordinate,null);
          rightSpriteNum++;
        }
      }
      //make spriteShown true
      spriteShown = true;
    }
    //if player is standing (not moving)
    g.drawImage(sniperSprite[currentSprite],playerScreenCoordinate,playerScreenCoordinate,null);
  }//end of draw
  
  /**basicAttack
    * method that allows Sniper object to fire bullets by creating new Bullet objects
    * @param Graphics and direction as a String
    * @return nothing
    */ 
  public void basicAttack(String direction){
    if(direction.equals("right")){
      addBullet(new Bullet(10,playerScreenCoordinate+120,playerScreenCoordinate+20,direction));
    }
    else if(direction.equals("left")){
      addBullet(new Bullet(10,playerScreenCoordinate-20,playerScreenCoordinate+20,direction));
    }
    else if(direction.equals("up")){
      addBullet(new Bullet(10,playerScreenCoordinate+60,playerScreenCoordinate-20,direction));
    }
    else{
      addBullet(new Bullet(10,playerScreenCoordinate+60,playerScreenCoordinate+100,direction));
    }
  }//end of basicAttack
  
}//end of Sniper