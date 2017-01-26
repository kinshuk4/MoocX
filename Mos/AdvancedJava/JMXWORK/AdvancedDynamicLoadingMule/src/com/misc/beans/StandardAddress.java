package com.misc.beans;

import com.util.ObjectAnalyzer;

public class StandardAddress implements StandardAddressMBean{
	private String street;
	private String city;
	public StandardAddress(String street, String city) {
		super();
		
		this.street = street;
		this.city = city;
	}
	public StandardAddress() {
		super();
		
		this.street = "Some Street";
		this.city = "Some City";
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
