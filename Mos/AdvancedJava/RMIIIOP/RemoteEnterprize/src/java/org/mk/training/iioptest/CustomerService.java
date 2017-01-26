package org.mk.training.iioptest;

import java.rmi.RemoteException;

import javax.ejb.Remote;

@Remote
public interface CustomerService{
	public String echoString(String str) throws RemoteException;
	public Customer getCustomer(String lastName,String firstName)throws RemoteException;
}
