package com.mule.common;

public class Address {
	private String street;
	private String city;
	public Address(String street, String city) {
		super();
		this.street = street;
		this.city = city;
	}
	public Address() {
		super();
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
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Street :: "+street+" City :: "+city;
	}
}
