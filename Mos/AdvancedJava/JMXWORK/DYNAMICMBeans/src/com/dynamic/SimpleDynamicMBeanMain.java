package com.dynamic;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class SimpleDynamicMBeanMain {

	/**
	 * @param args
	 * @throws  
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		// Construct the ObjectName for the MBean we will register
		ObjectName empname = new ObjectName("com.SimpleDynamicMBeanMain:type=Employee");

		DynamicAddress add=new DynamicAddress("MGRoad","Bangalore");
		
		DynamicEmployee emp = new DynamicEmployee("Cheeky","Monkey",1000,5.15f,false,add);

		// Register the Employee MBean
		mbs.registerMBean(emp, empname);
		System.out.println(mbs.getAttribute(empname, "address"));
		// Wait forever
		Thread.sleep(30000);
		System.out.println("Waiting 20000 ...");
		emp.setSalary(2000);
		System.out.println("Waiting forever...");
		Thread.sleep(Long.MAX_VALUE);
	    

	}

}
