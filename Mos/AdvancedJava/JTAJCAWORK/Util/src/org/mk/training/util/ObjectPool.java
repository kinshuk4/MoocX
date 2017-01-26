package org.mk.training.util;

/**
 * This interface is implemented by ObjectPoolImpl.
 * 
 * Author - Mohit Kumar<kumarmohit@vsnl.net> Created - 11/08/2001. Last
 * Modified - 11/08/2001.
 */

public interface ObjectPool {

	public static final int DEFAULT_SIZE = 2;

	public Object getObject();

	public void takeObject(Object object);

	public Class getObjectClass();

	public int getPoolSize();

}// interface closing
