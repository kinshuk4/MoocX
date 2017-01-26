/**
* PriorityNotification.java
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
import javax.management.Notification;

/**
 *
 * Extended JMX Notification that adds a priority field.
 */
public class PriorityNotification extends Notification {
    private int priority;
    /** Creates a new instance of PriorityNotification */
    public PriorityNotification(Object src, int priority) {
        super("priority.notification", src, 0);
        this.priority = priority;
    }
    
    /**
     * Returns the notification priority.
     */
    public int getPriority() {
        return priority;
    }
    
}
