//PrintDaemon2.java
package misc;
public class PrintDaemon2 extends Thread {

  public static void main(String[] args) {
    PrintDaemon2 p = new PrintDaemon2();
    p.setDaemon(true);
    p.start();
    // Just print M 30 times, then stop
    for (int i=0; i<30; i++)
      System.out.print("M");

    // After the 30 Ms are printed, main returns
  }

  public void run() {
    // The thread will print Ts as long as it exists
    for (;;)
      System.out.print("T");
  }
}