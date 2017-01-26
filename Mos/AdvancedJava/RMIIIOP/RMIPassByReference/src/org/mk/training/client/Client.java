package org.mk.training.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.mk.training.common.Car;
import org.mk.training.common.CarInterface;
import org.mk.training.server.ServerInterface;

public class Client {

	/**
	 * @param args
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		ServerInterface remoteref=(ServerInterface)Naming.lookup("rmi://localhost:1099/RemoteServer");
		CarInterface originalcar=new Car("M5","AUTO");
		System.out.println("Original Car :: "+originalcar);
		System.out.println("Original Car Model :: "+originalcar.getModel());
		remoteref.changeCarDetails(originalcar);
		System.out.println("Original Car :: "+originalcar);
		System.out.println("Original Car Model :: "+originalcar.getModel());
		System.out.println("Remote Reference :: "+remoteref);
		System.out.println("Remote Reference Type :: "+remoteref.getClass().getName());
	}

}
