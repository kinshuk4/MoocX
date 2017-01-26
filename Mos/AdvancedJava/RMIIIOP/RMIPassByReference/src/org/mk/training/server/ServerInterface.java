package org.mk.training.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.mk.training.common.Car;
import org.mk.training.common.CarInterface;

public interface ServerInterface extends Remote {
	public void changeCarDetails(CarInterface c) throws RemoteException;
}
