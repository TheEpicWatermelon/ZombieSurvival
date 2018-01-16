/**
 * Zombies
 * MenuWindow.java
 * @author Izzy
 * @date 1/15/2018
 */

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.*;

import java.awt.GridLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class MenuWindow extends JFrame{
  
  //panels
  private JPanel loadingScreen;
  private JPanel playButtonPanel;
  private JPanel playerClassScreen;
  private JPanel intro;
  private JPanel characterButtonPanel;
  
  //buttons to click
  private JButton playButton;
  private JButton scoutButton;
  private JButton tankButton;
  private JButton sniperButton;
  private JButton classChosenButton;
  
  //labels
  private JLabel scoutLabel;
  private JLabel tankLabel;
  private JLabel sniperLabel;
  private JLabel introLabel;
  
  //frames
  private JFrame introScreen;
  private JFrame characterScreen;
  
  //nums for what class is chosen
  private int characterSelect=0;
  
  MenuWindow() {
    //creating a window
    introScreen = new JFrame("Loading Screen");//creates a new window to work with
    introScreen.setSize(1280,600);//set size of window by 1280 by 600 pixals
    introScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //sets the program to close when the window closes
    characterScreen = new JFrame("Character Screen");
    characterScreen.setSize(700,700);
    characterScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    
    //creating panels
    loadingScreen = new JPanel(new BorderLayout());
    loadingScreen.setBackground(Color.BLACK);
    playButtonPanel = new JPanel(new BorderLayout());
    playButtonPanel.setBackground(Color.BLACK);
    playerClassScreen = new JPanel(new BorderLayout());
    playerClassScreen.setBackground(Color.BLACK);
    characterButtonPanel = new JPanel(new GridLayout(2,3));
    characterButtonPanel.setBackground(Color.WHITE);
    intro = new JPanel();
    intro.setBackground(Color.BLACK);
    
    
    
    //creating buttons
    playButton = new JButton("PLAY");
    scoutButton = new JButton("SCOUT");
    tankButton = new JButton("TANK");
    sniperButton = new JButton("SNIPER");
    classChosenButton = new JButton("SELECT CHARACTER");
    //font size
    playButton.setFont(new Font("Arial", Font.PLAIN, 20));
    scoutButton.setFont(new Font("Arial", Font.PLAIN, 40));
    tankButton.setFont(new Font("Arial", Font.PLAIN, 38));
    sniperButton.setFont(new Font("Arial", Font.PLAIN, 40));
    classChosenButton.setFont(new Font("Arial", Font.PLAIN, 40));
    //button color
    playButton.setBackground(new Color(115,77,38));
    playButton.setForeground(Color.WHITE);
    scoutButton.setBackground(new Color(115, 77, 38));
    scoutButton.setForeground(Color.WHITE);
    tankButton.setBackground(new Color(115, 77, 38));
    tankButton.setForeground(Color.WHITE);
    sniperButton.setBackground(new Color(115, 77, 38));
    sniperButton.setForeground(Color.WHITE);
    classChosenButton.setBackground(new Color(115, 77, 38));
    classChosenButton.setForeground(Color.WHITE);
    
    //creating jlabels
    scoutLabel = new JLabel();
    scoutLabel.setIcon(new ImageIcon("mageSingle.png"));
    tankLabel = new JLabel();
    tankLabel.setIcon(new ImageIcon("warriorSingle(2).png"));
    sniperLabel = new JLabel();
    sniperLabel.setIcon(new ImageIcon("archerSingle(2).png"));
    introLabel = new JLabel();
    introLabel.setIcon(new ImageIcon("newTitleScreen.png"));
    
    //creating an action listner to buttons
    playButton.addActionListener(new playButtonListener());
    scoutButton.addActionListener(new scoutButtonListener());
    tankButton.addActionListener(new tankButtonListener());
    sniperButton.addActionListener(new sniperButtonListener());
    classChosenButton.addActionListener(new classChosenButtonListener());
    
    intro.add(introLabel);
    playButtonPanel.add(playButton, BorderLayout.PAGE_START);
    loadingScreen.add(intro);
    loadingScreen.add(playButtonPanel, BorderLayout.PAGE_END);
    introScreen.add(loadingScreen);
    introScreen.setVisible(true);
    
  }
  
  //should open next frame with character choice
  class playButtonListener implements ActionListener {
    public  void actionPerformed(ActionEvent event) {
      introScreen.dispose();
      
      //creating a new frame for character selection
      characterButtonPanel.add(scoutLabel);
      characterButtonPanel.add(tankLabel);
      characterButtonPanel.add(sniperLabel);
      characterButtonPanel.add(scoutButton);
      characterButtonPanel.add(tankButton);
      characterButtonPanel.add(sniperButton);
      //characterButtonPanel.add(classChosenButton, BorderLayout.PAGE_END);
      playerClassScreen.add(characterButtonPanel, BorderLayout.CENTER);
      playerClassScreen.add(classChosenButton, BorderLayout.PAGE_END);
      characterScreen.add(playerClassScreen);
      characterScreen.setVisible(true);
    }
  }
  
  class scoutButtonListener implements ActionListener {
    public  void actionPerformed(ActionEvent event) {
      //redrawing the image to highlight scout
      sniperLabel.setIcon(new ImageIcon("archerSingle(2).png"));
      tankLabel.setIcon(new ImageIcon("warriorSingle(2).png"));
      scoutLabel.setIcon(new ImageIcon("mageSelect.png"));
      characterSelect = 1;
    }
  }
  
  class tankButtonListener implements ActionListener {
    public  void actionPerformed(ActionEvent event) {
      //redrawing the image to highlight tank
      sniperLabel.setIcon(new ImageIcon("archerSingle(2).png"));
      tankLabel.setIcon(new ImageIcon("warriorSelect.png"));
      scoutLabel.setIcon(new ImageIcon("mageSingle.png"));
      characterSelect = 2;
    }
  } 
  
  class sniperButtonListener implements ActionListener {
    public  void actionPerformed(ActionEvent event) {
      //redrawing the image to highlight sniper
      sniperLabel.setIcon(new ImageIcon("archerSelect.png"));
      tankLabel.setIcon(new ImageIcon("warriorSingle(2).png"));
      scoutLabel.setIcon(new ImageIcon("mageSingle.png"));
      characterSelect = 3;
    }
  } 
  
  class classChosenButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent event){
      try{
          String input = JOptionPane.showInputDialog("What is your name");
          characterScreen.dispose();
          new GameWindowTest(characterSelect , input);
        }catch(Exception e){}
      }
  }     
}//end of MenuWindow



