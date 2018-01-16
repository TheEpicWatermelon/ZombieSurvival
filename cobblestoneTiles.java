/**
 * Zombie
 * cobblestoneTiles.class
 * @authors Andrew, Jim, Izzy, John
 * @date 12/15/2016
 */ 
import java.awt.image.BufferedImage;
import java.awt.Color;

class cobblestoneTiles extends Tiles{
  
  
  //constructing the cobblestoneTiles
  cobblestoneTiles(Color c, int x, int y, int w, int h, boolean passability, BufferedImage tileSprite){
    super (c,x,y,w,h,passability,tileSprite);  
  }//end of constructor
  
}//end of cobblestoneTiles