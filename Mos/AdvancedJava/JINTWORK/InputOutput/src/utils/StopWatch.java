package utils;

public class StopWatch {
	private long startTime = -1;
    private long stopTime = -1;
    private boolean running = false;
    private static StopWatch stopwatch=null;
    public static StopWatch stopWatch(){
    	if (stopwatch==null)
    		stopwatch =new StopWatch();
    	return stopwatch;
    }
    public static StopWatch newStopWatch(){
    	return new StopWatch();
    	 
    }
    public StopWatch start() {
       startTime = System.currentTimeMillis();
       running = true;
       return this;
    }
    public StopWatch stop() {
       stopTime = System.currentTimeMillis();
       running = false;
       return this;
    }
    /** returns elapsed time in milliseconds
      * if the watch has never been started then
      * return zero
      */
    public long getElapsedTime() {
       if (startTime == -1) {
          return 0;
       }
       if (running){
       return System.currentTimeMillis() - startTime;
       } else {
       return stopTime-startTime;
       } 
    }

    public StopWatch reset() {
       startTime = -1;
       stopTime = -1;
       running = false;
       return this;
    }

}
