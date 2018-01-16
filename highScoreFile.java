/**
 * Zombies
 * highScoreFile.java
 * @author Izzy
 * @date 1/15/2018
 */

import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;

class HighScoreFile{
  
  //creates the arraylist for all of the contacts
  private ArrayList<String> nameScore;
  private ArrayList<Integer> numScore;
  //creating the file object
  //File highScoreFile;
  String fileNames; 
  //PrintWriter output;
  String name;
  
  HighScoreFile(){
    try{    
      nameScore = new ArrayList<String>();
      numScore = new ArrayList<Integer>();
      //highScoreFile = new File("highScore.txt");
    }catch(Exception e){}
  }
  
  //sets name
  public void setName(String name){
    this.name = name;
  }
  
  //gets name
  public String getName(){
    return name;
  }
  
  // takes score info from a file
  public void getFile () throws Exception {
    File highScoreFile = new File("highScore.txt");
    Scanner input1 = new Scanner(highScoreFile);   
    int score;
    int count = 0;
    String name;   
    while (input1.hasNext()){
      count++;
      //Temporary storage for the lines read from the file
      name = input1.nextLine();
      score = input1.nextInt();
      input1.nextLine();
      nameScore.add(name);
      numScore.add(score);
    }
    //Closing the scanner
    input1.close();
    System.out.println("workds"+count);
  }
  
  //scoreSize
  public int scoreSize(){
    return nameScore.size();
  }
  
  //bubble sorting the scores
  public void bubbleSort(){
    for (int i=0;i<nameScore.size();i++){
      for (int j=0;j<nameScore.size()-1;j++){
        if (numScore.get(j)<numScore.get(j+1)){
          int swap=numScore.get(j);
          String swapName = nameScore.get(j);
          numScore.set(j, numScore.get(j+1));
          nameScore.set(j, nameScore.get(j+1));
          numScore.set((j+1), swap);
          nameScore.set((j+1), swapName);
        }
      }
    }
  }
  
  //creating a new file
  public void createFile() throws Exception{
    try{
      File highScoreFile = new File("highScore.txt");
      PrintWriter output = new PrintWriter(highScoreFile);
      for(int i = 0; i< nameScore.size(); i++){
        output.println(nameScore.get(i));
        output.println(numScore.get(i));
      }
      output.close();
    }catch(Exception e){}    
  }
  
  //returns the score
  public String returnScore(int i){
    // System.out.println(i);
    if(i != -1){
      return (nameScore.get(i)+" :                 "+numScore.get(i));
    }else{
      return null;
    }
  }
  
  //adding to the arraylist
  public void addScore(String name, int score){
    nameScore.add(name);
    numScore.add(score); 
    System.out.println(nameScore.size());
    for(int i =0; i<nameScore.size(); i++){
      System.out.println(nameScore.get(i));
      System.out.println(numScore.get(i));
    }
  }
}


