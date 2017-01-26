package com.monitoring;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.monitor.Monitor;
import javax.management.monitor.StringMonitor;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import com.misc.beans.OpenAddress;
import com.misc.beans.OpenEmployee;
import com.misc.beans.StandardEmployee;

public class SimpleMonitorStringMain {

	/**
	 * @param args
	 * @throws NotCompliantMBeanException 
	 * @throws MBeanRegistrationException 
	 * @throws InstanceAlreadyExistsException 
	 * @throws NullPointerException 
	 * @throws MalformedObjectNameException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException, NullPointerException, InterruptedException {
		MBeanServer mbs=ManagementFactory.getPlatformMBeanServer();
                ObjectName empname =
			new ObjectName("com.misc.beans:type=StandardEmployee");
		StandardEmployee emp=new StandardEmployee();
		mbs.registerMBean(emp, empname);
		ObjectName name2 =
            new ObjectName("com.misc.beans:type=OpenEmployee");
    
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
	    
	    StringMonitor sm=new StringMonitor();
	    ObjectName name3 =
            new ObjectName("com.monitoring:type=StringMonitor");

	    sm.addObservedObject(name2);
	    sm.addObservedObject(empname);
	    sm.setObservedAttribute("LastName");
	    mbs.registerMBean(sm,name3);
	    sm.setStringToCompare("jonny gaddar");
	    sm.setNotifyMatch(true);
	    sm.setNotifyDiffer(true);
	    //sm.setGranularityPeriod(1500);
	    Thread.sleep(Integer.MAX_VALUE);
	}

}
