package threads.pools.pool;

/**
 * Any work encapsulated in a ThreadCapsule must implement this interface.
 *
 * @author "Mohit Kumar<kumarmohit@vsnl.net>" >
 * @version 1.0
 * @since 1.0
 */
public interface Task extends Runnable {

//Added by Ironluca
public void setParameters(Object parameters)throws IllegalArgumentException;

}
