package com.open.manual;
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

public class SimpleManualOpenMBeanMain {
	public static void main(String[] args) throws SecurityException, NoSuchMethodException, RuntimeOperationsException, MBeanException, InstanceNotFoundException, InvalidTargetObjectTypeException, MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, NotCompliantMBeanException, ReflectionException, InterruptedException, IntrospectionException, XMLParseException, AttributeNotFoundException {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
       
        // Construct the ObjectName for the MBean we will register
        ObjectName name =
                new ObjectName("com.open.manual:type=OpenEmployee");
        
        // Create the Employee MXBean
        OpenAddress a=new OpenAddress("MGRoad","CopenHagen");
        OpenEmployee empmxbean=new OpenEmployee("Cheeky","Monkey",1000,5.15f,false,a);
                
        // Register the Employee MXBean
        mbs.registerMBean(empmxbean, name);
        
        
        //Client can traverse without knowing the domain specific 
        //"Address" Type
        //or any other domain specific type.
        
        CompositeData cdadd=(CompositeData)mbs.getAttribute(name, "Address");
        System.out.println("cd.get() :: "+cdadd.get("street"));
        System.out.println("cd.get() :: "+cdadd.get("city"));
        
      
        System.out.println("**********************Address");
        printOpenDetails(mbs.getAttribute(name, "Address"));
        // Wait forever
        System.out.println("Waiting...");
        Thread.sleep(Long.MAX_VALUE);

	}
	private static void printOpenDetails(Object info){
		
		if (info!=null && info instanceof CompositeData[]) {
			
			CompositeData[] cda=(CompositeData[])info;
			for (int i = 0; i < cda.length; i++) {
				printOpenDetails(cda[i]);
			}
		}
		else if (info!=null && info instanceof CompositeData) {
			
        	CompositeData cd=(CompositeData)info;
        	if ((cd.values() instanceof CompositeData)) {
        		System.out.println("cd.values() :: "+cd.values());
			}
        	else{
        		for (Iterator iter = cd.values().iterator(); iter.hasNext();) {
					printOpenDetails(iter.next());
				}
        	}
		}
        else if (info!=null && info instanceof TabularData) {
        	
        	TabularData td=(TabularData)info;
        	Set ks=td.keySet();
        	for (Iterator iter = ks.iterator(); iter.hasNext();) {
        		List l=(List)iter.next();
        		Object o=td.get(l.toArray());
				printOpenDetails(o);
			}
        }
        else if (info!=null && info instanceof String[]) {
        	String[] result=(String[])info;
			for (int i = 0; i < result.length; i++) {
				System.out.println(result[i]);
			}
		}
        else {
        	System.out.println(info);
        }
	}

}
