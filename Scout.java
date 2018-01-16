/**
 * Zombie survival
 * Scout.class
 * @authors Izzy
 * @date 1/15/2018
 */ 

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.util.ArrayList;

class Scout extends Player{
  
  //integer for the player coordinates on the screen
  private int playerScreenCoordinate = 420;
  //keeps track of which sprite has been displayed
  private int currentSprite = 0;
  //array that stores scout sprites
  private BufferedImage[] scoutSprite;
  //array that stores scout attack sprites
  private BufferedImage[] scoutAttackSprite;
  //keeps track of which way the sprite is facing
  private String currentlyFacing = "";
  //keeps track of what key user presses, used to determine which attack sprite to show
  private String direction = "";
  //check if user pressed spacebar or not
  private boolean attacked = false;
  private int check = 0; // timer value to make sure sprite styays and shows
  
  
  //used to determine what sprites to show according to the user input
  int leftSpriteNum = 5, rightSpriteNum = 0;
  
  //boolean value to check if sprite has been shown or not
  private boolean spriteShown = false;
  
  //scout stats
  int attackStat;
  int defenseStat;
  int evasivnessStat;
  
  //basic constructor
  Scout(String name,int health, int exp, int x, int y){
    super(name,health,exp,x,y);
    
    try {
      //load files
      BufferedImage scoutSheet = ImageIO.read(new File("warriorSprite.png"));
      BufferedImage scoutAttackSheet = ImageIO.read(new File("warriorSprite_Attack.png"));
      
      //one scout sprite properties
      final int width = 256;
      final int height = 256;
      //scout sprite sheet properties
      final int rows1 = 2;
      final int cols1 = 5;
      //scout attack sprite sheet properties
      final int rows2 = 3;
      final int cols2 = 2;
      
      //set array size to the amount of total sprites found inside sprite sheet
      scoutSprite = new BufferedImage[rows1 * cols1];
      scoutAttackSprite = new BufferedImage[rows2 * cols2];
      
      //loads scout sprites into an array
      for (int j = 0; j < rows1; j++){
        for (int i = 0; i < cols1; i++){
          scoutSprite[(j*cols1)+i] = scoutSheet.getSubimage(i * width,j * height,width,height);
        }
      }
      
      //loads scout attack sprites into an array
      for (int m = 0; m < rows2; m++){
        for (int n = 0; n < cols2; n++){
          scoutAttackSprite[(m*cols2)+n] = scoutAttackSheet.getSubimage(n * width,m * height,width,height);
        }
      }
      
    } catch(Exception e) { System.out.println("error loading sheet");};    
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
  
  //need this method here because Player class has this abstract method
  public void tick(){
    
  }//end of tick
  
  
  /**render
    * method that draws scout attack animations based on direction
    * @param Graphics g
    * @return nothing
    */ 
  public void render(Graphics g){
    //reset check value
    if(check > 30){
      check = 0;
    }//end of render
    
    //draw attack animations only if user issued an attack
    if(attacked == true){  
      //right attack animation
      if(currentlyFacing.equals("right")){
        g.drawImage(scoutAttackSprite[0],playerScreenCoordinate,playerScreenCoordinate,null);
        //prevents program from drawing sprites too quickly
        if(check >=30){
          g.drawImage(scoutAttackSprite[1],playerScreenCoordinate,playerScreenCoordinate,null);
          //reset attacked to false
          attacked = false;
        }
      }
      //left attack animation
      else if(currentlyFacing.equals("left")){
        g.drawImage(scoutAttackSprite[2],playerScreenCoordinate,playerScreenCoordinate,null);
        //prevents program from drawing sprites too quickly
        if(check >= 30){
          g.drawImage(scoutAttackSprite[3],playerScreenCoordinate,playerScreenCoordinate,null);
          //reset attacked to false
          attacked = false;
        }
      }
      
      //update check
      check++;
    }
    //if user did not attack, draw "rest position" sprites
    if(check == 0){
      if(attacked == false){
        if(currentlyFacing.equals("right")){
          g.drawImage(scoutAttackSprite[4],playerScreenCoordinate,playerScreenCoordinate,null);
        }
        else if(currentlyFacing.equals("left")){
          g.drawImage(scoutAttackSprite[5],playerScreenCoordinate,playerScreenCoordinate,null); 
        }
      }
    }
  }
  
  /**checkBasicAttackHit
    * method that checks if a monster object is within scout object
    * .. attack range and reduces the monster's health
    * @param ArrayList of Monster objects;
    * @return nothing
    */ 
  public void checkBasicAttackHit(ArrayList <Monster> monsters){
    if(attacked == true){
      for(int i= 0;i < monsters.size();i++){
        if(Math.abs(getX() - monsters.get(i).getX()) <= 1){
          if(Math.abs(getY() - monsters.get(i).getY()) <= 1){
            monsters.get(i).setHealth(monsters.get(i).getHealth()-1);
          }
        }
      }
    }
  }//end of checkBasicAttackHit
  
  /**draw
    * method that draws scout sprites
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
    }//end of draw
    
    if(spriteShown == false){
      //draw scout sprite walking leftwards
      if(direction.equals("left")){
        currentSprite = leftSpriteNum;
        g.drawImage(scoutSprite[currentSprite],playerScreenCoordinate,playerScreenCoordinate,null);
        //subtracts from leftSpriteNum to allow program to use the next scout sprite thats walking left, for animation
        leftSpriteNum++;
        currentlyFacing = "left";
      }
      //draw scout sprite walking rightwards
      else if(direction.equals("right")){
        currentSprite = rightSpriteNum;
        g.drawImage(scoutSprite[currentSprite],playerScreenCoordinate,playerScreenCoordinate,null);
        rightSpriteNum++;
        currentlyFacing = "right";
      }
      //draw scout sprite walking downwards
      else if(direction.equals("down")){
        if(currentlyFacing.equals("left")){
          currentSprite = leftSpriteNum;   
          g.drawImage(scoutSprite[currentSprite],playerScreenCoordinate,playerScreenCoordinate,null);
          leftSpriteNum++;
        }
        else{
          currentSprite = rightSpriteNum;
          g.drawImage(scoutSprite[currentSprite],playerScreenCoordinate,playerScreenCoordinate,null);
          rightSpriteNum++;
        }
      }
      //draw scout sprite walking upwards
      else if(direction.equals("up")){
        if(currentlyFacing.equals("left")){
          currentSprite = leftSpriteNum;
          g.drawImage(scoutSprite[currentSprite],playerScreenCoordinate,playerScreenCoordinate,null);
          leftSpriteNum++;
        }
        else{
          currentSprite = rightSpriteNum;
          g.drawImage(scoutSprite[currentSprite],playerScreenCoordinate,playerScreenCoordinate,null);
          rightSpriteNum++;
        }
      }
      //make spriteShown true
      spriteShown = true;
    }
    //if player is standing (not moving)
    g.drawImage(scoutSprite[currentSprite],playerScreenCoordinate,playerScreenCoordinate,null);
  }//end of draw
  
  /**basicAttack
    * method that changes currentlyFacing
    * @param direction as a String
    * @return nothing
    */ 
  public void basicAttack(String direction){
    if(direction.equals("left")){
      this.direction = "left";
    }
    else if(direction.equals("right")){
      this.direction = "right";
    }
    else if(direction.equals("up")){
      this.direction = "up";
    }
    else{
      this.direction = "down";
    }
    
    //set attacked to be true
    attacked = true;
    
  }//end basicAttack  
}
