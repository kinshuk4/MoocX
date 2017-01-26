//Writer.java

package misc;

import java.util.Vector;

public class Writer {

  /**
   * Put some Integers on the queue so the
   * readers have something to read.
   */
  public void fill(Vector queue) {
    for (int i = 0; i < 1; i++) {
      queue.add(new Integer(i));
      synchronized (queue) {
        queue.notifyAll();
      } 
    } 
  } 
  public static void main(String[] args) throws Exception{

    // This vector will be the communication channel
    // between the writer and the readers.
    Vector queue = new Vector();
    Writer writer1 = new Writer();

    // Start two readers.
    Reader reader1 = new Reader(queue, "Reader1");
    reader1.start();
    Reader reader2 = new Reader(queue, "Reader2");
    reader2.start();
	Thread.sleep(1000);
    // Fill up the queue. The waiting readers will wake up
    // and start emptying the queue.
    writer1.fill(queue);
  } 
}
