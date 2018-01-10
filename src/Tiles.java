/* G11 CompSci Final project
 * Dungeon Crawler Game
 * Tiles.class
 * @authors Andrew, Jim, Izzy, John
 * @date 12/15/2016
 */ 

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.awt.image.BufferedImage;

abstract class Tiles{
  private int tileHeight, tileWidth;
  private boolean passability= true;
  private int width,height;
  private Color colour;
  BufferedImage tileSprite;
  
  BufferedImage wallSprite;
  
  //contstructor setting size and ability to walk on the tile
  Tiles(Color c, int x, int y, int w, int h, boolean passability,BufferedImage tileSprite){
      this.colour = c;
      this.tileHeight = y;
      this.tileWidth = x;
      this.width=w;
      this.height=h;
      this.passability = passability;
      this.tileSprite = tileSprite;  
  }//end of constructor
  
  /**getHeight
    * method for getting tileHeight
    * @param nothing
    * @return int
    */ 
  public int getHeight(){
   return this.tileHeight;
  }//end of getHeight
  
  /**setHeight
    * method for setting tileHeight
    * @param integer
    * @return nothing
    */ 
  public void setHeight(int h){
    this.tileHeight = h;
  }//end of setHeight
  
  /**getWidth
    * method for getting tileWidth
    * @param nothing
    * @return int
    */ 
  public int getWidth(){
    return this.tileWidth;
  }//end of getWidth
  
  /**setWidth
    * method for setting tileWidth
    * @param int
    * @return nothing
    */ 
  public void setWidth(int w){
    this.tileWidth = w;
  }//end of setWidth
  
  /**getPassability
    * method for getting passability
    * @param nothing
    * @return boolean
    */ 
  public boolean getPassability(){
    return this.passability;
  }//end of getPassability
  
  /**getWidth
    * method that sets passability value
    * @param boolean
    * @return nothing
    */ 
  public void setPassability(boolean p){
    this.passability = p;
  }//end of setPassability
  
  /**draw
    * method that draws tiles depending on the color passed through
    * @param Graphics, 2 integer values representing screen coordinate x and y
    * @return nothing
    */ 
  public void draw(Graphics g, int xScreen, int yScreen) {
    g.setColor(colour);
    g.fillRect(xScreen*width, yScreen*height, width, height);  //-1 to see edges
    //draw tiles corresponding to the color
    if(colour == Color.BLUE || colour == Color.GREEN){
      g.drawImage(tileSprite,xScreen*width,yScreen*height,null);
    }
    else if(colour == Color.RED){
      g.drawImage(tileSprite,xScreen*width,yScreen*height,null);
    }
    else if(colour == Color.BLACK){
      g.drawImage(tileSprite,xScreen*width,yScreen*height,null);
    }
  }//end of draw
  
  public Color getColor(){
    return colour;
  }//end of getColor
  
}//end of Tiles
                            