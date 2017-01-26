package threads.pools.pool;

/**
*This interface is implementd by the class which is acting as the thread capsule - in this case
*ThreadCapsule
*
*Author - Mohit Kumar<kumarmohit@vsnl.net>
*Last Modified - 9/03/2001
*/

public interface ThreadCapsuleInt{

    public void setActivePool(ActivePoolInt activePool);
    public ActivePoolInt getActivePool();
    public void setPassivePool(PassivePoolInt passivePool);
    public PassivePoolInt getPassivePool();
    public void setState(boolean state);
    //public boolean getState();
    public void setKeepAlive(boolean keepAlive);
    public boolean getKeepAlive();
    public Class getThreadClass();
    public Task getTask();
    public void setTask( Task task );
    public void activateThreadCapsule();
    

}//interface closing
