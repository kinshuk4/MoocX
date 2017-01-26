/*
 * @(#)file      MonitorSettingException.java
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

package javax.management.monitor; 


/**
 * Exception thrown by the monitor when a monitor setting becomes invalid while the monitor is running.
 * <P>
 * As the monitor attributes may change at runtime, a check is performed before each observation.
 * If a monitor attribute has become invalid, a monitor setting exception is thrown.
 *
 * @version     4.14     07/15/03
 * @author      Sun Microsystems, Inc
 *
 * @since-jdkbundle 1.5
 */
public class MonitorSettingException extends javax.management.JMRuntimeException { 

    /* Serial version */
    private static final long serialVersionUID = -8807913418190202007L;

    /**
     * Default constructor.
     */
    public MonitorSettingException() {
        super();
    }

    /**
     * Constructor that allows an error message to be specified.
     *
     * @param message The specific error message.
     */
    public MonitorSettingException(String message) {
        super(message);
    }
}
