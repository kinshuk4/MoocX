package threads.pools.pool;

import java.util.*;

/**
*This class is the active pool for threads that are right now servicing the clients
*
*Author - Mohit Kumar<kumarmohit@vsnl.net>>
*Created - 10/03/2001
*Last Modified - December 28th 2001.
*/

class ActivePool extends Vector implements ActivePoolInt{

    protected Class threadClass;//the class for which the activepool is being maintained
    protected int presentSize;//the size of the active pool at any point of time

    public ActivePool(Class threadClass){

	this.threadClass=threadClass;
	presentSize=0;

    }//constructor closing


    //This method from Vector class is being overridden to provide extra functionality when ThreadCapsule
    //objects are being swapped in and out of the ActivePool. 
    public boolean add(Object threadCapsuleObject){

	if(threadCapsuleObject instanceof ThreadCapsuleInt){

	    if(super.add(threadCapsuleObject)){

		presentSize++;
		return true;

	    }//if closing
	    else return false;

	}//if closing
	else return false;

    }//add closing


    //This method is overridden to decrement the presentSize
    public boolean remove(Object threadCapsuleObject){

	if(threadCapsuleObject instanceof ThreadCapsule){

	    if(super.remove(threadCapsuleObject)){

		presentSize--;
		return true;

	    }//if closing
	    else return false;

	}//if closing
	else return false;

    }//remove closing

    //This method returns the current size of the active pool. The current size cannot be set by any
    //means, since it may lead to instability of the pool
    public int getPresentSize(){

	return presentSize;

    }//getPresentSize closing

    //This method returns the class for which the ActivePool is being maintained
    public Class getThreadClass(){

	return threadClass;

    }//getThreadClass closing

}//class closing
