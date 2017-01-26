package org.mk.training.jsr160jmxconnector;

import java.io.IOException;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;


//import com.util.MBeanPrinter;

public class ClientSimpleRemoteMain {

	public static void main(String[] args) throws MalformedObjectNameException, NullPointerException, IOException, InstanceNotFoundException, MBeanException, ReflectionException, AttributeNotFoundException {
		//Client can traverse without knowing the domain specific 
		//"Address" Type
		//or any other domain specific type.
		System.out.println("Create an RMI connector client and " +
		 "connect it to the RMI connector server");
		JMXServiceURL url = new JMXServiceURL(
		      "service:jmx:ws://localhost:8010/jmxws");
		JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
		// Create listener
		
		// Get an MBeanServerConnection
		System.out.println("Get an MBeanServerConnection");
		   MBeanServerConnection mbs = jmxc.getMBeanServerConnection();
		// Get domains from MBeanServer
		System.out.println("Domains :: ");
		String domains[] = mbs.getDomains();
		for (int i = 0; i < domains.length; i++) {
			System.out.print("\tDomain[" + i + "] = " + domains[i]);
		}
		System.out.println();
	    // Get MBeanServer's default domain
	    //
	    String domain = mbs.getDefaultDomain();
	    System.out.println("mbs.getDefaultDomain() :: "+mbs.getDefaultDomain());
	    /*ObjectName name =
            new ObjectName("com.remote.beans:type=OpenEmployee");
	    
        CompositeData cdadd=(CompositeData)mbs.getAttribute(name, "Address");
        System.out.println("cd.get() :: "+cdadd.get("street"));
        System.out.println("cd.get() :: "+cdadd.get("city"));
        System.out.println("cd.get() :: "+cdadd.get("state"));
        System.out.println("cd.get() :: "+cdadd.get("zip"));
      
        System.out.println("**********************LastName");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "LastName"));
        System.out.println("**********************FirstName");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "FirstName"));
        System.out.println("**********************Manager");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "Manager"));
        
        System.out.println("**********************Address");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "Address"));
        System.out.println("**********************Projectnames");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "Projectnames"));
        System.out.println("**********************Onshorelocations");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "Onshorelocations"));
        System.out.println("**********************Projectnamesrolesmap");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "Projectnamesrolesmap"));
        System.out.println("**********************Projectnameslocationsmap");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "Projectnameslocationsmap"));
        System.out.println("**********************END");
		*/

	}
}
