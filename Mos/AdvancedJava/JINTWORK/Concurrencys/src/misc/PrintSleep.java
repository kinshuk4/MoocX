package misc;

//PrintSleep.java

public class PrintSleep {

  public static void main(String[] args) {
    long startSleepTime = System.currentTimeMillis();
    try {
      Thread.sleep(5000);   // sleep for 5 seconds (5000 milliseconds)
    } catch (InterruptedException ie) {
      System.out.println("Oh no! I was interrupted!");
    } 
    long endSleepTime = System.currentTimeMillis();
    System.out.println("Finished sleeping. Slept for " 
                       + (endSleepTime - startSleepTime) + " milliseconds");
  } 
}
