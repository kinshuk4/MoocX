package threads.pools.pool.objectpool;

import java.util.*;

/**
*This class represents an object pool. Given a class with a visible default constructor, it
*creates an pool of a specific size for the object. This pool always gives back an object
*when asked for. In case there is no more object in the pool, a new objet is instantiated and
*returned. The task of returning an object back to the pool is with the application that uses it.
*
*Author - MohitKumar>
*Created - 11/08/2001.
*Last Modified - 11/08/2001.
*/

public class ObjectPoolImpl extends Stack implements ObjectPool{

protected Class objectClass;//The reference to the Class object of the class for which the pool is maintained.
protected int poolSize;//The size of the pool - the maximum number of objects that can reside in the pool at the same time.

public ObjectPoolImpl(int poolSize){

this.poolSize=poolSize;

}//constructor closing

public ObjectPoolImpl(Class objectClass, int poolSize) throws InstantiationException, IllegalAccessException{

this.objectClass=objectClass;
this.poolSize=poolSize;

if(objectClass == null)return;

for(int i=0;i<poolSize;i++)this.push(objectClass.newInstance());

}//constructor closing


//This method is used to retrieve an object from the pool.
public Object getObject(){

Object tempObject=this.pop();

try{

if(tempObject == null && objectClass != null)tempObject=objectClass.newInstance();

}catch(InstantiationException ie){}
 catch(IllegalAccessException iae){}

return tempObject;

}//getObject closing


//This method is responsible to put an object back to the pool.
public void takeObject(Object object){

if(this.size() < poolSize)this.push(object);
else return;

}//takeObject closing


public Class getObjectClass(){return objectClass;}
public int getPoolSize(){return poolSize;}

}//class closing