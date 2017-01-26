package org.mk.training.iioptest;

import java.io.Serializable;

public class Customer implements  Serializable{
	private String lastName=null;
	private String firstName=null;
	@Override
	public String toString() {
		
		return super.toString()+" lastName:: "+lastName+" firstName:: "+firstName;
	}
	public Customer(String lastName, String firstName) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
