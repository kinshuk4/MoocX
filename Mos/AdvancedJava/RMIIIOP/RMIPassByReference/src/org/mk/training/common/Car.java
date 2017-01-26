package org.mk.training.common;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
//Object of this class is Pass-by-Reference because now this is
//a remote Object
public class Car extends UnicastRemoteObject implements CarInterface  {
	private String model=null;
	private String gearType=null;
	public Car(String model, String gearType) throws RemoteException {
		super();
		this.model = model;
		this.gearType = gearType;
	}
	public String getGearType() {
		return gearType;
	}
	public void setGearType(String gearType) {
		this.gearType = gearType;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String toString() {
		return super.toString()+" CarDetails:: model: "+model+" gearType:"+gearType;
	}
}
