/*
 * @(#)file      NotificationListener.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.14
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


import java.util.EventListener;


/**
 * Should be implemented by an object that wants to receive notifications.
 *
 * @since-jdkbundle 1.5
 */
public interface NotificationListener extends java.util.EventListener   { 

    /**
    * Invoked when a JMX notification occurs.
    * The implementation of this method should return as soon as possible, to avoid
    * blocking its notification broadcaster.
    *
    * @param notification The notification.    
    * @param handback An opaque object which helps the listener to associate information
    * regarding the MBean emitter. This object is passed to the MBean during the
    * addListener call and resent, without modification, to the listener. The MBean object 
    * should not use or modify the object. 
    *
    */
    public void handleNotification(Notification notification, Object handback) ;
}

