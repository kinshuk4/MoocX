package com.model;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.management.*;
import javax.management.modelmbean.*;

public class SimpleModelMBeanMain {
	public static void main(String[] args) throws SecurityException, NoSuchMethodException, RuntimeOperationsException, MBeanException, InstanceNotFoundException, InvalidTargetObjectTypeException, MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, NotCompliantMBeanException, ReflectionException, InterruptedException, IntrospectionException, XMLParseException, AttributeNotFoundException {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ModelAddress a=new ModelAddress("MGRoad","Bangalore");
		ModelEmployee e=new ModelEmployee("Cheeky","Monkey",1000,5.15f,false,a);
		ModelMBeanInfoBuilder builder=new ModelMBeanInfoBuilder();
		ModelMBeanInfo mmbi=builder.buildDynamicMBeanInfo();
		ModelMBean mmb = new RequiredModelMBean(mmbi);
		mmb.setManagedResource(e, "ObjectReference");
		ObjectName mapName = new ObjectName("com.SimpleModelMBeanMain:type=Map,name=Employee");
		mbs.registerMBean(mmb, mapName);
		System.out.println("mmbidescriptor :: "+mmbi.getDescription());
		System.out.println(mmb.getAttribute("salary"));
		System.out.println("waiting for ever.....");
		Thread.sleep(Integer.MAX_VALUE);
		Descriptor desmap=new DescriptorSupport(new String[]{"name","descriptorType","GetMethod"},new String[]{"lastName","attribute","getLastName"});
		System.out.println(desmap.isValid());
	}

}
