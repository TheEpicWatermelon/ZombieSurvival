/**
 * Zombies
 * obstaclesTile.java
 * @author Izzy
 * @date 1/15/2018
 */

import java.awt.Color;
import java.io.File;
import java.awt.image.BufferedImage;

class obstaclesTile extends Tiles{
  
  //constructing obstacles
  obstaclesTile(Color c, int x, int y, int w, int h, boolean passability,BufferedImage tileSprite){
    super (c,x,y,w,h,passability,tileSprite);
  }//end of constructor
  
}//end of obstaclesTile