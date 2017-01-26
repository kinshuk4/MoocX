package org.mk.training.common;

import java.io.Serializable;
//Object of this class is Pass-by-value
public class Car implements Serializable {
	private String model=null;
	private String gearType=null;
	public Car(String model, String gearType) {
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
