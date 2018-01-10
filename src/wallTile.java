/* G11 CompSci Final project
 * Dungeon Crawler Game
 * wallTile.class
 * @authors Andrew, Jim, Izzy, John
 * @date 12/15/2016
 */ 

import java.io.File;
import java.awt.Color;
import java.awt.image.BufferedImage;

class wallTile extends Tiles{
  
  BufferedImage wallSprite;  
  
  //constructing the wall tile
  wallTile(Color c, int x, int y, int w, int h, boolean passability, BufferedImage tileSprite){
    super (c,x,y,w,h,passability,tileSprite);   
  }//end of constructor
  
}