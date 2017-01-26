package com.relationship;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.relation.RelationService;
import javax.management.relation.RelationServiceMBean;
import javax.management.relation.Role;
import javax.management.relation.RoleInfo;
import javax.management.relation.RoleList;

import com.misc.beans.StandardAddress;
import com.misc.beans.StandardEmployee;

public class SimpleRelationShipMain {

	/**
	 * @param args
	 * @throws  
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		// Create the Relation Service MBean
		ObjectName relSvcName = new ObjectName("com.relationship:type=RelationService");
		RelationService relSvcObject = new RelationService(true);
		mbs.registerMBean(relSvcObject, relSvcName);

		//Create an MBean proxy for easier access to the Relation Service
		RelationServiceMBean relSvc =
		    MBeanServerInvocationHandler.newProxyInstance(mbs, relSvcName,
		                                                  RelationServiceMBean.class,
		                                                  false);
		//Create the mbeans which will participate in relationship
		StandardAddress add=new StandardAddress("MGRoad","Bangalore");
		StandardEmployee emp = new StandardEmployee("Cheeky","Monkey",1000,5.15f,false);
		List<StandardAddress> billingaddresses=new ArrayList<StandardAddress>();
		billingaddresses.add(add);
		emp.setBillingAddress(billingaddresses);
	    ObjectName empname =
			new ObjectName("com.misc.beans:type=StandardEmployee");
		ObjectName addname =
            new ObjectName("com.misc.beans:type=StandardAddress");
          
	    // Register the Employee MXBean
	    mbs.registerMBean(emp, empname);
	    mbs.registerMBean(add, addname);
	    // Define the DependsOn relation type
		RoleInfo[] dependsOnRoles = {
		    new RoleInfo("references", StandardEmployee.class.getName()),
		    new RoleInfo("referencedBy", StandardAddress.class.getName())
		};
		relSvc.createRelationType("DependsOn", dependsOnRoles);

		// Now define a relation instance "Customer DependsOn Address"

		
		Role references = new Role("references", Collections.singletonList(empname));
		Role referencedBy = new Role("referencedBy", Collections.singletonList(addname));
		Role[] roleArray = {references, referencedBy};
		RoleList roles = new RoleList(Arrays.asList(roleArray));
		relSvc.createRelation("A-DependsOn-B", "DependsOn", roles);

		//Query the Relation Service to find what modules moduleA depends on
		Map<ObjectName,List<String>> dependentAMap =
		    relSvc.findAssociatedMBeans(addname, "DependsOn", "referencedBy");
		System.out.println(""+dependentAMap);
		Set<ObjectName> dependentASet = dependentAMap.keySet();
		// Set of ObjectName containing moduleB
		System.out.println(""+dependentASet);

	}

}
