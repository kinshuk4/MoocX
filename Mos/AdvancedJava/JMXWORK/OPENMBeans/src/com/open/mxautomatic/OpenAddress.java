package com.open.mxautomatic;

import java.beans.ConstructorProperties;
import java.util.HashMap;
import java.util.Map;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeDataView;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

import com.util.ObjectAnalyzer;

public class OpenAddress /*implements CompositeDataView*/{
	private String street;
	private String city;
	private String state;
	private int zip;
	
	
	//in JDK 5.0 and below the convertion to open representation 
	//must be done manually
	@ConstructorProperties({"street", "city","state","zip"})/*Since 6.0*/
	public OpenAddress(String street, String city, String state, int zip) {
		super();
		// TODO Auto-generated constructor stub
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	public String toString() {
		// TODO Auto-generated method stub
		return ObjectAnalyzer.genericToString(this);
	}

}
