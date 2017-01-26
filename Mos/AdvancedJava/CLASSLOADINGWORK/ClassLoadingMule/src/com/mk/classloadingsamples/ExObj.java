package com.mk.classloadingsamples;

import java.io.Serializable;

public class ExObj
implements Serializable,Service
{
	public ExObj2 ivar = new ExObj2();

	public void doSomething() {
		System.out.println(" :: "+this.getClass().getName());
		
	}

	public void doSomeService() {
		System.out.println("doSomeService() :: "+this.getClass().getName()+" :: ClassLoader :: "+this.getClass().getClassLoader());
		
	}
}