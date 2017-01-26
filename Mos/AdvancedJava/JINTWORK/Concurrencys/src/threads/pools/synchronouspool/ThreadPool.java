//ThreadPool.java
package threads.pools.synchronouspool;

import java.util.*;

public class ThreadPool implements ThreadPoolInterface {

    private Vector freeThreads = new Vector();
    private Vector inUseThreads = new Vector();
    private static int INITIAL_SIZE = 2;

    public ThreadPool() {
        fillPool(INITIAL_SIZE);
    }

    private void fillPool(int poolSize) {
        for (int i = 0; i < poolSize; i++) {
            PoolableThread pt = new PoolableThread(this);
            pt.start();
            freeThreads.add(pt);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
        }
    }

    /**
     * Allocates a thread and starts this Runnable task.
     * @param task the task to run.
     */
    public void runTask(Runnable task) {
        /*if (freeThreads.isEmpty()) {
        throw new RuntimeException("All threads are in use");
        }*/
        synchronized (freeThreads) {   // acquire the monitor
            if (freeThreads.isEmpty()) {
                try {
                    freeThreads.wait();      // releases the monitor
                } catch (InterruptedException ex) {
                    // Nothing to do here. If the queue is empty,
                    // might as well go back to waiting.
                }
            }
        }

        PoolableThread t = (PoolableThread) freeThreads.remove(0);
        inUseThreads.add(t);
        t.setTask(task);
    }

    public void free(PoolableThread t) {
        inUseThreads.remove(t);
        freeThreads.add(t);

        synchronized (freeThreads) {
            freeThreads.notify();
        }
    }
}
