/**
 * Zombies
 * Timer.java
 * @author Izzy
 * @date 1/15/2018
 */

class Timer{
  
  private long startTime, endTime;
  private double elapsedTime;
  
  //constructor
  Timer(){
    this.elapsedTime = 0;
  }//end of constructor
  
  /**startTimer
    * method that sets startTime
    * @param nothing
    * @return nothing
    */ 
  public void startTimer(){
    startTime = System.nanoTime();
    elapsedTime = 0;
  }//end of startTimer
  
  /**stopTimer
    * method that get endTime and finds the elapsedTime
    * @param nothing
    * @return nothing
    */ 
  public void stopTimer(){
    endTime = System.nanoTime();
    elapsedTime = (endTime - startTime) / 1000000000.0;
  }//end of stopTimer
  
  /**getTime
    * method that returns the elapsedTime
    * @param nothing
    * @return double
    */ 
  public double getTime(){
    return this.elapsedTime;
  }//end of getTime
  
}//end pf Timer