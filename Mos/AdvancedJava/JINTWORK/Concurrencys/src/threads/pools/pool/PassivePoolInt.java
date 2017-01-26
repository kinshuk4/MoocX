package threads.pools.pool;

/**
*This interface is implemented by the class which acts as the passive pool for threads - in this
*case PassivePool
*Author - Mohit.MAILTO-kumarmohit@vsnl.net
*Last Modified - 9/03/2001
*/

public interface PassivePoolInt{

public int getPresentSize();
public Object pop();
public Object push(Object object);
public Class getThreadClass();
public void setSize(int newSize);

}//interface closing
