/**
 * Zombie 
 * GUI.java
 * @author Izzy
 * @date 1/15/2018
 */

import java.awt.Graphics;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


class GameWindowTest extends JFrame{
  Sniper sniperChar; //will become sniper
  highScore deathScore;
  Tank tankChar; // will become tank
  Scout scoutChar; //will become scout
  ArrayList<Monster> monsters;
  Monster monster;
  Player tempPlayer;
  JPanel rightPanel;
  JLabel xValue;
  JLabel yValue;
  JLabel fpsValue;
  JButton item1;//these all need to be changed to health packs
  JButton item2;
  JButton item3;
  JButton item4;
  boolean showArrow = true; //changed
  boolean useItems = false;
  boolean useItems2 = false;
  boolean useItems3 = false;
  boolean useItems4 = false;
  JProgressBar health;
  int characterSelect;
  JProgressBar monstersLeft;
  int totalMonsters=0;
  JLabel turnNumberValue;
  JLabel typeValue;
  JLabel waveValue;
  String name;
  int turns  = 0; //counts the number of turns that have occured
  int playerTurn = 0;
  
  
  
  String levelName="Level 1.txt";//need to get rid of this and just pass in the string for the map into
  String monsterLevelName="Monster Level 1.txt"; //will need to get rid of this to spawn in the monsters later
  int levelNum=1;// get rid of this after map and monster are able to be passed in
  
