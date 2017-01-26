package com.dynamic.betterandgeneric;

import java.lang.management.ManagementFactory;

import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class BetterandGenericDynamicMBeanMain {

	/**
	 * @param args
	 * @throws  
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		// Construct the ObjectName for the MBean we will register
		ObjectName empname = new ObjectName("com.BetterandGenericDynamicMBeanMain:type=Employee");

		DynamicAddress add=new DynamicAddress("MGRoad","Bangalore");
		
		DynamicEmployee emp = new DynamicEmployee("Cheeky","Monkey",1000,5.15f,false,add);

		// Register the Employee MBean
		
		mbs.registerMBean(emp, empname);
		
		long sa=100;
		System.out.println(mbs.getAttribute(empname, "Salary"));
		Attribute sal=new Attribute("Salary",sa);
		mbs.setAttribute(empname, sal);
		System.out.println(mbs.getAttribute(empname, "Salary"));
		
		System.out.println(mbs.getAttribute(empname, "Address"));
		DynamicAddress add2=new DynamicAddress("Koramangala","Bangalore");
		Attribute add2attr=new Attribute("Address",add2);
		mbs.setAttribute(empname, add2attr);
		System.out.println(mbs.getAttribute(empname, "Address"));
		
		// Wait forever
		
		
		System.out.println("Waiting forever...");
		Thread.sleep(Long.MAX_VALUE);
	    

	}

}
