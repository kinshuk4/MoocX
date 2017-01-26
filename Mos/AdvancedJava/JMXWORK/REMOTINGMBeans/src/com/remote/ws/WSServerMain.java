/*
 * Copyright 2006-2007 Sun Microsystems, Inc.  All Rights Reserved.
 * SUN PROPRIETARY/CONFIDENTIAL.  Use is subject to license terms.
 */

package com.remote.ws;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import com.remote.beans.OpenAddress;
import com.remote.beans.OpenEmployee;
import com.remote.beans.StandardEmployee;

public class WSServerMain {
    public static void main(String[] args) throws Exception {
    	//this is the mbs which is connected by default.
    	MBeanServer mbs=ManagementFactory.getPlatformMBeanServer();
    	//this is the mbs which is not connected by default.
    	//MBeanServer mbs=MBeanServerFactory.createMBeanServer();
        JMXConnectorServer server =
                JMXConnectorServerFactory.
                newJMXConnectorServer(new JMXServiceURL("service:jmx:ws://localhost:8010/jmxws"), null,
                mbs);
        server.start();
        ObjectName empname =
			new ObjectName("com.remote.beans:type=StandardEmployee");
		StandardEmployee emp=new StandardEmployee();
		mbs.registerMBean(emp, empname);
		ObjectName name2 =
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
	    mbs.registerMBean(empmxbean, name2);

        System.out.println(server.getClass().getName());
        System.out.println("JSR 262 ConnectorServer is ready to serve on http://localhost:8010/jmxws");
    }
    
}
