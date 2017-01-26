package garbagecollection;

import java.util.*;

public class Launcher {
  public static void main(String[] args) throws Exception {
    String testName = args[0];
    final CreateTest job = (CreateTest) Class.forName(testName).newInstance();
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      public void run() {
        System.out.println(job.getCount());
        System.exit(0);
      }
    }, 100000);
    job.run();
  }
}
abstract class CreateTest {
	  private long count;
	  public long getCount() {
	    return count;
	  }
	  protected final void incCount() {
	    count++;
	  }
	  public abstract void run();
	}

	class ObjectCreationNoPool extends CreateTest {
	  public void run() {
	    while (true) {
	      new Object();
	      incCount();
	    }
	  }
	}
	class ObjectCreationPool extends CreateTest {
		  public void run() {
		    Object[] pool = new Object[1 * 1024 * 1024];
		    int count = 0;
		    while (true) {
		      pool[(count++) % pool.length] = new Object();
		      incCount();
		    }
		  }
		}
		  