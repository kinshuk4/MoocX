package com.mule.common;

public class Employee {
	private String name;
	private Address address;
	public Employee() {
		super();
	}
	public Employee(String name, Address address) {
		super();
		this.name = name;
		this.address = address;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Name :: "+name+ " Address :: "+address;
	}
}
