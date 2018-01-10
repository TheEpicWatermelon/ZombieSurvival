	/* G11 CompSci Final project
	 * Dungeon Crawler Game
	 * Tiles.class
	 * @authors Andrew, Jim, Izzy, John
	 * @date 12/15/2016
	 */ 

	import java.util.Scanner;
	import java.awt.image.BufferedImage;
	import java.awt.Color;
	import java.awt.Graphics;
	import javax.imageio.ImageIO;
	import java.io.File;
	import java.util.ArrayList;

	class Map {
	  //2D array to store the numbers inside the map text files
	  int map[][] =null;
	  int boxWidth, boxHeight, visibleX,visibleY;
	  int mapX, mapY;
	  
	  BufferedImage floorSprite;
	  BufferedImage wallSprite;
	  BufferedImage obstacleSprite;
	  //dont have some tiles images
	  BufferedImage tempSprite;
	  
	  //this can be loaded from a file
	  Tiles worldMap[][];
	  
	  //constructor for Map
	  public Map(int xResolution,int yResolution, int mapX, int mapY, String levelName) { 
	    visibleX=9;
	    visibleY=9;
	    boxWidth=1080/visibleX; //The size of each square
	    boxHeight=1080/visibleY;  //it would be best to choose a res that has a common divisor
	    try {
	      map = loadArray(levelName);
	      //get tile images
	      floorSprite = ImageIO.read(new File("floorSprite.png")); 
	      wallSprite = ImageIO.read(new File("wallSprite.png"));
	      obstacleSprite = ImageIO.read(new File("Trap.png"));
	    }catch(Exception e){
	      System.out.println("File not found Exception");
	    }
	    createWorldMap();     
	  }//end of Map constructor 
	   
	 
	  
	  /**loadArray
	    * method that loads map text file into an array and create tiles based on the numbers inside the file
	    * @param String
	    * @return a 2D array of integers
	    */ 
	  public int[][] loadArray(String fileName) throws Exception{// load array from text file
	    System.out.println("loading map file");
	    File map=new File(fileName);
	    Scanner input = new Scanner(map);
	    
	    int[][] mapArray= new int[40][40];
	    
	    for(int j=0;j<40;j++ ){
	      for(int i=0; i<40;i++){
	        mapArray[j][i]=input.nextInt();
	        // System.out.print(mapArray[j][i]+" ");
	      }
	      // System.out.print(" ");
	    }
	    input.close();
	    return mapArray;
	  }// end of loadArray    
	  
	  /**CreateWorldMap
	   * Creates a 2D array of tiles from the int array(load from text file)
	   * @param nothing
	   * @return nothing
	   */ 
	  public void createWorldMap() { 
	    worldMap = new Tiles[map.length][map[1].length];
	    for (int j=0;j<worldMap.length;j++){
	      for (int i=0;i<worldMap[1].length;i++) {
	        if (map[j][i]==0){ 
	          worldMap[j][i]=new floorTiles(Color.BLUE,i*boxWidth, j*boxHeight,boxWidth,boxHeight,true,floorSprite);
	        }
	        else if(map[j][i]==1) {
	          worldMap[j][i]=new wallTile(Color.RED,i*boxWidth, j*boxHeight,boxWidth,boxHeight,false,wallSprite);
	        }
	        /*
	        else if(map[j][i]==3) {
	          worldMap[j][i]=new entranceTiles(Color.GREEN,i*boxWidth, j*boxHeight,boxWidth,boxHeight,true,tempSprite);
	        }
	        else{
	          worldMap[j][i]=new obstaclesTile(Color.BLACK,i*boxWidth, j*boxHeight,boxWidth,boxHeight,true,obstacleSprite);
	        }
	        */
	      }
	    }
	  }//end of createWorldMap
	  
	  /**draw
	    * method that draws tiles according to map position
	    * @param Graphics, the players screen x and y coordinates as integers
	    * @return nothing
	    */ 
	  public void draw(Graphics g, int x ,int y) {
	    for (int j=0;j<visibleY;j++){
	      for (int i=0;i<visibleX;i++){
	        worldMap[y-visibleY/2+j][x-visibleX/2+i].draw(g,i,j);  
	      }
	    }
	  }//end of draw
	  
	  /**getWorldMap
	    * method returns 2D array of Tiles representing the game map
	    * @param nothing
	    * @return 2D array of Tile objects
	    */ 
	  public Tiles[][] getWorldMap(){ 
	    return worldMap;
	  }//end of getWorldMap
	  
	}//end of Map
  
 
  

	
	
	

