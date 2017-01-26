/**
* CustomNotificationAgent.java
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
import javax.management.remote.ws.JMXWSNotificationExtensionMarshaller;

import java.util.Map;
import java.util.HashMap;

/**
 * Register an MBean that send a non supported JMX extended Notification.
 * Create and start a JSR262 Connector Server. Use JMXWSNotificationExtensionMarshaller
 * to marshal sent Notification.
 */
public class CustomNotificationAgent {

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
        
        Map<String, Object> env = new HashMap<String, Object>();
        
        // Custom Notification marshallers
        Map<Class, JMXWSNotificationExtensionMarshaller> marshallers =
                new HashMap<Class, JMXWSNotificationExtensionMarshaller>();
        
        // Add a marshaller for PriorityNotification
        marshallers.put(PriorityNotification.class, 
                new PriorityNotificationMarshaller());
        
        env.put(JMXWSDefinitions.JMX_WS_NOTIFICATION_MARSHALLER, marshallers);
        
        //Create JSR262 ConnectorServer
        JMXConnectorServer server =
                JMXConnectorServerFactory.
                newJMXConnectorServer(new JMXServiceURL("ws", host,
                port, urlPath), env, mbs);
        
        server.start();
        
        System.out.println("CustomNotificationAgent started on " + server.getAddress() +
                ". Waiting...");
        
        Thread.sleep(Long.MAX_VALUE);
    }
}


