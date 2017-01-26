/**
* PriorityNotificationMarshaller.java
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
import java.util.Map;
import javax.management.Notification;
import javax.management.remote.ws.JMXWSNotificationExtension;
import javax.management.remote.ws.JMXWSNotificationExtensionMarshaller;

import javax.xml.namespace.QName;

/**
 *
 * PriorityNotification marshaller. The priority field is 
 * marshalled as an XML attribute.
 */
public class PriorityNotificationMarshaller implements
        JMXWSNotificationExtensionMarshaller {
    //The attribute name used to marshall the priority field.
    private static final QName name = 
            new QName("http://my.notifications.com/Priority","priority");
    
    public void marshalExtension(Notification toMarshal,
            JMXWSNotificationExtension extension) throws Exception {
        
        //If the class ios the one we want to marshall
        if(toMarshal instanceof PriorityNotification) {
            PriorityNotification notification =
                    (PriorityNotification) toMarshal;
            
            int priority = notification.getPriority();
            
            //Marshall the priority field as an attribute.
            Map<QName, String> attributes = 
                    (Map<QName, String>)extension.getOtherAttributes();
            attributes.put(name, String.valueOf(priority));
        }
    }
}