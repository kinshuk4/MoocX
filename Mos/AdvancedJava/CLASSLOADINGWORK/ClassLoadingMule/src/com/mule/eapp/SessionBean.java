package com.mule.eapp;

import com.mule.common.Address;
import com.mule.common.Employee;

public class SessionBean implements SessionInterface {
	
	
	public SessionBean() {
		super();
		System.out.println("Employee(SessionBean) :: "+Employee.class.getName()+"("+Employee.class.hashCode()+") :: ClassLoaders :: "+Employee.class.getClassLoader());
	}

	public Employee getEmployee(String name) {
		return new Employee("CHEEKU MONKEY",new Address("MG ROAD","BANGALORE"));
	}

}
