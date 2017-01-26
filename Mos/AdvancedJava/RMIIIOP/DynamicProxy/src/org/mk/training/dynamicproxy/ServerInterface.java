package org.mk.training.dynamicproxy;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ServerInterface extends Remote {
	public Object call(String methodname ,Object arg[]) throws RemoteException;
}
