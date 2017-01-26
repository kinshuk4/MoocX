package threads.pools.pool;

import java.util.*;

/**
*This class represents the passive pool of threads which are waiting to service clients
*Author - Mohit Kumar<kumarmohit@vsnl.net>
*Last Modified - 9/03/2001
*/

public class PassivePool extends Stack implements PassivePoolInt{

protected int presentSize;//present size of the PassivePool
protected Class threadClass;//the class for which the Passive Pool is being maintained

public PassivePool(Class threadClass){

super();
this.threadClass=threadClass;
presentSize=0;

}//constructor closing

//Returns the presentSize of the PassivePool
public int getPresentSize(){

return presentSize;

}//getPresentSize closing


//This method is overloaded to decrement the presentSize when an object is popped
public Object pop(){

if(presentSize<=0) return null;
else{

presentSize--;
return super.pop();

}//else closing

}//pop closing


//This method is overloaded to increment the presentSize when an object is pushed
public Object push(Object threadCapsuleObj){

super.push(threadCapsuleObj);
presentSize++;
return threadCapsuleObj;

}//push closing

//This method returns the class for which the pool is being maintained
public Class getThreadClass(){

return threadClass;

}//getClass closing


//this method of Stack class is being overridden - the functionality remains the same as in Vector
//this method reduces the size of the passivePool.
public void setSize(int newSize){

super.setSize(newSize);

}//setSize closing

}//class closing
