package org.mk.training.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import org.mk.training.common.Car;
import org.mk.training.common.CarInterface;

public class RemoteServer extends UnicastRemoteObject implements ServerInterface{

	protected RemoteServer() throws RemoteException {
		// TODO Auto-generated constructor stub
	}

	public void changeCarDetails(CarInterface c) throws RemoteException {
		System.out.println("Car on Server before change:: "+c);
		c.setModel("M6");
		System.out.println("Car on Server after change:: "+c);
	}
	public static void main(String[] args) throws Exception {
        LocateRegistry.createRegistry(1099);
		RemoteServer obj=new RemoteServer();
		Naming.rebind("/RemoteServer", obj);
		System.out.println("RemoteServer bound in registry:: "+obj);
	}


}
