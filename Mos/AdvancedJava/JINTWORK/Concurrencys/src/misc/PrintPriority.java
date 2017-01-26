
//PrintPriority.java
package misc;
public class PrintPriority extends Thread {

  public static void main(String[] args) {
    PrintPriority t = new PrintPriority();
    t.setPriority(MAX_PRIORITY);
    t.start();
    for (; ; ) {
      System.out.print("M");
    }
  } 
  public void run() {
    for (; ; ) {
      System.out.print("T");
    }
  } 
}
