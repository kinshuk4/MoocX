package org.mk.training.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CarInterface extends Remote {
	public String getGearType()throws RemoteException;
	public void setGearType(String gearType)throws RemoteException;
	public String getModel() throws RemoteException;
	public void setModel(String model)throws RemoteException;
}
