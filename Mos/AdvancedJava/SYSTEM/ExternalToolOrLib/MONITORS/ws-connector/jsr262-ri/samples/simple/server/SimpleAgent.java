/**
* SimpleAgent.java
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

/**
 * Register Sample MBean.
 * Create and start a JSR262 Connector Server.
 */
public class SimpleAgent {
    
    public static void main(String[] args) throws Exception {
        
        //You can replace with your host name
        String host = "localhost";        
        //You can replace with your port number
        int port = 9998;
        //You can replace with your url path
        String urlPath = "/jmxws";
        
        //Access platform MBeanServer
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        
        //Register Sample MBean.
        mbs.registerMBean(new Sample(), 
               new ObjectName(":type=Sample")); 
        
        //Create JSR262 ConnectorServer
        JMXConnectorServer server = 
                JMXConnectorServerFactory.
                newJMXConnectorServer(new JMXServiceURL("ws", host, 
                port, urlPath), null, mbs);       
        
        server.start();
        
        System.out.println("SimpleAgent started on " + server.getAddress() + 
                ". Waiting...");
        
        Thread.sleep(Long.MAX_VALUE);
    }
}


