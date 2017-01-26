package com.standard;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class SimpleStandardMBeanMain {

	/**
	 * @param args
	 * @throws  
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		// Construct the ObjectName for the MBean we will register
		ObjectName empname = new ObjectName("com.SimpleStandardMBeanMain:type=Employee");

		ObjectName addname = new ObjectName("com.SimpleStandardMBeanMain:type=Address");
		StandardAddress add=new StandardAddress("MGRoad","Bangalore");
		StandardEmployee emp = new StandardEmployee("Cheeky","Monkey",1000,5.15f,false,add);

		// Register the both the MBean
		mbs.registerMBean(emp, empname);
		mbs.registerMBean(add, addname);
		
		//use mbeanserver interface to get details
		MBeanAttributeInfo mbai[]=mbs.getMBeanInfo(empname).getAttributes();
		for (int i = 0; i < mbai.length; i++) {
			System.out.print(" :: "+mbai[i].getName());
			System.out.print(" :: "+mbai[i].getType());
			System.out.println();
		}
		System.out.println(mbs.getAttribute(empname, "Address"));
		
		
		// Wait till jconsole is ready and set salary.
		Thread.sleep(30000);
		emp.setSalary(2000);
		
		
		//Wait forever
		System.out.println("Waiting forever...");
		Thread.sleep(Long.MAX_VALUE);
	    

	}
}
