/**
* CustomClient.java
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/

import java.util.HashMap;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.ws.JMXWSDefinitions;

import mycustomclasses.HelloHolder;

/**
 * CustomClient. Add an unsupported Java Class to Connector
 * by extending JAXB context.
 * Then gets the attribute.
 *
 */
public class CustomClient {
    
    public static void main(String[] args) throws Exception {
        
        //Update with your agent port
        int port = 9998;
        //Update with your agent host
        String host = "localhost";
        //Update with your agent url path
        String urlPath = "/jmxws";
        
        //Create JMX Agent URL
        JMXServiceURL url = new JMXServiceURL("ws", host, port, urlPath);
        
        //Connect the JMXConnector
        
        Map<String, Object> env = new HashMap<String, Object>();
        
        // JAXB context extension.
         // Provide annotated JAXB classes
        Class[] classes = {HelloHolder.class};
        env.put(JMXWSDefinitions.JAXB_EXTENDED_CONTEXT, classes);
        
        JMXConnector connector = JMXConnectorFactory.connect(url, env);
        
        //Get the MBeanServerConnection
        MBeanServerConnection connection = connector.getMBeanServerConnection();
        
        // Get Hello Attribute
        ObjectName mbeanName = new ObjectName(":type=Sample");
        
        HelloHolder value = (HelloHolder) connection.getAttribute(mbeanName, "Hello");
        
        System.out.println("Received message in holder : " + value.getHello());
        
        //close connection
        connector.close();
        
        System.out.println("Connection closed.");
    }
}
