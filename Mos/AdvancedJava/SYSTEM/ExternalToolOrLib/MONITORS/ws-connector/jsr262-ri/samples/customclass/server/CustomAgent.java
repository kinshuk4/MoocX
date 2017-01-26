/**
* CustomAgent.java
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.ws.JMXWSDefinitions;

import mycustomclasses.HelloHolder;

/**
 * Register an MBean that returns an unsupported type.
 * Create and start a JSR262 Connector Server. Extends JAXB context to 
 * support MBean returned class.
 */
public class CustomAgent {
    
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
        
        // Create Connector Server
        Map<String, Object> env = new HashMap<String, Object>();
        
        // JAXB context extension.
        // Provide annotated JAXB classes
        Class[] classes = {HelloHolder.class};
        env.put(JMXWSDefinitions.JAXB_EXTENDED_CONTEXT, classes);
        
        //Create JSR262 ConnectorServer
        JMXConnectorServer server = 
                JMXConnectorServerFactory.
                newJMXConnectorServer(new JMXServiceURL("ws", host, 
                port, urlPath), env, mbs);
        
        server.start();
        
        System.out.println("CustomAgent started on " + server.getAddress() + 
                ". Waiting...");
        
        Thread.sleep(Long.MAX_VALUE);
    }
}


