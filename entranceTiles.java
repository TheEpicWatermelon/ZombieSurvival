/**
 * Zombiee
 * entranceTiles.java
 * @authors Izzy
 * @date 1/15/2018
 */ 
import java.awt.Color;
import java.awt.image.BufferedImage;

class entranceTiles extends Tiles{
  
  //constructing the entrance tiles
  entranceTiles(Color c, int x, int y, int w, int h, boolean passability,BufferedImage tileSprite){
    super (c,x,y,w,h,passability,tileSprite);
  }//end of constructor
  
}//end of entranceTiles
  