package com.remote.rmi;

import java.io.IOException;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;


import com.util.AppPauser;
import com.util.MBeanPrinter;

public class ClientCreateMBeanMain {
	public static void main(String[] args) throws MalformedObjectNameException, NullPointerException, IOException, InstanceNotFoundException, MBeanException, ReflectionException, AttributeNotFoundException, InstanceAlreadyExistsException, NotCompliantMBeanException {
		//Client can traverse without knowing the domain specific 
		//"Address" Type
		//or any other domain specific type.
		System.out.println("Create an RMI connector client and " +
		 "connect it to the RMI connector server");
		JMXServiceURL url = new JMXServiceURL(
		      "service:jmx:rmi:///jndi/rmi://localhost:1099/server");
		JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
		// Create listener
		ClientListener listener = new ClientListener();
		// Get an MBeanServerConnection
		System.out.println("Get an MBeanServerConnection");
		   MBeanServerConnection mbs = jmxc.getMBeanServerConnection();
		// Get domains from MBeanServer
		System.out.println("Domains :: ");
		String domains[] = mbs.getDomains();
		for (int i = 0; i < domains.length; i++) {
			System.out.print("\tDomain[" + i + "] = " + domains[i]);
		}
		String domain = mbs.getDefaultDomain();
	    System.out.println("mbs.getDefaultDomain() :: "+mbs.getDefaultDomain());
	    
		System.out.println("Press Enter to create MBean");
	    AppPauser.waitForEnterPressed();
	    
	    ObjectName name =
			new ObjectName("com.remote.beans:type=StandardEmployee");
	    System.out.println("Create StandardEmployee MBean...");
	    mbs.createMBean("com.remote.beans.StandardEmployee", name, null, null);
	    mbs.addNotificationListener(name, listener, null, null);
	    System.out.println("Created.. press enter to view details");
	    AppPauser.waitForEnterPressed();
	    
        System.out.println("**********************LastName");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "LastName"));
        System.out.println("**********************FirstName");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "FirstName"));
        System.out.println("**********************Manager");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "Manager"));
        
        System.out.println("press enter to view address details will get exception because address is not serializable");
	    AppPauser.waitForEnterPressed();
        System.out.println("**********************Address");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "Address"));
    	System.out.println("**********************END");


	}
	
	
}
