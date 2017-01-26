/**
* PriorityNotificationUnmarshaller.java
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
import java.util.Map;
import javax.management.Notification;
import javax.management.remote.ws.JMXWSNotificationExtension;
import javax.management.remote.ws.JMXWSNotificationExtensionUnmarshaller;
import javax.xml.namespace.QName;
/**
 * PriorityNotification unmarshaller. The priority field is 
 * unmarshalled from the list of received XML attributes.
 * Other Notification fields are unmarshalled by JSR262 Connector.
 * 
 */
public class PriorityNotificationUnmarshaller implements
        JMXWSNotificationExtensionUnmarshaller{
    //The attribute name used to unmarshall the priority field.
    private static final QName name = 
            new QName("http://my.notifications.com/Priority","priority");
    
    public PriorityNotification unmarshalExtension(Notification stdUnmarshalled,
            JMXWSNotificationExtension extensions) throws Exception {
        //Look for priority attribute.
        Map<QName, String> attributes = (Map<QName, String>) extensions.getOtherAttributes();
        String priority = attributes.get(name);
        
        //Not our Notification to marshall.
        if(priority == null) return null;
        
        // Create a PriorityNotification based on received stdUnmarshalled 
        // Notification and unmarshalled priority
        PriorityNotification priorityNotification = 
                new PriorityNotification(stdUnmarshalled.getSource(),
                Integer.valueOf(priority));
        
        return priorityNotification;
    }
}
