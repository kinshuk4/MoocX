/**
* CustomNotificationClient.java
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.ws.JMXWSDefinitions;
import javax.management.remote.ws.JMXWSNotificationExtensionUnmarshaller;


/**
 * Receives a PriorityNotification (notification extended form JMX Notification).
 * Use JMXWSNotificationExtensionUnmarshaller class to unmarshall from XML to
 * PriorityNotification Java class.
 *
 */
public class CustomNotificationClient {

    //Notification Listener to receive PriorityNotification
    private static class MyListener implements NotificationListener {
        public void handleNotification(Notification notification,
                Object handback) {
            PriorityNotification notif = (PriorityNotification) notification;
            System.out.println("Received priority : " + notif.getPriority());
            synchronized(this) {
                notifyAll();
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
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
        
        // Custom Notification unmarshallers
        List<JMXWSNotificationExtensionUnmarshaller> unmarshallers =
                new ArrayList<JMXWSNotificationExtensionUnmarshaller>();
        
        // Add a marshaller for PriorityNotification
        unmarshallers.add(new PriorityNotificationUnmarshaller());
        
        env.put(JMXWSDefinitions.JMX_WS_NOTIFICATION_UNMARSHALLER, unmarshallers);
        
        JMXConnector connector = JMXConnectorFactory.connect(url, env);
        
        //Get the MBeanServerConnection
        MBeanServerConnection connection = connector.getMBeanServerConnection();
        
        // Add notification listener
        ObjectName mbeanName = new ObjectName(":type=Sample");
        
        //register to notifications in order to receive PriorityNotifications
        MyListener listener = new MyListener();
        connection.addNotificationListener(mbeanName,
                listener,
                null,
                null);
        
        //Call the MBean to make it send a Priority Notification
        connection.invoke(mbeanName,"sendPriorityNotification", null, null);
        
        synchronized(listener) {
            listener.wait();
        }
        
        //close connection
        connector.close();
        
        System.out.println("Connection closed.");
    }
}