  GameWindowTest(int characterSelect, String name)throws Exception{
    super("Game Window");
    this.name = name;
    this.setSize(1920,1080);
    setResizable(false);  // set my window to allow the user to resize it
    
    //select character
    this.characterSelect = characterSelect;
    
    JPanel game = new GamePanel();
    
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    FlowLayout layout1=new FlowLayout();
    
    JPanel mainPanel= new JPanel();
    mainPanel.setLayout(new BorderLayout());
    
    
    // left panel
    JPanel leftPanel= new JPanel();
    leftPanel.setLayout(layout1);
    leftPanel.setFocusable(false);
    
    JPanel itemPanel= new JPanel();
    BoxLayout layout2 = new BoxLayout(itemPanel,BoxLayout.Y_AXIS);
    itemPanel.setLayout(layout2);
    itemPanel.setFocusable(false);
    
    Dimension d1=new Dimension(270,270);
    
    // placeholder JLabel, 
    JLabel placeHolder=new JLabel("                                                                                          ");
    placeHolder.setSize(270,270);
    
    item1=new JButton(new ImageIcon("potion.png"));// need to change it too a health pack
    item1.setPreferredSize(d1);
    item1.setBackground(Color.lightGray);
    item1.setFocusable(false);
    item1.addActionListener(new item1Listener());
    
    item2=new JButton(new ImageIcon("potion.png"));
    item2.setPreferredSize(d1);
    item2.setBackground(Color.lightGray);
    item2.setFocusable(false);
    item2.addActionListener(new item2Listener());
    
    item3=new JButton(new ImageIcon("potion.png"));
    item3.setPreferredSize(d1);
    item3.setBackground(Color.lightGray);
    item3.setFocusable(false);
    item3.addActionListener(new item3Listener());
    
    item4=new JButton(new ImageIcon("potion.png"));
    item4.setPreferredSize(d1);
    item4.setBackground(Color.lightGray);
    item4.setFocusable(false);
    item4.addActionListener(new item4Listener());
    
    // health bar
    Dimension d2=new Dimension(75,1080);
    
    health = new JProgressBar(JProgressBar.VERTICAL);
    health.setString("");
    health.setPreferredSize(d2);   
    health.setEnabled(true);
    health.setFocusable(false);
    
    health.setBackground(Color.darkGray);
    health.setForeground(Color.RED);
    
    
    health.setStringPainted(true);
    health.setMinimum(0);
    health.setMaximum(tempPlayer.getHealth());
    health.setValue(tempPlayer.getHealth());
    
    // monstersLeft bar
    monstersLeft = new JProgressBar(JProgressBar.VERTICAL);
    monstersLeft.setString("");
    monstersLeft.setPreferredSize(d2);
    monstersLeft.setEnabled(true);
    monstersLeft.setFocusable(false);
    monstersLeft.setMaximum(totalMonsters);
    
    monstersLeft.setBackground(Color.darkGray);
    monstersLeft.setForeground(Color.BLUE);
    
    monstersLeft.setStringPainted(true);
    monstersLeft.setValue(0);
    
    
    // left panel assembly
    itemPanel.add(placeHolder);
    itemPanel.add(item1);
    itemPanel.add(item2);
    itemPanel.add(item3);
    itemPanel.add(item4);
    
    leftPanel.add(itemPanel);
    leftPanel.add(health);
    leftPanel.add(monstersLeft);
    
    
    // right panel
    
    rightPanel=new JPanel();
    rightPanel.setFocusable(false);
    JLabel rightPanelBackground= new JLabel(new ImageIcon("stoneWallSprite.png")); //need to replace this image with bricks
    rightPanelBackground.setSize(new Dimension(420,1080));
    rightPanelBackground.setLayout(new GridLayout(8,2));
    rightPanelBackground.setFocusable(false);
    Dimension d3= new Dimension (420,100);
    Font font1= new Font("Serif", Font.BOLD, 44);
    Font font2= new Font("Serif", Font.BOLD, 60);
    Font font3= new Font("Serif", Font.BOLD, 40);
    
    JLabel turnNumber=new JLabel("  Turn Number:");
    turnNumber.setPreferredSize(d3);
    turnNumber.setFont(font2);
    turnNumber.setForeground(Color.BLACK);
    turnNumber.setFocusable(false);
    //can replace it with wave number
    JLabel wave=new JLabel("   Wave:");
    wave.setPreferredSize(d3);
    wave.setFont(font1);
    wave.setForeground(Color.BLACK);
    wave.setFocusable(false);
    
    JLabel type=new JLabel("   Type:");
    type.setPreferredSize(d3);
    type.setFont(font1);
    type.setForeground(Color.BLACK);
    type.setFocusable(false);
    JLabel x=new JLabel("    X:");
    x.setPreferredSize(d3);
    x.setFont(font1);
    x.setForeground(Color.BLACK);
    x.setFocusable(false);
    JLabel y=new JLabel("    Y:");
    y.setPreferredSize(d3);
    y.setFont(font1);
    y.setForeground(Color.BLACK);
    y.setFocusable(false);
    JLabel fps=new JLabel("   FPS:");
    fps.setPreferredSize(d3);
    fps.setFont(font1);
    fps.setForeground(Color.BLACK);
    fps.setFocusable(false);
    // values
    turnNumberValue=new JLabel("  "+turns);
    turnNumberValue.setPreferredSize(d3);
    turnNumberValue.setFont(font2);
    turnNumberValue.setForeground(Color.BLACK);
    turnNumberValue.setFocusable(false);
    waveValue=new JLabel("   "+levelNum);
    waveValue.setPreferredSize(d3);
    waveValue.setFont(font3);
    waveValue.setForeground(Color.BLACK);
    waveValue.setFocusable(false);
    
    typeValue.setPreferredSize(d3);
    typeValue.setFont(font3);
    typeValue.setForeground(Color.BLACK);
    typeValue.setFocusable(false);
    xValue=new JLabel("      "+tempPlayer.getX());
    xValue.setPreferredSize(d3);
    xValue.setFont(font3);
    xValue.setForeground(Color.BLACK);
    xValue.setFocusable(false);
    yValue=new JLabel("      "+tempPlayer.getY());
    yValue.setPreferredSize(d3);
    yValue.setFont(font3);
    yValue.setForeground(Color.BLACK);
    yValue.setFocusable(false);
    //declared globally
    fpsValue=new JLabel("12312");
    fpsValue.setPreferredSize(d3);
    fpsValue.setFont(font3);
    fpsValue.setForeground(Color.BLACK);
    fpsValue.setFocusable(false);
    
    rightPanelBackground.add(turnNumber);
    rightPanelBackground.add(turnNumberValue);
    rightPanelBackground.add(wave);
    rightPanelBackground.add(waveValue);
    
    
    rightPanelBackground.add(type);
    rightPanelBackground.add(typeValue);
    rightPanelBackground.add(x);
    rightPanelBackground.add(xValue);
    rightPanelBackground.add(y);
    rightPanelBackground.add(yValue);
    rightPanelBackground.add(fps);
    rightPanelBackground.add(fpsValue);
    
    rightPanel.add(rightPanelBackground);
    
    
    // final assembly
    
    mainPanel.add(leftPanel,BorderLayout.WEST);
    mainPanel.add(rightPanel,BorderLayout.EAST);
    mainPanel.add(game,BorderLayout.CENTER);
    
    //this.add(game);
    getContentPane().add(mainPanel);
    pack();
    this.setVisible(true);
  }//end of GameWindow constructor
  
  
  class item1Listener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      //Code here runs when the button is pressed only will disappear if 
      if (turns != 4 && playerTurn == 0){
        item1.setVisible(false);
        useItems = false;
        turns+= 1;
        if (turns == 4){
          playerTurn = 1;
        }
      }
      if (useItems){
        item1.setVisible(true);
      }
      //need to ask sasha if it needs to be changed each new wave so that more health can be gained back
      tempPlayer.setHealth(tempPlayer.getHealth()+5);
    }
  }
  class item2Listener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      //Code here runs when the button is pressed
      if (turns != 4 && playerTurn ==0){
        item2.setVisible(false);
        turns += 1;
        useItems2 = false;
        if (turns == 4){
          playerTurn = 1;
        }
      }
      if(useItems2){
        item2.setVisible(true);
      }
      tempPlayer.setHealth(tempPlayer.getHealth()+5);
    }
  }
  class item3Listener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      //Code here runs when the button is pressed
      if (turns != 4 && playerTurn == 0){
        item3.setVisible(false);
        turns+=1;
        useItems3 = false;
        if (turns == 4){
          playerTurn = 1;
        }
      }
      if(useItems3){
        item3.setVisible(true);
      }
      tempPlayer.setHealth(tempPlayer.getHealth()+5);
    }
  }
  class item4Listener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      //Code here runs when the button is pressed
      if (turns != 4 && playerTurn == 0){
        item4.setVisible(false);
        turns +=1;
        useItems4 = false;
        if (turns == 4){
          playerTurn = 1;
        }
      }
      if (useItems4){
        item4.setVisible(true);
      }
      tempPlayer.setHealth(tempPlayer.getHealth()+5);
    }
  }
  
  
  class GamePanel extends JPanel implements KeyListener{
    Timer clock = new Timer();
    boolean keyPressed = false;
    boolean attackAble = false;
    String direction = "right";
    String frameRate; //to display the frame rate to the screen
    long lastTimeCheck; //store the time of the last time the time was recorded
    long deltaTime; //to keep the elapsed time between current time and last time
    int frameCount; //used to cound how many frame occurred in the elasped time (fps)
    Map map;//changed
    
    
    int currentSpriteNum = 13;
    
    public GamePanel() { 
      clock.startTimer();
      //start counting frames
      //frames = new FrameRate();
      setPreferredSize(new Dimension(1080,1080));
      
      //create player
      if(characterSelect == 1){
        typeValue=new JLabel("   Scout");
        scoutChar = new Scout("dave",5,0,20,4);   
        tempPlayer = scoutChar;
      }
      else if(characterSelect == 2){
        typeValue=new JLabel("   Tank");
        tankChar = new Tank("bob",15,0,20,4);
        tempPlayer = tankChar;
      }
      else{
        typeValue=new JLabel("   Sniper");
        sniperChar = new Sniper("steve",10,0,20,4);
        tempPlayer = sniperChar;
      }
      map = new Map(1080,1080,levelName);
      
      
      try{
        monsters = arrayMonsters(monsterLevelName,10,10,map); // need to be replaced by a loaded in array of were they spawn
        
      }catch(Exception e){
        System.out.println("File not found Exception");
      }
      
      addKeyListener(this);
      setFocusable(true);
      requestFocusInWindow();
    }
    
    public String getFrameRate()  {
      long currentTime = System.currentTimeMillis();  //get the current time
      deltaTime += currentTime - lastTimeCheck; //add to the elapsed time
      lastTimeCheck = currentTime; //update the last time var
      frameCount++; // everytime this method is called it is a new frame
      if (deltaTime>=1000) { //when a second has passed, update the string message
        frameRate = frameCount + " fps" ;
        frameCount=0; //reset the number of frames since last update
        deltaTime=0;  //reset the elapsed time
        
      }
      return frameRate;
    }
    
    public ArrayList<Monster> arrayMonsters(String levelName1,int health,int damage, Map map2)throws Exception{
      System.out.println("loading map file");
      
      File map1=new File(levelName1);
      
      Scanner input = new Scanner(map1);
      
      int[][] mapArray= new int[40][40];
      
      ArrayList<Monster> monsters = new ArrayList<Monster>();
      
      Monster monster;
      for(int j=0;j<40;j++ ){
        for(int i=0; i<40;i++){
          
          mapArray[j][i]=input.nextInt();
          //System.out.print(mapArray[j][i]+" ");
          //System.out.println("input");
          if (mapArray[j][i]==6){
            
            monster = new Monster(health,damage,i,j);// changed
            
            monsters.add(monster);
            totalMonsters++;
          }
        }
      }
      input.close();
      return monsters;
    }
    
    public void drawMonsters(ArrayList<Monster> monsters1, int x, int y, Graphics g){
      for (int i=0;i<monsters.size();i++){
        monsters.get(i).draw(g,direction,x,y);
        if(monsters.get(i).getHealth() <= 0 ){
          monsters.remove(monsters.get(i));
          monstersLeft.setValue(monstersLeft.getValue()+1);
          tempPlayer.setExp(tempPlayer.getExp()+50);
          turnNumberValue.setText("   "+tempPlayer.getExp());
        }
        
      }
    }
    
    public void nextLevel(){
      
      levelNum++;
      resetItems();
      levelName="Level "+levelNum+ ".txt";
      monsterLevelName ="Monster Level "+levelNum+ ".txt";
      waveValue.setText("   "+levelNum);
      tempPlayer.setX(20);
      tempPlayer.setY(4);
      map = new Map(1080,1080,levelName);
      try{
        monsters = arrayMonsters(monsterLevelName,10,10,map);
        
      }catch(Exception e){
        System.out.println("File not found Exception");
      }
      
    }
    
    public void resetItems(){
      useItems = true;
      useItems2 = true;
      useItems3 = true;
      useItems4 = true;
    }
    
    public void paintComponent(Graphics g) {
      try{
        super.paintComponent(g); //required to ensure the panel si correctly redrawn
        
        
        fpsValue.setText(getFrameRate());
        //fpsValue.setText(Integer.toString(frames.getFrameRate()));
        
        map.draw(g,tempPlayer.getX(), tempPlayer.getY());//changed
        
        clock.stopTimer(); 
        
        if(clock.getTime() >= 0.5){    
          attackAble = true;   
        }
        
        //draw only if user presses a key
        if(keyPressed = true && turns != 4 && playerTurn ==0){
          //call method to draw bob based on user input
          //testing purposes: change name
          tempPlayer.draw(g,direction);
          turns+=1;
          if (turns == 4){
            playerTurn =1;
          }
        }
        
        //draw animations for player
        tempPlayer.render(g);
        tempPlayer.tick();
        tempPlayer.checkBasicAttackHit(monsters);
        
        //allow monster to seek and attack player
        for(int i = 0;i < monsters.size();i++){
          monsters.get(i).seekPlayer(tempPlayer,map);
          monsters.get(i).attackPlayer(tempPlayer);
        }
        drawMonsters(monsters,tempPlayer.getX(),tempPlayer.getY(),g);
        
        if (monsters.size()==0){
          totalMonsters=0;
          if (levelNum<8){
            nextLevel();
          }
        }
        
        if(tempPlayer.getHealth() <=0){
          
          dispose();
          //launches the highscore screen
          deathScore = new highScore(tempPlayer.getExp(),name,2);
        }else if (levelNum==8&&monsters.size()==0){
          
          dispose();
          //launches the highscore screen
          deathScore = new highScore(tempPlayer.getExp(),name,1);
        }
        else{
          repaint();
        }
        health.setValue(tempPlayer.getHealth());
      }catch (Exception e){}
    }
    
    //resets the amount of moves the player has back to 0 preparing them for the next turn
    public void resetTurn(){
      turns = 0;
      playerTurn = 0;
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
        if (map.getWorldMap()[tempPlayer.getY()][tempPlayer.getX()-1].getPassability()==true){
          if (map.getWorldMap()[tempPlayer.getY()][tempPlayer.getX()-1] instanceof obstaclesTile){
            tempPlayer.setHealth(tempPlayer.getHealth()-3);
          }
          tempPlayer.setX(tempPlayer.getX()-1);
        }
        
        direction = "left";
      }
      //move down
      else if(e.getKeyCode() == KeyEvent.VK_DOWN ){
        
        tempPlayer.setSpriteShown(false);
        if (map.getWorldMap()[tempPlayer.getY()+1][tempPlayer.getX()].getPassability()==true){
          if (map.getWorldMap()[tempPlayer.getY()+1][tempPlayer.getX()] instanceof obstaclesTile){
            tempPlayer.setHealth(tempPlayer.getHealth()-3);
          }
          tempPlayer.setY(tempPlayer.getY()+1);
        }
        
        direction = "down";
      }
      //move right
      else if(e.getKeyCode() == KeyEvent.VK_RIGHT ){
        
        tempPlayer.setSpriteShown(false);
        //dave.setX(dave.getX()+10);
        //bob.setX(bob.getX()+10);
        if (map.getWorldMap()[tempPlayer.getY()][tempPlayer.getX()+1].getPassability()==true){
          if (map.getWorldMap()[tempPlayer.getY()][tempPlayer.getX()+1] instanceof obstaclesTile){
            tempPlayer.setHealth(tempPlayer.getHealth()-3);
          }
          tempPlayer.setX(tempPlayer.getX()+1);
        }
        direction = "right";
      }
      //move up
      else if(e.getKeyCode() == KeyEvent.VK_UP ){
        tempPlayer.setSpriteShown(false);
        //dave.setY(dave.getY()-10);
        //bob.setY(bob.getY()-10);
        if (map.getWorldMap()[tempPlayer.getY()-1][tempPlayer.getX()].getPassability()==true){
          if (map.getWorldMap()[tempPlayer.getY()-1][tempPlayer.getX()] instanceof obstaclesTile){
            tempPlayer.setHealth(tempPlayer.getHealth()-3);
          }
          tempPlayer.setY(tempPlayer.getY()-1);
        }
        direction = "up";
      }  //note - would be better to make player class and pass in map, test movement in there
      xValue.setText("      "+tempPlayer.getX());
      yValue.setText("      "+tempPlayer.getY());   
    }
    
    
    public void keyReleased(KeyEvent e) {
      //when user releases key, set keyPressed to false
      keyPressed = false;
    }  
  }
  
}
