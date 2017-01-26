/**
* SimpleAgentSecurity.java
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


/**
 * Register Sample MBean.
 * Create and start a JSR262 Connector Server.
 * Enable Authentication.
 */
public class SimpleAgentSecurity {
    
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
        Map<String, String> env = new HashMap<String, String>();
        
        // Providing a file will enable authentication based on default, 
        // file based, JMXAuthenticator.
        
        // NB: You can also provide your own JMXAuthenticator thanks to 
        // JMXConnectorServer.AUTHENTICATOR
        env.put("jmx.remote.x.password.file", "users.security");
        
        JMXConnectorServer server = 
                JMXConnectorServerFactory.
                newJMXConnectorServer(new JMXServiceURL("ws", host, 
                port, urlPath), env, mbs);       
        
        server.start();
        
        System.out.println("SimpleAgentSecurity started on " + server.getAddress() + 
                ". Waiting...");
        
        Thread.sleep(Long.MAX_VALUE);
    }
}


