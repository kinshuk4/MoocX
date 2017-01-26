//PoolableThread.java

package threads.pools.synchronouspool;

class PoolableThread extends Thread {

  Runnable task = null;
  ThreadPool pool;

  PoolableThread(ThreadPool pool) {
    this.pool = pool;
  }

  synchronized void setTask(Runnable task) {
    this.task = task;
    notify();
  } 

  synchronized void executeTasks() {
    for (; ; ) {
      try {
        if (task == null) {
          wait();
        } 
      } catch (InterruptedException ex) {

        // Interrupted
      } 
      task.run();
      task = null;
      pool.free(this);
    } 
  } 
  public void run() {
    executeTasks();
  } 
}
