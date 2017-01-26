package misc;

//PrintStatic.java

public class PrintStatic implements Runnable {

  public static void main(String[] args) {

    // Create two threads
    Thread t1 = new Thread(new PrintStatic(), "First Thread");
    t1.start();
    Thread t2 = new Thread(new PrintStatic(), "Second Thread");
    t2.start();

    // Now, use the Thread static methods to look at these threads.
    int numThreads = Thread.activeCount();
    System.out.println("The number of threads is: " + numThreads);
    Thread[] threads = new Thread[numThreads];
    Thread.enumerate(threads);
    for (int i = 0; i < threads.length; i++) {
      System.out.println("  Thread named: " + threads[i].getName());
    } 
  } 

  public void run() {
    try {
      Thread.sleep(1000);   // wait a second...
    } catch (InterruptedException ie) {
      System.out.println("Oh no! I was interrupted!");
    } 

    // Call a method that will print the chain of method calls.
    aMethod();
  } 

  /**
   * Print the list of methods that were called to get to here.
   */
  private void aMethod() {
    Thread.dumpStack();
  } 
}
