/**
* Sample.java
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/

import javax.management.*;

/**
 * Class Sample
 * Sample MBean
 */
public class Sample extends NotificationBroadcasterSupport 
        implements SampleMBean {
    
    public Sample() {
    }

    /**
     * Returns an hello message
     */
    public void sendPriorityNotification() {
       //Send a Notification with a priority of 999
       sendNotification(new PriorityNotification(this, 999));
    }
}


