//StackVariable.java
package misc;
public class StackVariable extends Thread {

  public int modify(int amount) {
    int stack = 0;   // each call gets another copy of this variable
    for (int i = 0; i < 100; i++) {
      stack = stack + amount;
      System.out.println("stack = " + stack);
    } 
    return stack;   // after return, "stack" is destroyed
  } 

  public static void main(String[] args) {
    StackVariable t = new StackVariable();
    t.start();
    System.out.println("FINISHED main: stack = " + t.modify(+1));
  } 
  public void run() {
    System.out.println("FINISHED thread: stack = " + modify(-1));
  } 
}
