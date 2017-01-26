package com.remote.http;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import com.remote.beans.OpenAddress;
import com.remote.beans.OpenEmployee;
import com.remote.beans.StandardEmployee;

import mx4j.tools.adaptor.http.XSLTProcessor;

public class MX4JHttpAdaptorServerMain {

	/**
	 * @param args
	 * @throws NullPointerException 
	 * @throws MalformedObjectNameException 
	 * @throws NotCompliantMBeanException 
	 * @throws InstanceAlreadyExistsException 
	 * @throws ReflectionException 
	 * @throws MBeanException 
	 * @throws InstanceNotFoundException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, NotCompliantMBeanException, InstanceNotFoundException, MBeanException, ReflectionException, IOException {
		//Must use an MBeanServer (a.o.t. a MBeanServerConnection, since
		//the method HttpAdaptor.setProcessor() is not JMX-exposed.
		//MBeanServer mbs = MBeanServerFactory.createMBeanServer();
		MBeanServer mbs=ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName(
		"mx4j:class=mx4j.tools.adaptor.http.HttpAdaptor,id=1");
		//Make our own Java object so we can run adaptor.setProcessor() below.
		mx4j.tools.adaptor.http.HttpAdaptor adaptor =
		new mx4j.tools.adaptor.http.HttpAdaptor(
		((args.length == 0) ? 8112 : Integer.parseInt(args[0])),
		"0.0.0.0");
		//Previous line sets the listen port and hostname.
		//"0.0.0.0" listens to all IP addresses. You can use a specific
		//host name or IP address if you wish.
		
		mbs.registerMBean(adaptor, name);
		mbs.invoke(name, "start", null, null);
		adaptor.setProcessor(new XSLTProcessor());
		// De-comment this block to run this in foreground.
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
	
	    OpenEmployee empmxbean=new OpenEmployee("Monkey","Cheeky",1000,5.15f,false,new OpenAddress("MGRoad","Bangalore","KAR",560062),projects,onshorelocations,projectnamesrolesmap,projectnameslocationsmap);
	            
	    // Register the Employee MXBean
	    mbs.registerMBean(empmxbean, name2);
	    
		//System.err.println("Adaptor started. Hit ENTER to exit completely.");
		//System.in.read();
		//mbs.invoke(name, "stop", null, null);
		//System.out.println("Exiting Mx4j.main()");

	}

}
