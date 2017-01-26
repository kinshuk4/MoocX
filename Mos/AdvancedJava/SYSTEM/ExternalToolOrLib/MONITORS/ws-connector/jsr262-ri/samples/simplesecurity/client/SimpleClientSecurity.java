/**
* SimpleClientSecurity.java
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


/**
 * Connect to JSR262 Connector Server providing credentials
 * and send a getAttribute.
 *
 */
public class SimpleClientSecurity {
    
    public static void main(String[] args) throws Exception {
        
        //Update with your agent port
        int port = 9998;
        //Update with your agent host
        String host = "localhost";
        //Update with your agent url path
        String urlPath = "/jmxws";
        
        //Create JMX Agent URL
        JMXServiceURL url = new JMXServiceURL("ws", host, port, urlPath);
        
        System.out.println("Connecting with credentials.");
        Map<String, Object> env = new HashMap<String, Object>();
        String[] credentials = new String[]{"admin", "adminpassword"};
        env.put(JMXConnector.CREDENTIALS, credentials);
        
        //Connect providing credentials
        JMXConnector connector = JMXConnectorFactory.connect(url, env);
        
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
