package threads.pools.pool;

/**
*This class represents a layer or a capsule over the client processing thread and is responsible for
*managing the life-cycle of individual threads. Any interaction with the thread shall have to pass
*through the capsule
*
*Author - Mohit Kumar<kumarmohit@vsnl.net>>
*Last Modified - 9/03/2001
*/

public class ThreadCapsule extends Thread implements ThreadCapsuleInt{

    protected Class threadClass;//the class of the coreThread that is embeded in this threadCapsule
    protected Task coreThread;//the thread which is embedded in this capsule;
    protected boolean state;//the state of the capsule, active or passive - false indicates the thread is not servicing any client right now.
    protected boolean keepAlive;//this variable is used to control the thread & to keep it alive or terminate it
    protected ActivePoolInt activePool;//reference to the ActivePool for the capsule
    protected PassivePoolInt passivePool;//reference to the PassivePool for the capsule

    public ThreadCapsule(Class threadClass) throws InstantiationException, IllegalAccessException{
	this.threadClass = threadClass;
	
	if ( threadClass != null )
	    coreThread=( Task )threadClass.newInstance();

	state=false;
	keepAlive=true;
    this.setName("ThreadCapsule");

	this.setPriority(8);//The priority of ThreadCapsule thread must be highest in this section, otherwise
	//there is a chance that the thread which is activating the ThreadCapsule thread
	//shall run and execute wait() on this object - DO NOT CHANGE THIS PRIORITY.
	//THIS CODE WAS ADDED AFTER TESTING.

	this.setDaemon(true);//This code was added after testing - at any point if only ThreadCapsule objects
	//are running in the JVM then the JVM shall exit, as there is no server thread running
	//there is little point in keeping the ThreadCapsule objects alive.
	//DO NOT CHANGE THIS CODE - OTHERWISE IN CASE OF MAIN SERVER
	//CRASHING THE JVM WILL NEVER EXIT NORMALLY
	this.start();

    }//constructor closing

    public synchronized void setTask( Task task ) {
	coreThread = task;
	notify();
    }

//The run method is called in two circumstances - i) At the time of creation and start of the thread
//by the ThreadPool class. ii) an explicit call on the activateThreadCapsule() method of this class.
    public synchronized void run(){
	try {

	    while(keepAlive){

                if (!state) wait();//if the thread is not in service then wait till notified.
                else {notify();return;}//notify and return.

                try{

            		coreThread.run();

                }catch(Exception unexpectedMess){unexpectedMess.printStackTrace();/*Dont do anything, just get out clean.*/}

		activePool.remove( this );
                state=false;
		passivePool.push( this );
                
		// The task shouldn't be repeated
		//coreThread = null; - if the task is to be set per case basis, uncomment this.

	    }//while closing

	} catch( InterruptedException exc ) {}
          catch(RuntimeException rte){}

    }//run closing

    public void setActivePool(ActivePoolInt activePool){this.activePool=activePool;}
    public ActivePoolInt getActivePool(){return activePool;}

    public void setPassivePool(PassivePoolInt passivePool){this.passivePool=passivePool;}
    public PassivePoolInt getPassivePool(){return passivePool;}

    public void setState(boolean state){this.state=state;}
    //public boolean getState(){return state;}

    public void setKeepAlive(boolean keepAlive){this.keepAlive=keepAlive;}
    public boolean getKeepAlive(){return keepAlive;}

    public Class getThreadClass(){return threadClass;}

    public Task getTask(){return coreThread;}

    //This method must be called by the client to activate the thread capsule as well as the embedded thread
    //the generic run() method of the embedded thread should not be called as this may result in
    //destabilization of the entitre pool
    public void activateThreadCapsule(){state=true; this.run();}//Changes state from not active to active and begins servicing

}//class closing


