package threads.pools.pool;

import java.util.*;

/**
* This program is an generic implementation of thread pool engine, which takes the following input
*a) Size of the pool to be constructed
*b) Name of the class which implements Runnable (which has a visible default constructor)
*and constructs a thread pool with active threads that are waiting for activation.
*once the threads have finished processing they come back and wait once again in the pool.
*
*This thread pool engine can be locked i.e. if some internal operation is performed on the pool
*then it is preferable that the thread engine be locked. Locking ensures that no new threads are issued
*by the engine. However, the currently executing threads are allowed to continue till they come back
*to the passivePool.
*
*This pool returns "null" in two cases a) When the pool is  locked.
*b) When there are no free thread in the ActivePool. This pool does not issue temporary threads.
*
*Author - Mohit Kumar<kumarmohit@vsnl.net>>
*Last Modified - 9/03/2001
*/

public class ThreadPool implements ThreadPoolInt{

    protected int totalPoolSize;//initial size of the pool as indicated by the user
    protected int activePoolSize;//current no of threads in the pool which are not available for servicing
    protected int passivePoolSize;//current no of threads in the pool which are available for servicing

    protected Class threadClass;//the class for which the pool is constructed, it must implement runnable
    //and have a visible default constructor

    protected ActivePoolInt activePool;//the pool where the currently active threads which are servicing the
    //clients are kept
    protected PassivePoolInt passivePool;//the pool where the currently passive threads which are not servicing
    //the clients are kept

    protected boolean poolLocked;//this represents the status of the pool - unlocked or locked
    // if pool is locked then no more threads are issued by the thread pool
    // this feature shall become useful when some internal change to the
    //pool is being performed

    protected ThreadCapsuleInt tempThreadCapsule;//temporary storage for ThreadCapsule objects

    public ThreadPool( int totalPoolSize ) {

	poolLocked = false;

	try {

	    initializePools( null, totalPoolSize );

	} catch( Exception exc ) {}

    }//constructor closing

    public ThreadPool(Class threadClass, int totalPoolSize) throws IllegalClassException, IllegalAccessException, InstantiationException{

	poolLocked=false;
	this.initializePools(threadClass, totalPoolSize);

    }//constructor closing


/**
*This method is called by the constructor as well as setThreadClass method to initialize or
*reinitialize all the pools.
*/
    protected void initializePools(Class threadClass, int totalPoolSize) throws IllegalClassException, IllegalAccessException, InstantiationException{

	if(threadClass != null && !(Runnable.class.isAssignableFrom(threadClass)))throw new IllegalClassException();
	else{

	    this.threadClass=threadClass;
	    this.totalPoolSize=totalPoolSize;

	    activePool=new ActivePool(threadClass);
	    passivePool=new PassivePool(threadClass);

	    for(int i=0;i<totalPoolSize;i++){

		tempThreadCapsule=new ThreadCapsule(threadClass);
		tempThreadCapsule.setActivePool(activePool);
		tempThreadCapsule.setPassivePool(passivePool);
		passivePool.push(tempThreadCapsule);

		activePoolSize=activePool.getPresentSize();
		passivePoolSize=passivePool.getPresentSize();

	    }//for closing

	}//else closing

    }//initializePools closing

    public Class getThreadClass(){return threadClass;}//returns the class for which the pool is being maintained

/**
*Destroyes the current pools (active as well as passive) and reinitializes the pool.
*this method should not be called if there are current clients being serviced.
*/
    public void setThreadClass(Class threadClass, int totalPoolSize) throws IllegalClassException, IllegalAccessException, InstantiationException{

	this.setPoolLocked(true);
	this.initializePools(threadClass, totalPoolSize);
	this.setPoolLocked(false);

    }//setClass closing



    public int getTotalPoolSize(){return totalPoolSize;}//returns the value of totalPoolSize variable

/**
*This method sets the size of the total pool (active + passive) pool - if the size of the active
*pool is more than the size of the passive pool then ClientsUnderServiceException is thrown
*otherwise the size of the passive pool is reduced to (totalPoolSize-newPoolSize)
*/
    public void setTotalPoolSize(int totalPoolSize) throws ClientsUnderServiceException, IllegalClassException, IllegalAccessException, InstantiationException{

	if(totalPoolSize == this.totalPoolSize)return;

	if(totalPoolSize > this.totalPoolSize){

	    for(int i=0;i<(totalPoolSize-this.totalPoolSize);i++){

		tempThreadCapsule=new ThreadCapsule(threadClass);
		tempThreadCapsule.setActivePool(activePool);
		tempThreadCapsule.setPassivePool(passivePool);
		passivePool.push(tempThreadCapsule);

		activePoolSize=activePool.getPresentSize();
		passivePoolSize=passivePool.getPresentSize();

	    }//for closing

	}//if closing

	else{

	    poolLocked=true;

	    int reduce=this.totalPoolSize-totalPoolSize;

	    if(reduce > passivePoolSize){

		poolLocked=false; 
		throw new ClientsUnderServiceException("Clients under service, cannot reduce pool size");

	    }//if closing

	    else if(reduce == passivePoolSize){poolLocked=false; return;}
	    else {passivePool.setSize(this.totalPoolSize-reduce); poolLocked=false;}

	}//else closing

    }//setTotalPoolSize closing



    //the size of these two pools (active and passive) cannot be changed individually
    public int getActivePoolSize(){return activePoolSize;}//returns the value of the activePoolSize variable
    public int getPassivePoolSize(){return passivePoolSize;}//returns the value of the passivePoolSize variable



    public boolean isPoolLocked(){return poolLocked;}//returns the current value of the poolLocked variable
    public void setPoolLocked(boolean lockingState){poolLocked=lockingState;}//toggles the locked or unlocked state of the pool



/**
*This method returns a ThreadCapsule object with the client processing thread embedded in it
*This object is responsible for controlling the core thread, if there are no passive threads in the pool
*this method returns null. 
*/

public ThreadCapsuleInt getThreadCapsule(){

	if(poolLocked)return null;
	else{

	    tempThreadCapsule=(ThreadCapsuleInt)passivePool.pop();

	    if(tempThreadCapsule == null) return tempThreadCapsule;
	    else{

		activePool.add(tempThreadCapsule);
		activePoolSize=activePool.getPresentSize();
		passivePoolSize=passivePool.getPresentSize();

		return tempThreadCapsule;

	    }//else closing

	}//else closing

    }//getThreadCapsule closing


}//class ThreadPool closing
