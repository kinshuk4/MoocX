package org.mk.training.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.mk.training.common.Car;

public interface ServerInterface extends Remote {
	public Car changeCarDetails(Car c) throws RemoteException;
}
