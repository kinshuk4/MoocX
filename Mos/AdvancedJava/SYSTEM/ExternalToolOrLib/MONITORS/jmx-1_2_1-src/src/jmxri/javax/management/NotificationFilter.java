/*
 * @(#)file      NotificationFilter.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.15
 * @(#)lastedit      03/07/15
 *
 * Copyright 2000-2003 Sun Microsystems, Inc.  All rights reserved.
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 * 
 * Copyright 2000-2003 Sun Microsystems, Inc.  Tous droits réservés.
 * Ce logiciel est proprieté de Sun Microsystems, Inc.
 * Distribué par des licences qui en restreignent l'utilisation. 
 */

package javax.management; 


/**
 * To be implemented by a any class acting as a notification filter.
 * It allows a registered notification listener to filter the notifications of interest.
 *
 * @since-jdkbundle 1.5
 */
public interface NotificationFilter extends java.io.Serializable { 

    /**
     * Invoked before sending the specified notification to the listener.
     *   
     * @param notification The notification to be sent.
     * @return <CODE>true</CODE> if the notification has to be sent to the listener, <CODE>false</CODE> otherwise.
     */  
    public boolean isNotificationEnabled(Notification notification);  
}
