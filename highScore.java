/**
 * Zombies
 * highScore.java
 * @author Izzy
 * @date 1/15/2018
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;



import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class highScore extends JFrame{
  //creating a highScore file object to get all of the scores
  private HighScoreFile highScoreFile;
  
  //temp name and score
  String name;
  int score;
  
  //panels
  private JPanel gameOverWin;
  private JPanel gameOverButtons;
  private JPanel gameOverPicture;
  private JPanel highScorePanel;
  private JPanel highScoreDisplay;
  private JPanel highScoreButtons;
  
  
  //buttons
  private JButton quitGameOver;
  private JButton quitHighScore;
  private JButton checkScore;
  private JButton playAgainScore;
  
  //labels
  private JLabel gameOverLabel;//to be replaced with pictures
  // private JLabel highScoreLabel;// to be replaced with JList 
  
  //frames
  private JFrame gameOverScreen;
  private JFrame highScoreScreen;
  
  //making a jlist
  private JList highScoreList;
  private DefaultListModel scoreModel;
  private JScrollPane highScoreScrollPane;
  
  //numbers
  private int num;
  //private int menu = 0;
  
  highScore(int score, String name, int num) throws Exception{ 
    this.score = score;
    this.num = num;
    this.name = name;
    //creating jframe
    gameOverScreen = new JFrame("Game Over");
    gameOverScreen.setSize(700,700);
    gameOverScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    highScoreScreen = new JFrame("Game Over");
    highScoreScreen.setSize(700,700);
    highScoreScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //creating the highScore object
    highScoreFile = new HighScoreFile();
    System.out.println("works");
    highScoreFile.getFile();
    highScoreFile.addScore(name, score);
    highScoreFile.bubbleSort();
    highScoreFile.createFile();
    
    //making the jlistmodel
    scoreModel = new DefaultListModel();
    //if(highScoreFile.scoreSize() != 0){
    for(int i = 0; i< highScoreFile.scoreSize(); i++){
      scoreModel.addElement(highScoreFile.returnScore(i));
    }
    // }
    
    //creating panels
    gameOverWin = new JPanel(new BorderLayout());
    gameOverButtons = new JPanel(new GridLayout(1,2));
    gameOverPicture = new JPanel();
    highScorePanel = new JPanel(new BorderLayout());
    highScoreDisplay = new JPanel();
    highScoreButtons = new JPanel(new GridLayout(1,2));
    
    //creating the buttons
    quitGameOver = new JButton("Quit Game");
    quitHighScore = new JButton("Quit Game");
    checkScore = new JButton("High Score");
    playAgainScore = new JButton("Play Again");
    
    //adding font to buttons
    quitGameOver.setFont(new Font("Arial", Font.PLAIN, 20));
    quitHighScore.setFont(new Font("Arial", Font.PLAIN, 20));
    checkScore.setFont(new Font("Arial", Font.PLAIN, 20));
    playAgainScore.setFont(new Font("Arial", Font.PLAIN, 20));
    
    //adding color to buttons
    quitGameOver.setBackground(new Color(115,77,38));
    quitGameOver.setForeground(Color.WHITE);
    quitHighScore.setBackground(new Color(115,77,38));
    quitHighScore.setForeground(Color.WHITE);
    checkScore.setBackground(new Color(115,77,38));
    checkScore.setForeground(Color.WHITE);
    playAgainScore.setBackground(new Color(115,77,38));
    playAgainScore.setForeground(Color.WHITE);
    
    //button action listeners
    quitGameOver.addActionListener(new quitGameOverListener());
    quitHighScore.addActionListener(new quitHighScoreListener());
    checkScore.addActionListener(new checkScoreListener());
    playAgainScore.addActionListener(new playAgainScoreListener());
    
    
    if (num == 1){
      //labels
      gameOverLabel = new JLabel();
      gameOverLabel.setIcon(new ImageIcon("WinScreen.png"));
      gameOverButtons.add(quitGameOver);
      gameOverButtons.add(checkScore);
    }else if(num == 2){
      //labels
      gameOverLabel = new JLabel();
      gameOverLabel.setIcon(new ImageIcon("LoseScreen.png"));
      gameOverButtons.add(quitGameOver);
      gameOverButtons.add(checkScore);
    }
    gameOverPicture.add(gameOverLabel);
    gameOverWin.add(gameOverPicture, BorderLayout.PAGE_START);
    gameOverWin.add(gameOverButtons, BorderLayout.PAGE_END);
    gameOverScreen.add(gameOverWin);
    gameOverScreen.setVisible(true);
  }
  class quitGameOverListener implements ActionListener {
    public  void actionPerformed(ActionEvent event) {
      System.out.println("quit game");
      gameOverScreen.dispose();
    }
  }  
  
  class quitHighScoreListener implements ActionListener {
    public  void actionPerformed(ActionEvent event) {
      System.out.println("quit highscore");
      highScoreScreen.dispose();
    }
  } 

  
  class checkScoreListener implements ActionListener {
    public  void actionPerformed(ActionEvent event) {
      System.out.println("going to high score");
      gameOverScreen.dispose();
      
      //creating the jlist
      highScoreList = new JList(scoreModel);
      highScoreList.setFont(new Font("Arial", Font.PLAIN, 30));
      highScoreList.setBackground(new Color(115,77,38));
      highScoreList.setForeground(Color.WHITE);
      
      //panel where the buttons are added
      highScoreButtons.add(quitHighScore);
      highScoreButtons.add(playAgainScore);
      
      //creating the scroll pane
      highScoreScrollPane = new JScrollPane(highScoreList);
      //adding everything the main panels
      highScoreDisplay.add(highScoreScrollPane);
      highScoreScrollPane.setPreferredSize(new Dimension(600, 600));
      highScorePanel.add(highScoreDisplay, BorderLayout.CENTER);
      highScorePanel.add(highScoreButtons, BorderLayout.PAGE_END);
      highScoreScreen.add(highScorePanel);
      highScoreScreen.setVisible(true);
    }
  }        
  
  class playAgainScoreListener implements ActionListener {
    public  void actionPerformed(ActionEvent event) {   
      System.out.println("play game again from high score");
      highScoreScreen.dispose();
      new MenuWindow();   
    }
  }          
}
