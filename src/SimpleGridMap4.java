import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

//Using Tiles to create a map



class SimpleGridMap4 { 
  
  
  public static void main(String[] args) { 
    GameWindow game= new GameWindow();  
  }
  
}

//This class represents the game window
class GameWindow extends JFrame { 
	public Warrior warriorChar;
	public Player tempPlayer;
	
  //Window constructor
  public GameWindow() { 
    setTitle("Simple Grid/Map Example");
    //setSize(400,400);  // set the size of my window to 400 by 400 pixels
    setResizable(true);  // set my window to allow the user to resize it
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // set the window up to end the program when closed
    
    getContentPane().add(new GamePanel());
    pack(); //makes the frame fit the contents
    setVisible(true);
  }
  
  
  
// An inner class representing the panel on which the game takes place
  class GamePanel extends JPanel implements KeyListener {
	  Timer clock = new Timer();
    Map map;
    boolean keyPressed = false;
    boolean attackAble = false;
    String direction = "right";
    //constructor
    public GamePanel() { 
      setPreferredSize(new Dimension(1080,1080)); 
      warriorChar = new Warrior("bob", 10, 10, 20, 20); //shouldn't be here should be in the main class
      tempPlayer = warriorChar; // shouldn't be here should be in the main class
      map = new Map(1080,1080);
    }
    
    
    public void paintComponent(Graphics g) { 
      super.paintComponent(g); //required to ensure the panel is correctly redrawn
      g.setColor(Color.BLUE);
      map.draw(g);
      
      //draw only if user presses a key
      if(keyPressed = true){
        //call method to draw player based on user input
        //testing purposes: change name
        tempPlayer.draw(g,direction);
      }
      //draw animations for player
      tempPlayer.render(g);
      tempPlayer.tick();
    }
      
