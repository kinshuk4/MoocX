/**
* SimpleClient.java
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;


/**
 * Connect to Servlet container and send a getAttribute.
 *
 */
public class SimpleClient {
    
    public static void main(String[] args) throws Exception {
        
        //Update with your agent port
        int port = 8080;
        //Update with your agent host
        String host = "localhost";
        //Update with your agent url path
        String urlPath = "/samplewebapp/jmxws";
        
        //Create JMX Agent URL
        JMXServiceURL url = new JMXServiceURL("ws", host, port, urlPath);
        //Connect the JMXConnector
        JMXConnector connector = JMXConnectorFactory.connect(url, null);
        
        //Get the MBeanServerConnection
        MBeanServerConnection connection = connector.getMBeanServerConnection();
        
        //getAttribute
        ObjectName mbeanName = new ObjectName(":type=Sample");
        
        String value = (String) connection.getAttribute(mbeanName, "Hello");
        
        System.out.println("Received : " + value);
        
        //close connection
        connector.close();
        
        System.out.println("Connection closed.");
    }
}
