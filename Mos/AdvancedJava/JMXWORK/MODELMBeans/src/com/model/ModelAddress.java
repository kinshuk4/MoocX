package com.model;

import com.util.ObjectAnalyzer;

public class ModelAddress{
	private String street;
	private String city;
	public ModelAddress(String street, String city) {
		super();
		
		this.street = street;
		this.city = city;
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
	public void reset() {
		street=null;
		city=null;
		
	}
	public String toString() {
		// TODO Auto-generated method stub
		return ObjectAnalyzer.genericToString(this);
	}
}
