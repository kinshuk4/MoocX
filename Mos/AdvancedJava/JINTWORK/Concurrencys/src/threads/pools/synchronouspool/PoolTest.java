//PoolTest.java

package threads.pools.synchronouspool;

public class PoolTest implements Runnable {

  public PoolTest() {
    ThreadPool tp = new ThreadPool();
    for (int i=0; i<10; i++)
      tp.runTask(this);

    try {Thread.sleep(400000);} catch (Exception e) {}
  }

  public static void main(String[] args) {
    PoolTest poolTest1 = new PoolTest();
  }
  public void run() {
    System.out.println("Start on "+Thread.currentThread().getName());
    try {Thread.sleep(2000);} catch (InterruptedException ie) {}
    System.out.println("Done on "+Thread.currentThread().getName());
  }
}