      public void keyTyped(KeyEvent e) { 
        //if the user presses space bar, execute basic attack command
        if(e.getKeyChar() == KeyEvent.VK_SPACE){
          
          //only attack if the player is able to, prevents spamming multiple attacks
          if(attackAble == true){
            //execute basic attack
            tempPlayer.basicAttack(direction);
            
            //reset timer
            clock.startTimer();
            
            //reset attackAble
            attackAble = false;      
          }
        }
      }
      public void keyPressed(KeyEvent e) {
        //set keyPressed to be true
        keyPressed = true;
        
        //move left
        if(e.getKeyCode() == KeyEvent.VK_LEFT ){    //Good time to use a Switch statement
          tempPlayer.setSpriteShown(false);
          /*
          if (map.getWorldMap()[tempPlayer.getY()][tempPlayer.getX()-1].getPassability()==true){
            if (map.getWorldMap()[tempPlayer.getY()][tempPlayer.getX()-1] instanceof obstaclesTile){
              tempPlayer.setHealth(tempPlayer.getHealth()-3);
            }
            
            tempPlayer.setX(tempPlayer.getX()-1);
          }
          */
          tempPlayer.setX(tempPlayer.getX()-1);
          direction = "left";
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN ){//move down
          
          tempPlayer.setSpriteShown(false);
          /*
          if (map.getWorldMap()[tempPlayer.getY()+1][tempPlayer.getX()].getPassability()==true){
            if (map.getWorldMap()[tempPlayer.getY()+1][tempPlayer.getX()] instanceof obstaclesTile){
              tempPlayer.setHealth(tempPlayer.getHealth()-3);
            }
          
            tempPlayer.setY(tempPlayer.getY()+1);
          }
          */
          tempPlayer.setY(tempPlayer.getY()+1);
          direction = "down";
        }
        //move right
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT ){
          
          tempPlayer.setSpriteShown(false);
          //dave.setX(dave.getX()+10);
          //bob.setX(bob.getX()+10);
          /*
          if (map.getWorldMap()[tempPlayer.getY()][tempPlayer.getX()+1].getPassability()==true){
            if (map.getWorldMap()[tempPlayer.getY()][tempPlayer.getX()+1] instanceof obstaclesTile){
              tempPlayer.setHealth(tempPlayer.getHealth()-3);
            }
          
            tempPlayer.setX(tempPlayer.getX()+1);
          }
          */
          tempPlayer.setX(tempPlayer.getX()+1);
          direction = "right";
        }
        //move up
        else if(e.getKeyCode() == KeyEvent.VK_UP ){
          tempPlayer.setSpriteShown(false);
          //dave.setY(dave.getY()-10);
          //bob.setY(bob.getY()-10);
          /*
          if (map.getWorldMap()[tempPlayer.getY()-1][tempPlayer.getX()].getPassability()==true){
            if (map.getWorldMap()[tempPlayer.getY()-1][tempPlayer.getX()] instanceof obstaclesTile){
              tempPlayer.setHealth(tempPlayer.getHealth()-3);
            }
      
            tempPlayer.setY(tempPlayer.getY()-1);
          }
          */
          tempPlayer.setY(tempPlayer.getY()-1);
          direction = "up";
        }  //note - would be better to make player class and pass in map, test movement in there
        //xValue.setText("      "+tempPlayer.getX());
       // yValue.setText("      "+tempPlayer.getY());   
      }
      
      
      public void keyReleased(KeyEvent e) {
        //when user releases key, set keyPressed to false
        keyPressed = false;
      }
  
    }
    

  
  class Map { 
    
    //sprites
    BufferedImage cobblestoneSprite;
    BufferedImage brickSprite;
    
    int boxWidth, boxHeight;
    int map[][]= { 
      {0,0,0,0,0,0,0,0,0,0},
      {0,1,1,1,1,1,1,1,1,0},
      {0,1,1,1,1,1,1,1,1,0},
      {0,1,1,1,1,1,1,1,1,0},
      {0,1,1,0,0,1,0,0,1,0},
      {0,1,1,1,0,1,0,1,1,0},
      {0,1,0,1,1,1,1,1,1,0},
      {0,1,1,0,0,0,0,0,1,0},
      {0,1,1,1,1,1,1,1,1,0},
      {0,0,0,0,0,0,0,0,0,0},
      {0,0,0,0,0,0,0,0,0,0}};  //this can be loaded from a file
    Tile worldMap[][];
    
    public Map(int xResolution,int yResolution) { 
      boxWidth=xResolution/9; //The size of each square
      boxHeight=yResolution/9;  //it would be best to choose a res that has a common divisor
      
      //loading in sprites
      try {
        cobblestoneSprite = ImageIO.read(new File("cobblestoneSprite.png"));
        brickSprite = ImageIO.read(new File ("brickSprite.png"));
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        System.out.println("File not found Exception");
      } 
      
      createWorldMap();
    }
    
    //Creates a 2D array of tiles from the int array
    public void createWorldMap() { 
      worldMap = new Tile[map.length][map[1].length];
      for (int j=0;j<worldMap.length;j++)
        for (int i=0;i<worldMap[1].length;i++) {
        if (map[j][i]==0) 
          worldMap[j][i]=new Tile(Color.BLUE,i*boxWidth, j*boxHeight,boxWidth,boxHeight, cobblestoneSprite);
        else
          worldMap[j][i]=new Tile(Color.RED,i*boxWidth, j*boxHeight,boxWidth,boxHeight, brickSprite);
      }
    }
    
    //draws the 2D array of tiles
    public void draw(Graphics g) { 
      for (int j=0;j<map.length;j++)
        for (int i=0;i<map[1].length;i++) {
        worldMap[j][i].draw(g,i,j);
      }
    }
    
  }
  
  //This class represents a single tile on the map
  class Tile { 
    int xPosMap, yPosMap;
    int width,height;
    Color colour;
    BufferedImage tileSprite;
    
    public Tile(Color c, int x, int y, int w, int h, BufferedImage sprite) { 
      this.colour=c;
      this.xPosMap=x;
      this.yPosMap=y;
      this.width=w;
      this.height=h; 
      this.tileSprite = sprite;
    }
    
    public void draw(Graphics g, int xScreen, int yScreen) { 
      g.setColor(colour);
      g.fillRect(xScreen*width, yScreen*height, width-1, height-1);  //-1 to see edges
      if (colour == Color.BLUE) {
        g.drawImage(tileSprite,xScreen*width,yScreen*height,null);
      }else if (colour == Color.RED) {
        g.drawImage(tileSprite,xScreen*width,yScreen*height,null);
      }
    }
    
    
  }
  
}
