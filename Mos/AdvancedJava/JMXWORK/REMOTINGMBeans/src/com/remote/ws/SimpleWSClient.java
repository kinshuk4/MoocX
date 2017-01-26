/*
 * Copyright 2006-2007 Sun Microsystems, Inc.  All Rights Reserved.
 * SUN PROPRIETARY/CONFIDENTIAL.  Use is subject to license terms.
 */

package com.remote.ws;

import java.util.HashMap;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.xml.ws.BindingProvider;

import com.util.MBeanPrinter;

public class SimpleWSClient {
    
    public static void main(String[] args) throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:ws://localhost:8010/jmxws");
        
        JMXConnector connector = JMXConnectorFactory.connect(url, null);
        
		System.out.println(System.getProperty("jmx.remote.protocol.provider.pkgs"));
        //Get the MBeanServerConnection
        MBeanServerConnection mbs = connector.getMBeanServerConnection();
        String domain = mbs.getDefaultDomain();
        System.out.println("Default Domain = "+ domain);
        
        ObjectName name =
			new ObjectName("com.remote.beans:type=StandardEmployee");

        System.out.println("**********************LastName");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "LastName"));
        System.out.println("**********************FirstName");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "FirstName"));
        System.out.println("**********************Manager");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "Manager"));
        /*custom type 
        System.out.println("**********************Address");
        MBeanPrinter.printDetails(mbs.getAttribute(name, "Address"));*/
        
        ObjectName name2 =
            new ObjectName("com.remote.beans:type=OpenEmployee");

        System.out.println("**********************Projectnames");
        MBeanPrinter.printDetails(mbs.getAttribute(name2, "Projectnames"));
        System.out.println("**********************Onshorelocations");
        MBeanPrinter.printDetails(mbs.getAttribute(name2, "Onshorelocations"));
        System.out.println("**********************Projectnamesrolesmap");
        MBeanPrinter.printDetails(mbs.getAttribute(name2, "Projectnamesrolesmap"));
        System.out.println("**********************Projectnameslocationsmap");
        MBeanPrinter.printDetails(mbs.getAttribute(name2, "Projectnameslocationsmap"));
        System.out.println("**********************END");

    }
    
}
