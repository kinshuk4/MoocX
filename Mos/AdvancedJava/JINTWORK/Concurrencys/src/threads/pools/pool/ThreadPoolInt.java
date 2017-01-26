package threads.pools.pool;


/**
*This interface is implemented by the class which shall be the main engine for thread pool
*in this case ThreadPool.
*Author - Mohit Kumar<kumarmohit@vsnl.net>>
*Created - 9/03/2001.
*Last Modified - December 28th 2001.
*/

public interface ThreadPoolInt{


//For features of each of the methods consult the ThreadPool class which implements
//this interface.

public Class getThreadClass();
public int getTotalPoolSize();
public void setTotalPoolSize(int totalPoolSize) throws ClientsUnderServiceException, IllegalClassException, IllegalAccessException, InstantiationException;
public int getActivePoolSize();
public int getPassivePoolSize();
public boolean isPoolLocked();
public void setPoolLocked(boolean lockingState);
public ThreadCapsuleInt getThreadCapsule();
public void setThreadClass(Class threadClass, int totalPoolSize)throws IllegalClassException, IllegalAccessException, InstantiationException;

}//interface closing
