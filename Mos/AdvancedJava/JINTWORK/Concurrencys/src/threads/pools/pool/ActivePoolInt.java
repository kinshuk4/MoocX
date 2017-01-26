package threads.pools.pool;

import java.util.*;

/**
*This is the interface implemented by the class acting as the active pool of threads - in this case
*ActivePool
*Author - Mohit Kumar<kumarmohit@vsnl.net>>
*Created - 9/03/2001
*Last Modified - December 28th 2001.
*/

public interface ActivePoolInt{

public boolean remove(Object threadCapsuleObject);
public boolean add(Object threadCapsuleObject);
public int getPresentSize();
public Class getThreadClass();

}//interface closing

