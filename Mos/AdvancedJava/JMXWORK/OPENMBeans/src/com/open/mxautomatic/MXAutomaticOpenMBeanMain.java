package com.open.mxautomatic;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.*;
import javax.management.modelmbean.*;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;

import com.util.MBeanPrinter;

//import com.standard.StandardAddress;
//import com.standard.StandardEmployee;

//import com.standard.StandardAddress;
//import com.standard.StandardEmployee;

public class MXAutomaticOpenMBeanMain {
	public static void main(String[] args) throws SecurityException, NoSuchMethodException, RuntimeOperationsException, MBeanException, InstanceNotFoundException, InvalidTargetObjectTypeException, MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, NotCompliantMBeanException, ReflectionException, InterruptedException, IntrospectionException, XMLParseException, AttributeNotFoundException {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		// Construct the ObjectName for the MBean we will register
        ObjectName name =
                new ObjectName("com.open.mxautomatic:type=OpenEmployee");
        
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
        
        
        //Client can traverse without knowing the domain specific 
        //"Address" Type
        //or any other domain specific type.
        
        CompositeData cdadd=(CompositeData)mbs.getAttribute(name, "Address");
        System.out.println("cd.get() :: "+cdadd.get("street"));
        System.out.println("cd.get() :: "+cdadd.get("city"));
        System.out.println("cd.get() :: "+cdadd.get("state"));
        System.out.println("cd.get() :: "+cdadd.get("zip"));
      
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
        // Wait forever
        System.out.println("Waiting...");
        Thread.sleep(Long.MAX_VALUE);

	}
	
}
