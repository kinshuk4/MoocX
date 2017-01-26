/* 
 * @(#)file      TraceFilter.java 
 * @(#)author    Sun Microsystems, Inc. 
 * @(#)version   1.3 
 * @(#)lastedit      03/07/15 
 * 
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 * Copyright 2003 Sun Microsystems, Inc. Tous droits r‰serv‰s.
 * Ce logiciel est propriet‰ de Sun Microsystems, Inc.
 * Distribu‰ par des licences qui en restreignent l'utilisation. 
 */ 


package com.sun.management.jmx;


import javax.management.*;

/**
 * This class does nothing. To get the traces, use the 
 * {@link java.util.logging} APIs.
 *
 * @deprecated Use {@link java.util.logging} APIs instead.
 *
 * @since-jdkbundle 1.5
 */
public class TraceFilter implements NotificationFilter {

    // -----------------
    // protected variables
    // -----------------
    protected int levels;
    protected int types;

// ------------
// constructors
// ------------
    /**
     * This class does nothing. To get the traces, use the 
     * {@link java.util.logging} APIs.
     */
    public TraceFilter(int levels, int types) throws IllegalArgumentException {
	this.levels = levels;
	this.types = types;
    }


// --------------
// public methods
// --------------
    /**
     * This method does nothing. To get the traces, use the 
     * {@link java.util.logging} APIs.
     * @return false
     */
    public boolean isNotificationEnabled(Notification notification) {
	return false;
    }

    /**
     * get the levels selected
     *
     * @return the levels selected for filtering.
     */
    public int getLevels() {
	return levels;
    }
    
    /**
     * get types selected
     *
     * @return the types selected for filtering.
     */
    public int getTypes() {
	return types;
    }
    
}
