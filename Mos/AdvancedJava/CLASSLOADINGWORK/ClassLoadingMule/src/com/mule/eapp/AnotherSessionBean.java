package com.mule.eapp;

import com.mule.common.Address;
import com.mule.common.Employee;

class AnotherSessionBean implements SessionInterface{
	public AnotherSessionBean() {
		super();
		System.out.println("AnotherSessionBean()");
	}

	public Employee getEmployee(String name) {
		return new Employee("CHEEKU MONKEY",new Address("MG ROAD","BANGALORE"));
	}

}
