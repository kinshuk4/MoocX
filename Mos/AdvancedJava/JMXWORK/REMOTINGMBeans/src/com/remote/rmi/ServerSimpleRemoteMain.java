package com.remote.rmi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.*;
import javax.management.modelmbean.*;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import com.remote.beans.OpenAddress;
import com.remote.beans.OpenEmployee;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerSimpleRemoteMain {
	public static void main(String[] args) throws SecurityException, NoSuchMethodException, RuntimeOperationsException, MBeanException, InstanceNotFoundException, InvalidTargetObjectTypeException, MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, NotCompliantMBeanException, ReflectionException, InterruptedException, IntrospectionException, XMLParseException, AttributeNotFoundException, Exception {
		//MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		MBeanServer mbs=MBeanServerFactory.createMBeanServer();
        // Construct the ObjectName for the MBean we will register
        ObjectName name =
                new ObjectName("com.remote.beans:type=OpenEmployee");
        
        // Create the Employee MXBean
        List<String> projects=new ArrayList<String>();
        projects.add("GPS");
        projects.add("PEERTOPEER");
        List<OpenAddress> onshorelocations=new ArrayList<OpenAddress>();
        onshorelocations.add(new OpenAddress("MGRoad","CopenHagen","Denmark",560062));
        onshorelocations.add(new OpenAddress("MGRoad","Zurick","Switzerland",560062));
        Map<String, String> projectnamesrolesmap=new HashMap<String, String>();
        projectnamesrolesmap.put("GPS", "PL");
        projectnamesrolesmap.put("PEERTOPEER", "PM");
        Map<String, OpenAddress> projectnameslocationsmap=new HashMap<String, OpenAddress>();
        projectnameslocationsmap.put("GPS", new OpenAddress("MGRoad","CopenHagen","Denmark",560062));
        projectnameslocationsmap.put("PEERTOPEER", new OpenAddress("MGRoad","Zurick","Switzerland",560062));

        OpenEmployee empmxbean=new OpenEmployee("Cheeky","Monkey",1000,5.15f,false,new OpenAddress("MGRoad","Bangalore","KAR",560062),projects,onshorelocations,projectnamesrolesmap,projectnameslocationsmap);
                
        // Register the Employee MXBean
        mbs.registerMBean(empmxbean, name);
        
        JMXServiceURL url = new JMXServiceURL(
	      "service:jmx:rmi:///jndi/rmi://localhost:1099/server");
        JMXConnectorServer cs =
         JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
         System.out.println("Starting rmiregistry first...");
        LocateRegistry.createRegistry(1099);
        System.out.println("Start the RMI connector server");
        cs.start();
        
        System.out.println("The RMI connector server successfully started");
        System.out.println("and is ready to handle incoming connections");
        System.out.println("Start the client on a different window and");
        //System.out.println("press <Enter> once the client has finished");
	    //Thread.sleep(Integer.MAX_VALUE);
        //wait for ever
        
        
        //waitForEnterPressed();

	    // Stop the RMI connector server
	    //
	    //System.out.println("\nStop the RMI connector server");
	    //cs.stop();
	}
	
	
}
