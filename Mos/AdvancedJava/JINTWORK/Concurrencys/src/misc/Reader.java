//Reader.java
package misc;

import java.util.Vector;

public class Reader extends Thread {

    // The queue is the communication channel between
    // this reader and the writer.
    private Vector queue;
    // A name so we can tell the readers apart.
    private String name;

    /**
     * Create a reader to read objects from a queue.
     *
     * @param queue
     *            Contains the flow of objects from the writer.
     * @param name
     *            A name so we can distinguish readers.
     */
    public Reader(Vector queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    public void run() {
        for (;;) {
            synchronized (queue) {
                // acquire the monitor
                // only one thread will traverse this section even though there
                // are
                // two reader objects. This depends on the object that we aquire
                 // the lock
                // on. In this case the same queue....
                while (queue.isEmpty()) {
                    try {
                        queue.wait();// releases the monitor

                    } catch (InterruptedException e) { // Nothing to do here.
                        // If the queue is
                        // empty,
                        // might as well go back to waiting.
                    }

                }
                // At this point the monitor has been re-acquired
                Object o = queue.remove(0);
                System.out.println(name + " got job number: " + o);

            } // release the monitor at the end of the synchronized block
        }
    }
}
